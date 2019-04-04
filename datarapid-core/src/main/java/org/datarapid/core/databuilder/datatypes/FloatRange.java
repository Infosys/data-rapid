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
 * @Description This class generates the random float values between the range
 * given by the user. The user should give the input in the format
 * "startValue to endValue".
 */

public class FloatRange implements DataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(FloatRange.class);

    /**
     * @param values,numRows
     * @Description invokes the getRange method and returns the list of float
     * range
     */
    public List<String> getColumn(String values, int numRows) {

        List<String> list = getRange(values.trim(), numRows);
        return list;
    }

    /**
     * @param values,numRows
     * @Description Populates the list with float range and return the list.
     */
    public List<String> getRange(String value, int numRows) {
        List<String> list = new ArrayList<String>();

        try {
            IntegerAndFloatRangeGenerator generator = new IntegerAndFloatRangeGenerator();

            for (int i = 0; i < numRows; i++) {
                list.add(generator.generateRandomFloat(value));
            }
        } catch (Exception e) {
            logger.error("Error in Float range class  " + e);

        }
        return list;
    }
}
