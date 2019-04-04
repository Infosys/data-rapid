/*
 * Copyright 2018 Infosys Ltd.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.datarapid.core.databuilder;

import org.datarapid.core.databuilder.bucketing.ColumnBucketing;
import org.datarapid.core.databuilder.factory.DatageneratorFactory;
import org.datarapid.core.view.ColumnMeta;
import org.datarapid.core.view.FileInfo;
import org.datarapid.core.view.JobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * @Description :- This class is used to build the data. It receives the
 * configuration object from the DataGenCoreServiceImpl class.It
 * starts building all the columns one by one and populates this
 * list of columns in a list and then finally returns the List<List
 * <String>> containing all the data.
 */

public class BuildData {

    private int noOfValues;

    private static final Logger logger = LoggerFactory.getLogger(BuildData.class);


    public BuildData() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param configuration
     * @Description :-This method receives the configuration object from the
     * DataGenCoreServiceImpl class.It extracts the fileInfo from
     * the configuration object and invokes the populateColumns
     * method.
     */
    public List<List<String>> startBuilding(JobConfiguration configuration) {

        FileInfo fileInfo = configuration.getFileInfo();
        List<List<String>> columnOfColumns = populateColumns(fileInfo);

        return columnOfColumns;
    }

    /**
     * @param fileInfo
     * @Description :-This method receives the fileInfo object from the
     * startBuilding method.It extracts the list of columnMeta from
     * the fileInfo and convert it into a map.It then retrieves the
     * DataGenerator class from the DatageneratorFactory class
     * according to the data type of each column.Once the
     * appropriate DataGenerator class is received ,list of String
     * is populated with that data-type.Once all data is generated
     * for all the columns it then sends it to the startBuilding
     * method.
     */
    public List<List<String>> populateColumns(FileInfo fileInfo) {

        // final list to be populated
        List<List<String>> columnOfColumns = new ArrayList<List<String>>();


        try {
            // list of columns coming from the UI
            List<ColumnMeta> columnMetas = fileInfo.getListOfColumns();

            Map<Integer, ColumnMeta> myMap = new LinkedHashMap<Integer, ColumnMeta>();

            for (int i = 0; i < columnMetas.size(); i++) {
                myMap.put(i, columnMetas.get(i));
            }

            Set<Entry<Integer, ColumnMeta>> entrySet = myMap.entrySet();


            DatageneratorFactory datageneratorFactory = new DatageneratorFactory();

            for (Entry<Integer, ColumnMeta> entry : entrySet) {

                final String dataType = entry.getValue().getDataType();
                final DataGenerator dataGenerator = datageneratorFactory.getDataGenerator(dataType);

                boolean columnBucketingEnabled = false;
                if (entry.getValue().getBucketedColumn().getColumnMapper().size() == 0) {
                    columnBucketingEnabled = false;
                } else {
                    // for column mapping
                    if ((null != entry.getValue().getBucketedColumn()) && (null != entry.getValue().getBucketedColumn().getBucketedColumnHeader()) && ((entry.getValue().getBucketedColumn().getBucketedColumnHeader()).length() != 0)
                            ) {
                        columnBucketingEnabled = true;
                        ColumnBucketing bucketing = new ColumnBucketing(getNoOfValues());
                        List<List<String>> list = bucketing.buildBucketedColumn(entry.getValue().getBucketedColumn().getColumnMapper());

                        for (int i = 0; i < list.size(); i++) {
                            columnOfColumns.add(list.get(i));
                        }

                    }
                }

                if (!columnBucketingEnabled) {
                    columnOfColumns.add(dataGenerator.getColumn(entry.getValue().getValues(), getNoOfValues()));
                }

            }
        } catch (Exception e) {
            logger.error("Error while populating the final list of lists  " + e);
        }
        return columnOfColumns;
    }

    public int getNoOfValues() {
        return noOfValues;
    }

    public void setNoOfValues(int noOfValues) {
        this.noOfValues = noOfValues;
    }

}
