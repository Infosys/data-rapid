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

package org.datarapid.core.persistence.model;

import javax.persistence.*;

/**
 * @Description Bean class for ActivityLogInformation .
 */

@Entity
@Table(name = "activity_log")
public class ActivityLogInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private int logId;

    @Column(name = "triggered_user_name")
    private String triggeredUserName;

    @Column(name = "activity_date")
    private String activityDate;

    @Column(name = "generated_file_name")
    private String generatedFileName;

    @Column(name = "processed_record_count")
    private String processedRecordCount;

    @Column(name = "processed_usage")
    private String processedUsage;

    public ActivityLogInformation() {
        // TODO Auto-generated constructor stub
    }

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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ActivityLogInformation [logId=" + logId + ", triggeredUserName=" + triggeredUserName + ", activityDate="
                + activityDate + ", generatedFileName=" + generatedFileName + ", processedRecordCount="
                + processedRecordCount + ", processedUsage=" + processedUsage + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActivityLogInformation other = (ActivityLogInformation) obj;

        if (triggeredUserName == null) {
            if (other.triggeredUserName != null)
                return false;
        }

        return true;
    }


}
