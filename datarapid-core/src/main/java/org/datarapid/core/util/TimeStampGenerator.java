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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description The user can give the date range in the format
 * "StartDate-EndDate#outputSimpleDateFormat".
 * The user will get the oracle timestamp between the two
 * given dates.
 */
public class TimeStampGenerator implements Constants {

    private static final Logger logger = LoggerFactory.getLogger(TimeStampGenerator.class);

    /**
     * @param inputFromUser,noOfRows
     * @Description This method is called from the Timestamp class.It receives
     * the number of rows and String input which contains
     * information of the startDate and the endDate.It separates
     * the input field into the start date and end date.It then
     * populates the list with random oracle timestamp generated
     * between the two dates.
     */
    public List<String> generateTimestamp(String inputFromUser, int noOfRows) {

        String[] separator = inputFromUser.split("#");
        String format = separator[1].trim();
        // date format from the user
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);
        String date = separator[0].trim();
        String[] dateBreaker = date.split("to");
        List<String> list = new ArrayList<String>();
        if (dateBreaker.length == 2) {
            try {
                Date fromDate = outputFormat.parse(dateBreaker[0]);
                Date toDate = outputFormat.parse(dateBreaker[1]);
                long startDate = fromDate.getTime();
                long endDate = toDate.getTime();
                long diff = endDate - startDate + 1;
                for (int i = 0; i < noOfRows; i++) {
                    Timestamp rand = new Timestamp(startDate + (long) (Math.random() * diff));
                    list.add(String.valueOf(rand.getTime()));
                }
            } catch (ParseException e) {
                logger.error("Error in TimeStampGenerator datatype  " + e);
            }
        }
        return list;
    }
}
