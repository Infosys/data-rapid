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

import java.io.Serializable;

/**
 * @Description :- Contains all the file information such as
 * fileName,numberOfRows,fileType and FileInfo object.
 */
public class JobConfiguration implements Serializable {

    private int configId;
    private String fileName;
    private FileInfo fileInfo;
    private String numberOfRows;
    private String fileType;

    public JobConfiguration() {
        // TODO Auto-generated constructor stub
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
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
     * @return the fileInfo
     */
    public FileInfo getFileInfo() {
        return fileInfo;
    }

    /**
     * @param fileInfo the fileInfo to set
     */
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    /**
     * @return the numberOfRows
     */
    public String getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param numberOfRows the numberOfRows to set
     */
    public void setNumberOfRows(String numberOfRows) {
        this.numberOfRows = numberOfRows;
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

    public JobConfiguration(String fileName, FileInfo fileInfo, String numberOfRows, String fileType) {
        super();
        this.fileName = fileName;
        this.fileInfo = fileInfo;
        this.numberOfRows = numberOfRows;
        this.fileType = fileType;
    }

}
