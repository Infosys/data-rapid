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

import org.datarapid.core.databuilder.factory.DatageneratorFactory;
import org.datarapid.core.view.UIColumnMeta;
import org.datarapid.core.view.UIConfiguration;
import org.datarapid.core.view.UIFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * @Description :- This class is used to retrieving the data for UI.
 */

public class RetrieveData {

    private int dataLimit;

    private static final Logger logger = LoggerFactory.getLogger(RetrieveData.class);


    public RetrieveData() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param configuration
     * @Description :-This method is used to retrieving the data for UI related to the database related datatypes.
     */
    public Map<String, List<String>> startRetrieving(UIConfiguration configuration) {

        UIFileInfo fileInfo = configuration.getUiFileInfo();
        Map<String, List<String>> dataMap = populateColumns(fileInfo);

        return dataMap;
    }

    /**
     * @param fileInfo
     * @Description :-This method is used to populated the data in predefined format.
     */
    public Map<String, List<String>> populateColumns(UIFileInfo fileInfo) {

        // final list to be populated
        List<List<String>> columnOfColumns = new ArrayList<List<String>>();
        Map<String, List<String>> dataMap = new HashMap<String, List<String>>();

        try {

            List<UIColumnMeta> columnMetas = fileInfo.getListOfColumns();
            Map<Integer, UIColumnMeta> myMap = new LinkedHashMap<Integer, UIColumnMeta>();
            for (int i = 0; i < columnMetas.size(); i++) {
                myMap.put(i, columnMetas.get(i));
            }
            Set<Entry<Integer, UIColumnMeta>> entrySet = myMap.entrySet();
            DatageneratorFactory datageneratorFactory = new DatageneratorFactory();

            for (Entry<Integer, UIColumnMeta> entry : entrySet) {

                final String dataType = entry.getValue().getDataTypeName();
                final DataGenerator dataGenerator = datageneratorFactory.getDataGenerator(dataType);
                String dataLimit = entry.getValue().getDataLimit();
                int intdataLimit = Integer.parseInt(dataLimit);
                //columnOfColumns.add(dataGenerator.getColumn(dataType, intdataLimit));
                List<String> data = dataGenerator.getColumn(dataType, intdataLimit);
                columnOfColumns.add(data);
                dataMap.put(dataType, data);
            }

        } catch (Exception e) {
            logger.error("Error while populating the final list of lists  " + e);
        }
        return dataMap;
    }


    public int getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(int dataLimit) {
        this.dataLimit = dataLimit;
    }

}
