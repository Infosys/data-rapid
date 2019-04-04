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

package org.datarapid.core.databuilder.datatypes;

import org.datarapid.core.databuilder.DataGenerator;
import org.datarapid.core.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description This class will generate a list with random values from the user
 * defined set.
 */
public class DefaultSet implements DataGenerator, Constants {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSet.class);

    public List<String> getColumn(String userInputSet, int numRows) {

        List<String> list = generateDefaultSet(userInputSet, numRows);
        return list;
    }

    public List<String> generateDefaultSet(String userInputSet, int noOfValues) {

        List<String> list = new ArrayList<String>();

        try {
            String[] setValues = userInputSet.split(COMMA_SPLIT);

            int min = 0;
            int max = setValues.length - 1;

            for (int i = 0; i < noOfValues; i++) {

                list.add(setValues[min + (int) (Math.random() * ((max - min) + 1))]);

            }
        } catch (Exception e) {

            logger.error("Error in Default Set Class " + e);
        }
        return list;

    }

}
