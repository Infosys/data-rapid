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

public class IncrementalUniqueValues implements DataGenerator, Constants {

    // user input will be of the format 10-100,2 incrementing value

    private static final Logger logger = LoggerFactory.getLogger(AmericanCard.class);

    public List<String> getColumn(String values, int numRows) {
        return getRegex(values.trim(), numRows);
    }

    public List<String> getRegex(String values, int numRows) {

        List<String> listOfColumns = new ArrayList<String>(numRows);

        try {
            String[] splitter = values.split(",");
            String[] lowerLimit = splitter[0].split("to");
            int startingValue = Integer.valueOf(lowerLimit[0].trim());
            int incrementBy = Integer.valueOf(splitter[1].trim());
            for (int i = 0; i < numRows; i++) {
                listOfColumns.add(String.valueOf(startingValue));
                startingValue = startingValue + incrementBy;

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Error in IncrementalUniqueValues.The input format entered is wrong");
        }
        return listOfColumns;

    }
}
