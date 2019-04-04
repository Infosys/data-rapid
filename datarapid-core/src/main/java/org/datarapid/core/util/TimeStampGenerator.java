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
 * @Description The user will have to give the input in the dd/mm/yyyy format
 * only.The user will get the oracle timestamp between the two
 * given dates.
 */
public class TimeStampGenerator implements Constants {


    private static final Logger logger = LoggerFactory.getLogger(TimeStampGenerator.class);

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat(STANDARD_INPUT_FORMAT);

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

        String[] dateBreaker = inputFromUser.split("-");
        List<String> list = new ArrayList<String>();

        if (dateBreaker.length == 2) {
            try {
                Date fromDate = inputFormat.parse(dateBreaker[0]);
                Date toDate = inputFormat.parse(dateBreaker[1]);

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

    public static void main(String[] args) {
        TimeStampGenerator dateGenerator = new TimeStampGenerator();
        dateGenerator.generateTimestamp("01/01/2014-01/01/2016", 1000);

    }

}
