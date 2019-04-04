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
 * @Description This class generates the random date between the StartDate and
 * the EndDate.The user will have to give the input in the
 * dd/mm/yyyy format only and then he can specify his own desired
 * output format using the # separator . The format is
 * "StartDate-EndDate#outputSimpleDateFormat" for ex:-
 * 01/01/1993-01/01/1994#mm/dd/yyyy
 */
public class DateGenerator implements Constants {

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat(STANDARD_INPUT_FORMAT);
    private static final Logger logger = LoggerFactory.getLogger(DateGenerator.class);

    /**
     * @param input,noOfRows
     * @Description This method is called from the Date class.It receives the
     * number of rows and String input which contains information
     * of the startDate and the endDate followed by the hash
     * separator which contains the output date format which the
     * user expects.It separates the input field into the start
     * date,end date and the separator.It then populates the list
     * with random date between the start date and the end date and
     * then returns the list to the Date class
     */
    public List<String> generateDate(String inputFromUser, int noOfRows) {

        // to split the input from the user
        String[] separator = inputFromUser.split("#");
        String format = separator[1].trim();

        // date format from the user
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);

        String date = separator[0].trim();
        String[] dateBreaker = date.split("-");
        List<String> list = new ArrayList<String>();

        if (dateBreaker.length == 2) {
            try {
                Date from = inputFormat.parse(dateBreaker[0]);
                Date to = inputFormat.parse(dateBreaker[1]);

                long startDate = from.getTime();
                long endDate = to.getTime();

                long diff = endDate - startDate + 1;

                for (int i = 0; i < noOfRows; i++) {

                    Timestamp rand = new Timestamp(startDate + (long) (Math.random() * diff));
                    list.add(outputFormat.format(rand.getTime()));

                }

            } catch (ParseException e) {
                logger.error("Error in Date Generator class " + e);
            }
        }
        return list;

    }

}
