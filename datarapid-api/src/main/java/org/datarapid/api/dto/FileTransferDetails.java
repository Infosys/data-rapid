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

package org.datarapid.api.dto;


public class FileTransferDetails {

    public FileTransferDetails() {
        // TODO Auto-generated constructor stub
    }

    private String defaultFileTransferType;
    private String fileName;
    private String host;
    private String userId;
    private String password;
    private String remoteDirectory;
    private String localDirectory;
    private String fileTransferType;

    public FileTransferDetails(String defaultFileTransferType, String fileName, String host, String userId, String password, String remoteDirectory, String localDirectory, String fileTransferType) {
        this.defaultFileTransferType = defaultFileTransferType;
        this.fileName = fileName;
        this.host = host;
        this.userId = userId;
        this.password = password;
        this.remoteDirectory = remoteDirectory;
        this.localDirectory = localDirectory;
        this.fileTransferType = fileTransferType;
    }

    public String getFileTransferType() {
        return fileTransferType;
    }

    public void setFileTransferType(String fileTransferType) {
        this.fileTransferType = fileTransferType;
    }

    public String getDefaultFileTransferType() {
        return defaultFileTransferType;
    }

    public void setDefaultFileTransferType(String defaultFileTransferType) {
        this.defaultFileTransferType = defaultFileTransferType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

}
