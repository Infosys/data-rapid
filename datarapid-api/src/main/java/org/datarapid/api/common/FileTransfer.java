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

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.datarapid.core.exception.DataGenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;

public class FileTransfer {

    private static final Logger log = LoggerFactory.getLogger(FileTransfer.class);

    public static void main(String[] args) {

        FileTransfer fileTransfer = new FileTransfer();
        //fileTransfer.startFTPUpload("Test1.txt","ip","user","password","/path/","D:\\test\\download\\","upload");
//		fileTransfer.startHDFSFileCopy("D:\\test\\download\\Test.csv","/usr/test");		
    }

    public String startFTPDownload(String fileName, String host, String userId, String password,
                                   String remoteDirectory, String localDirectory, String fileTransferType) {

        StandardFileSystemManager manager = new StandardFileSystemManager();

        String filedownloadMessage = null;

        try {

            fileName = fileName.trim();
            host = host.trim();
            userId = userId.trim();
            password = password.trim();
            remoteDirectory = remoteDirectory.trim();
            localDirectory = localDirectory.trim();

            // Initializes the file manager
            manager.init();

            // Setup our SFTP configuration
            FileSystemOptions opts = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
            SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

            // Create the SFTP URI using the host name, userid, password, remote
            // path and file name
            String sftpUri = "sftp://" + userId + ":" + password + "@" + host + "/" + remoteDirectory + fileName;

            // Create local file object
            String filepath = localDirectory + fileName;
            File file = new File(filepath);
            FileObject localFile = manager.resolveFile(file.getAbsolutePath());

            // Create remote file object
            FileObject remoteFile = manager.resolveFile(sftpUri, opts);

            // Copy local file to sftp server
            localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
            log.debug("File download successful");

        } catch (Throwable ex) {
            ex.printStackTrace();
            log.error("Exception in File download : ", ex);
            filedownloadMessage = "Failure in download for the file : " + fileName + ": Exception :" + ex;
            return filedownloadMessage;
        } finally {
            manager.close();
        }

        filedownloadMessage = "File download successful for the file : " + fileName;
        return filedownloadMessage;
    }

    public String startFTPUpload(String fileName, String host, String userId, String password, String remoteDirectory,
                                 String localDirectory, String fileTransferType) throws DataGenException {

        StandardFileSystemManager manager = new StandardFileSystemManager();

        String fileUploadMessage = null;

        try {

            fileName = fileName.trim();
            host = host.trim();
            userId = userId.trim();
            password = password.trim();
            remoteDirectory = remoteDirectory.trim();
            localDirectory = localDirectory.trim();

            // check if the file exists
            String filepath = localDirectory + fileName;
            File file = new File(filepath);
            if (!file.exists())
                throw new RuntimeException("Error. Local file not found");

            // Initializes the file manager
            manager.init();

            // Setup our SFTP configuration
            FileSystemOptions opts = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
            SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

            // Create the SFTP URI using the host name, userid, password, remote
            // path and file name
            String sftpUri = fileTransferType + "://" + userId + ":" + password + "@" + host + "/" + remoteDirectory + "/" + fileName;

            // Create local file object
            FileObject localFile = manager.resolveFile(file.getAbsolutePath());

            // Create remote file object
            FileObject remoteFile = manager.resolveFile(sftpUri, opts);

            // Copy local file to sftp server
            remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
            log.debug("File upload successful");

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception in File download : ", ex);
            fileUploadMessage = "Failure in Upload for the file : " + fileName + ": Exception :" + ex;
            throw new DataGenException(fileUploadMessage);

        } finally {
            manager.close();
        }
        fileUploadMessage = "File Upload successful for the file : " + fileName;
        return fileUploadMessage;
    }


    public String startHDFSFileCopy(String localSrc, String dst) {

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
            Configuration conf = new Configuration();
            conf.addResource(new Path("D:\\test\\conf\\core-site.xml"));
            conf.addResource(new Path("D:\\test\\conf\\hdfs-site.xml"));
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            OutputStream out = fs.create(new Path(dst), new Progressable() {
                public void progress() {
                    log.debug("Progress");
                }
            });
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (Exception ex) {
            log.error("Exception in HDFS Copy : ", ex);
        }
        return "";
    }

}
