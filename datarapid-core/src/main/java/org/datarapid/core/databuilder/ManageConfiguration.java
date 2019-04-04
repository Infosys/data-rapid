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

package org.datarapid.core.databuilder;

import com.google.gson.Gson;
import org.datarapid.core.common.SpringContext;
import org.datarapid.core.persistence.model.DataSetInformation;
import org.datarapid.core.persistence.transactionservice.DataSetInfoService;
import org.datarapid.core.security.SecurityUtility;
import org.datarapid.core.util.CommonUtils;
import org.datarapid.core.view.JobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description :- This class is for managing the data set configuration.
 */

public class ManageConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ManageConfiguration.class);

    public ManageConfiguration() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Description For creating the data set configuration
     */

    @Autowired
    private DataSetInformation dataSetInformation;

    public boolean createDataSetConfiguration(JobConfiguration configuration) throws IOException {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        CommonUtils commonUtils = new CommonUtils();
        byte[] byteArray = null;
        Blob dataSetBlob = null;

        if (configuration.getConfigId() ==0){
            dataSetInformation =  new DataSetInformation();
        }
        SecurityUtility securityUtility = SecurityUtility.getInstance();
        dataSetInformation.setUserName(securityUtility.getCurrentUser());
        dataSetInformation.setFileName(configuration.getFileName());
        dataSetInformation.setCreatedTime(dateFormat.format(date));
        dataSetInformation.setFileType(configuration.getFileType());
        Gson gson = new Gson();
        String json = gson.toJson(configuration);
        dataSetInformation.setConfigurationJson(json);

		/* Setting the blob data*/

        try {
            byteArray = commonUtils.serialize(configuration);
            dataSetBlob = new SerialBlob(byteArray);

        } catch (SQLException e) {
            logger.error("Error in setting the blob createDataSetConfiguration" + e);
        }
        dataSetInformation.setConfigurationDetails(dataSetBlob);

        boolean createDataSet = infoService.addDataSet(dataSetInformation);

        return createDataSet;

    }

    /**
     * @Description For deleting the dataset
     */

    public boolean deleteDataSet(JobConfiguration configuration) {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        dataSetInformation.setFileName(configuration.getFileName());

        boolean dataSetDel = infoService.deleteDataSet(dataSetInformation);

        return dataSetDel;

    }

    /**
     * @Description For updating the user dataSet
     */

    public boolean updateDataSet(DataSetInformation configuration) {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        boolean dataSetUpdate = infoService.updateDataSet(configuration);

        return dataSetUpdate;

    }

    /**
     * @Description For querying the data set
     */

    public List<DataSetInformation> queryDataSet(String fileName) {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        List<DataSetInformation> info = infoService.getDataSet(fileName);

        return info;
    }

    /**
     * @Description For querying all dataset information
     */

    public List<DataSetInformation> queryAllDataSets() {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        List<DataSetInformation> infos = infoService.listDataSets();

        return infos;

    }

    public boolean updateDataSetConfiguration(JobConfiguration configuration) throws IOException {

        DataSetInfoService infoService = SpringContext.getBean("dataSetInfoService");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        CommonUtils commonUtils = new CommonUtils();
        byte[] byteArray = null;
        Blob dataSetBlob = null;

        SecurityUtility securityUtility = SecurityUtility.getInstance();
        dataSetInformation.setUserName(securityUtility.getCurrentUser());

        dataSetInformation.setFileName(configuration.getFileName());
        dataSetInformation.setCreatedTime(dateFormat.format(date));
        dataSetInformation.setFileType(configuration.getFileType());
        Gson gson = new Gson();
        String json = gson.toJson(configuration);
        dataSetInformation.setConfigurationJson(json);
								
		/* Setting the blob data*/
        byteArray = commonUtils.serialize(configuration.getFileInfo());
        try {
            dataSetBlob = new SerialBlob(byteArray);

        } catch (SQLException e) {
            logger.error("Error in setting the blob createDataSetConfiguration" + e);
        }
        dataSetInformation.setConfigurationDetails(dataSetBlob);

        boolean updateDataSet = infoService.updateDataSet(dataSetInformation);

        return updateDataSet;

    }

}
