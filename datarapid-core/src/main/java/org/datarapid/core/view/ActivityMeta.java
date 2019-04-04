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

package org.datarapid.core.view;

/**
 * @Description:-Contains information of activity details.
 */
public class ActivityMeta {

    private int logId;
    private String triggeredUserName;
    private String activityDate;
    private String generatedFileName;
    private String processedRecordCount;
    private String processedUsage;

    /**
     * @return the logId
     */
    public int getLogId() {
        return logId;
    }


    /**
     * @param logId the logId to set
     */
    public void setLogId(int logId) {
        this.logId = logId;
    }


    /**
     * @return the triggeredUserName
     */
    public String getTriggeredUserName() {
        return triggeredUserName;
    }


    /**
     * @param triggeredUserName the triggeredUserName to set
     */
    public void setTriggeredUserName(String triggeredUserName) {
        this.triggeredUserName = triggeredUserName;
    }


    /**
     * @return the activityDate
     */
    public String getActivityDate() {
        return activityDate;
    }


    /**
     * @param activityDate the activityDate to set
     */
    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }


    /**
     * @return the generatedFileName
     */
    public String getGeneratedFileName() {
        return generatedFileName;
    }


    /**
     * @param generatedFileName the generatedFileName to set
     */
    public void setGeneratedFileName(String generatedFileName) {
        this.generatedFileName = generatedFileName;
    }


    /**
     * @return the processedRecordCount
     */
    public String getProcessedRecordCount() {
        return processedRecordCount;
    }


    /**
     * @param processedRecordCount the processedRecordCount to set
     */
    public void setProcessedRecordCount(String processedRecordCount) {
        this.processedRecordCount = processedRecordCount;
    }


    /**
     * @return the processedUsage
     */
    public String getProcessedUsage() {
        return processedUsage;
    }


    /**
     * @param processedUsage the processedUsage to set
     */
    public void setProcessedUsage(String processedUsage) {
        this.processedUsage = processedUsage;
    }


    public ActivityMeta() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param logId
     * @param triggeredUserName
     * @param activityDate
     * @param generatedFileName
     * @param processedRecordCount
     * @param processedUsage
     */
    public ActivityMeta(int logId, String triggeredUserName, String activityDate, String generatedFileName,
                        String processedRecordCount, String processedUsage) {
        super();
        this.logId = logId;
        this.triggeredUserName = triggeredUserName;
        this.activityDate = activityDate;
        this.generatedFileName = generatedFileName;
        this.processedRecordCount = processedRecordCount;
        this.processedUsage = processedUsage;
    }

}
