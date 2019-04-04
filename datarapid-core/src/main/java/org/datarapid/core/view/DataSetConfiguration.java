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
 * @Description :- Contains all the dataset information
 */
public class DataSetConfiguration {

    private String fileName;
    private String fileType;
    private String userName;
    private String createdTime;
    private JobConfiguration jobConfiguration;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return the jobConfiguration
     */
    public JobConfiguration getJobConfiguration() {
        return jobConfiguration;
    }

    /**
     * @param jobConfiguration the jobConfiguration to set
     */
    public void setJobConfiguration(JobConfiguration jobConfiguration) {
        this.jobConfiguration = jobConfiguration;
    }


    //private List<JobConfiguration> jobConfiguration;

    /**
     * @return the jobConfiguration
     */
    /*public List<JobConfiguration> getJobConfiguration() {
		return jobConfiguration;
	}*/

    /**
     * @param jobConfiguration the jobConfiguration to set
     */
	/*public void setJobConfiguration(List<JobConfiguration> jobConfiguration) {
		this.jobConfiguration = jobConfiguration;
	}*/


}
