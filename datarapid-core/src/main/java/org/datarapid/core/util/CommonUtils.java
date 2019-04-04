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

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description This class is used to add the common functions that can be used in the datagen
 */
public class CommonUtils implements Constants {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public CommonUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Description :-This method to get the sysdate
     */
    public String getSysDate() {

        String sysDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_OUTPUT_FORMAT);
            Date date = new Date();
            sysDate = dateFormat.format(date);
        } catch (Exception ex) {
            logger.error("Error in getting sysdate " + ex);
        }
        return sysDate;
    }

    /**
     * @Description :-This method to get the highdate
     */
    public String getHighDate() {

        return HIGHDATE;

    }

    /**
     * @Description :-This method to get the default role name
     */
    public String getDefaultRoleName() {

        return DEFAULT_ROLE_NAME;

    }

    /**
     * @Description :-This method to get the default role name
     */
    public String getDefaultUsageType() {

        return DEFAULT_USAGE_TYPE;

    }

    /**
     * @Description :-This method to get the max record count
     */
    public String getMaxRecordCountLimit() {

        return MAX_RECORD_COUNT_LIMIT;

    }

    /**
     * @Description :-This method to get the max usage limit
     */
    public String getMaxUsageLimit() {

        return MAX_USAGE_LIMIT;

    }

    /**
     * @Description :-This method to get the max usage limit
     */
    public String getMaxDaysLimit() {

        return MAX_DAYS_LIMIT;

    }


    /**
     * @Description :-This method is used to get the file size
     */
    public static long getFileSize(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            logger.error("File doesn\'t exist");
            return -1;
        }
        return file.length();
    }

    /**
     * @Description :-This method is used to add no : of days from a date
     */

    public Date addDays(Date date, int days) {

        Date outdate = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            outdate = cal.getTime();
        } catch (Exception ex) {
            logger.error("Error in getting addDays " + ex);
        }
        return outdate;
    }

    /**
     * @Description :-This method is used to add no : of days from a date
     */

    public String addDaysString(String sourceDate, String days) {

        String modifiedDate = null;
        try {

            CommonUtils commonUtils = new CommonUtils();
            DateFormat format = new SimpleDateFormat(TIMESTAMP_OUTPUT_FORMAT);
            Date date = format.parse(sourceDate);
            date = commonUtils.addDays(date, Integer.parseInt(days));
            modifiedDate = format.format(date);

        } catch (Exception ex) {
            logger.error("Error in getting addDaysString " + ex);
        }
        return modifiedDate;
    }


    /**
     * @Description :-This method is used to do the null check
     */

    public boolean isNull(String strInput) {

        boolean isNull = false;
        if (strInput == null || strInput.equals("") || strInput == "") {
            isNull = true;
        }
        return isNull;
    }

    /**
     * @throws IOException
     * @Description :-This method is used to convert any object into byte array
     */
    public static byte[] serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        return byteArrayOutputStream.toByteArray();

    }

    /**
     * @Description :-This method is used to convert byte array into object
     */
    public static Object deserialize(byte[] bytes) throws Exception {

    	/*ObjectInputStream objectInputStream = null;
        try{
        	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        	objectInputStream = new ObjectInputStream(byteArrayInputStream);
        }
        catch (Exception ex){
        	logger.error("Error in converting ByteArray to Object-  deserialize "+ex);
        }
        return objectInputStream.readObject();
        
        */
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;


    }

    public static void main(String[] args) {
        try {
            CommonUtils commonUtils = new CommonUtils();
            String strDate = commonUtils.getSysDate();
            System.out.println(strDate);

            String strModified = commonUtils.addDaysString("2016-02-29 15:34:17", "30");
            System.out.println(strModified);
        } catch (Exception ex) {
            //
            logger.error("Error in main " + ex);

        }


    }
}
