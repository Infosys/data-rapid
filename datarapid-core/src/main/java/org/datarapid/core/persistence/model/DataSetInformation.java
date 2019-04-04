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
import java.sql.Blob;

/**
 * @Description Bean class for DataSetInformation .
 */

@Entity
@Table(name = "dataset_configuration")
public class DataSetInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "config_id")
    private int configId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "configuration_json")
    private String configurationJson;

    @Column(name = "configuration_details")
    private Blob configurationDetails;

    public DataSetInformation() {
        // TODO Auto-generated constructor stub
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    /**
     * @return the configurationJson
     */
    public String getConfigurationJson() {
        return configurationJson;
    }

    /**
     * @param configurationJson the configurationJson to set
     */
    public void setConfigurationJson(String configurationJson) {
        this.configurationJson = configurationJson;
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
     * @return the configurationDetails
     */
    public Blob getConfigurationDetails() {
        return configurationDetails;
    }

    /**
     * @param configurationDetails the configurationDetails to set
     */
    public void setConfigurationDetails(Blob configurationDetails) {
        this.configurationDetails = configurationDetails;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DataSetInformation [fileName=" + fileName + ", userName=" + userName + ", createdTime=" + createdTime
                + ", fileType=" + fileType + ", configurationJson=" + configurationJson + ", configurationDetails="
                + configurationDetails + "]";
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
        DataSetInformation other = (DataSetInformation) obj;

        if (userName == null) {
            if (other.userName != null)
                return false;
        }

        return true;
    }

}
