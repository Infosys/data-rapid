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

package org.datarapid.core.databuilder.bucketing;

import org.datarapid.core.databuilder.DataGenerator;
import org.datarapid.core.databuilder.factory.DatageneratorFactory;
import org.datarapid.core.view.ColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description This class performs the column bucketing function where-in you
 * give in the parent column and then map the relation to the child
 * column.
 */

public class ColumnBucketing {

    private int noOfRows;
    private static final Logger logger = LoggerFactory.getLogger(ColumnBucketing.class);

    public ColumnBucketing(int noOfRows) {
        this.noOfRows = noOfRows;
    }

    /**
     * @param columnMapper
     * @Description This method receives the list of ColumnMapper and returns
     * the parent column as well as the bucketed column.
     */
    public List<List<String>> buildBucketedColumn(List<ColumnMapper> columnMapper) {

        List<List<String>> finalList = new ArrayList<List<String>>();
        try {
            List<String> parentList = new ArrayList<String>();
            List<String> childList = new ArrayList<String>();

            for (int i = 0; i < columnMapper.size(); i++) {

                childList.addAll(buildChildColumn(columnMapper.get(i)));
                parentList.addAll(buildParentColumn(columnMapper.get(i)));
            }

            finalList.add(parentList);
            finalList.add(childList);
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            logger.error("Error in bucketed column:-Array index out of bounds  " + e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in bucketed column  " + e);
        }
        return finalList;

    }

    /**
     * @param columnMapper
     * @Description This method receives the list of ColumnMapper and returns
     * the bucketed column.
     */
    public List<String> buildChildColumn(ColumnMapper columnMapper) {
        List<String> list = new ArrayList<String>();

        try {
            DatageneratorFactory datageneratorFactory = new DatageneratorFactory();
            final DataGenerator dataGenerator = datageneratorFactory.getDataGenerator(columnMapper.getDataType());
            int noOfRows2 = noOfRows * Integer.valueOf(columnMapper.getPercentageOfrows()) / 100;
            list.addAll(dataGenerator.getColumn(columnMapper.getValues(), noOfRows2));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in bucketed child column  " + e);
        }
        return list;

    }

    /**
     * @param columnMapper
     * @Description This method receives the list of ColumnMapper and returns
     * the parent column.
     */
    public List<String> buildParentColumn(ColumnMapper columnMapper) {
        List<String> list = new ArrayList<String>();

        try {
            int noOfRows2 = Integer.valueOf(columnMapper.getPercentageOfrows()) * noOfRows / 100;
            for (int i = 0; i < noOfRows2; i++) {
                list.add(columnMapper.getValuesFromSet());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in bucketed parent column  " + e);
        }
        return list;

    }
}
