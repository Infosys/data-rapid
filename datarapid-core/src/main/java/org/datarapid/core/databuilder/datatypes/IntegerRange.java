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
import org.datarapid.core.util.IntegerAndFloatRangeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Returns the list of random integers between the startValue and
 * the EndValue.User will have to give input in the format
 * "startvalue to endvalue"
 */
public class IntegerRange implements DataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IntegerRange.class);

    /**
     * @param values,numRows
     * @Description invokes the getRange method and returns the list of integer
     * range
     */
    public List<String> getColumn(String values, int numRows) {
        List<String> list = getRange(values.trim(), numRows);
        return list;
    }

    /**
     * @param values,numRows
     * @Description Populates the list with integer range and return the list.
     */
    public List<String> getRange(String value, int numRows) {
        List<String> list = new ArrayList<String>();

        try {
            IntegerAndFloatRangeGenerator generator = new IntegerAndFloatRangeGenerator();

            for (int i = 0; i < numRows; i++) {
                list.add(generator.generateRandomInteger(value));
            }
        } catch (Exception e) {
            logger.error("Error in IntegerRange class  " + e);
        }
        return list;
    }

}
