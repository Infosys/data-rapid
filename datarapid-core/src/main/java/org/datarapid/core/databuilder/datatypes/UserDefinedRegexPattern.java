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
 * @Description Generates a list of values from the user defined regex pattern.
 */
public class UserDefinedRegexPattern implements DataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedRegexPattern.class);

    public List<String> getColumn(String values, int numRows) {

        List<String> list = getRegex(values, numRows);

        return list;
    }

    public List<String> getRegex(String regExPattern, int numRows) {

        List<String> listOfColumns = new ArrayList<String>(numRows);

        try {
            RegexGenerator generator = new RegexGenerator();
            for (int i = 0; i < numRows; i++) {
                final String regExPattern2 = "^" + regExPattern + "$";
                listOfColumns.add(generator.generateFromRegex(regExPattern2).toString());

            }
        } catch (Exception e) {
            logger.error("Error in UserDefiendRegexPattern datatype  " + e);
        }
        return listOfColumns;

    }

    ;

}
