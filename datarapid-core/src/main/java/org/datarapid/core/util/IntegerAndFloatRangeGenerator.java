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

package org.datarapid.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Description This class will generate the random values from the user given
 * set.It also generates the random integer and float range from
 * the given range.
 */
public class IntegerAndFloatRangeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IntegerAndFloatRangeGenerator.class);
    private static final Random rand = new Random();

    /**
     * @param integerRange
     * @Description This method generates the random integer between the range
     * given from the user. The user should give the input in the
     * format "startValue to endValue".
     */
    public String generateRandomInteger(String integerRange) {
        try {
            String[] arg = integerRange.split("to");
            int min = Integer.valueOf(arg[0].trim());
            int max = Integer.valueOf(arg[1].trim());
            int a = min + (int) (Math.random() * ((max - min) + 1));
            return String.valueOf(a);
        } catch (NumberFormatException e) {
            logger.error("Error in the input pattern for the Random Integer generation " + e);
        }
        return null;

    }

    /**
     * @param floatRange
     * @Description This method generates the random float values between the
     * range given from the user. The user should give the input in
     * the format "startValue to endValue".
     */
    public String generateRandomFloat(String floatRange) {

        try {
            String[] arg = floatRange.split("to");
            float min = Integer.valueOf(arg[0].trim());
            float max = Integer.valueOf(arg[1].trim());
            float finalX = rand.nextFloat() * (max - min) + min;
            return String.valueOf(finalX);

        } catch (NumberFormatException e) {
            logger.error("Error in the input pattern for the Random float generation " + e);
        }
        return null;
    }

}
