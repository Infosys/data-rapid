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
import org.datarapid.core.util.RegexGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Generates a list of whole numbers.
 */
public class DigitFormat implements DataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DigitFormat.class);

    public List<String> getColumn(String values, int numRows) {

        List<String> resultColumn = new ArrayList<String>();

        try {
            String regex = "^[1-9]{" + values.trim() + "}$";
            resultColumn = getRegex(regex, numRows);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in DigitFormat class  " + e);
        }

        return resultColumn;
    }

    public List<String> getRegex(String regExPattern, int numRows) {

        List<String> listOfColumns = new ArrayList<String>();

        try {
            RegexGenerator generator = new RegexGenerator();
            listOfColumns = new ArrayList<String>(numRows);
            for (int i = 0; i < numRows; i++) {
                listOfColumns.add(generator.generateFromRegex(regExPattern).toString());

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in DigitFormat class  " + e);
        }
        return listOfColumns;

    }
}
