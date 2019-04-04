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

package org.datarapid.api.common;

import org.datarapid.api.dto.FileTransferDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileTransferConfigReader {

    private static final Logger log = LoggerFactory.getLogger(FileTransferConfigReader.class);

    public FileTransferConfigReader() {
        // TODO Auto-generated constructor stub
    }

    //Loading contents from the file into a {@link java.util.Properties}

    public FileTransferDetails readConfigParameters(FileTransferDetails fileTransferDetails) {

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {

            String fileName = "application.properties";

            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (null == stream) {
                log.error("{} is not available in classpath", fileName);
                throw new IOException(fileName + " is not available in classpath");
            }

            properties.load(stream);

            if (null == fileTransferDetails.getDefaultFileTransferType() || (fileTransferDetails.getDefaultFileTransferType()).isEmpty()) {
                fileTransferDetails.setDefaultFileTransferType(properties.getProperty("defaultFileTransferType"));
            }

            if (null == fileTransferDetails.getFileName() || (fileTransferDetails.getFileName()).isEmpty()) {
                fileTransferDetails.setFileName(properties.getProperty("fileName"));
            }

            if (null == fileTransferDetails.getHost() || (fileTransferDetails.getHost()).isEmpty()) {
                fileTransferDetails.setHost(properties.getProperty("host"));
            }

            if (null == fileTransferDetails.getUserId() || (fileTransferDetails.getUserId()).isEmpty()) {
                fileTransferDetails.setUserId(properties.getProperty("userId"));
            }

            if (null == fileTransferDetails.getPassword() || (fileTransferDetails.getPassword()).isEmpty()) {
                fileTransferDetails.setPassword(properties.getProperty("password"));
            }

            if (null == fileTransferDetails.getRemoteDirectory() || (fileTransferDetails.getRemoteDirectory()).isEmpty()) {
                fileTransferDetails.setRemoteDirectory(properties.getProperty("remoteDirectory"));
            }

            if (null == fileTransferDetails.getLocalDirectory() || (fileTransferDetails.getLocalDirectory()).isEmpty()) {
                fileTransferDetails.setLocalDirectory(properties.getProperty("localDirectory"));
            }

            if (null == fileTransferDetails.getFileTransferType() || (fileTransferDetails.getFileTransferType()).isEmpty()) {
                fileTransferDetails.setFileTransferType(properties.getProperty("fileTransferType"));
                if (null == fileTransferDetails.getFileTransferType() || (fileTransferDetails.getFileTransferType()).isEmpty()) {
                    fileTransferDetails.setFileTransferType(properties.getProperty("defaultFileTransferType"));
                }
            }

            log.debug("Property defaultFileTransferType : ", fileTransferDetails.getDefaultFileTransferType());


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileTransferDetails;
    }

}