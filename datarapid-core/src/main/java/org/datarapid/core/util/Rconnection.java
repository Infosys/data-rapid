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

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description This class makes a connection to R and retrieves the values from
 * R for the user defined function.
 */
public class Rconnection {

    RConnection rconnection = null;
    final static Logger logger = LoggerFactory.getLogger(Rconnection.class);

    /**
     * @param inputRange,noOfRows
     * @Description This method generates the unique keys from R.The user needs
     * to specify the input range such as 1-1000.Primary key or
     * unique values will be generated accordingly.
     */
    public List<String> createUniquekeys(String inputRange, int noOfRows) {
        List<String> list = new ArrayList<String>();

        try {

            rconnection = new RConnection();

            int inter = Integer.valueOf(inputRange.split("to")[1].trim()) - Integer.valueOf(inputRange.split("to")[0].trim());

            if (noOfRows == inter) {
                REXP x = rconnection.eval("seq(" + inputRange.split("to")[0].trim() + "," + inputRange.split("to")[1].trim() + ",by=1)");
                int[] a = x.asIntegers();

                for (int i = 0; i < x.length(); i++) {
                    list.add(String.valueOf(a[i]));
                }

            } else {

                int inter2 = Integer.valueOf(inputRange.split("to")[1].trim()) - Integer.valueOf(inputRange.split("to")[0].trim());
                int inter3 = noOfRows - inter2;
                int q = Integer.valueOf(inputRange.split("to")[1].trim()) + inter3;

                REXP x = rconnection.eval("seq(" + inputRange.split("to")[0].trim() + "," + String.valueOf(q) + ",by=1)");
                int[] a = x.asIntegers();

                for (int i = 0; i < x.length(); i++) {
                    list.add(String.valueOf(a[i]));
                }
            }

        } catch (Exception e) {
            logger.error("Error in Get UniqueKeys from R !!!", e);
        } finally {
            if (rconnection != null) {
                try {
                    rconnection.close();
                } finally {
                    // TODO Comments
                }

            }
        }
        return list;

    }

    /**
     * @param rCommandFromUser,noOfRows {@link #isConnected}
     * @Description This method takes the R-command from the user and executes
     * that function in R returns back the result set from which
     * the list is populated.
     */

    public List<String> getValuesFromR(String rCommandFromUser, int noOfRows) {

        List<String> list = new ArrayList<String>();

        try {

            PropertyUtil propertyUtil = new PropertyUtil();
            Properties property = propertyUtil.loadPropertyFromClasspath("application.properties");
            String rconnectip = property.getProperty("rconnectip");
            int rconnectport = Integer.parseInt(property.getProperty("rconnectport"));

            rconnection = new RConnection(rconnectip, rconnectport);

            REXP x = rconnection.eval(rCommandFromUser);

            double[] a = x.asDoubles();

            for (int j = 0; j < noOfRows; j++) {
                list.add(String.valueOf(a[j]));
            }

        } catch (Exception e) {
            logger.error("Error in Get Values from R !!!", e);

        } finally {
            if (rconnection != null) {
                try {
                    rconnection.close();
                } finally {
                }

            }
        }
        return list;
    }
}
