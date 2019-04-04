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

package org.datarapid.api.service.impl;

import org.datarapid.api.common.ConfigReader;
import org.datarapid.api.common.FileTransfer;
import org.datarapid.api.common.FileTransferConfigReader;
import org.datarapid.api.dto.ConfigDetails;
import org.datarapid.api.dto.DBDataDetails;
import org.datarapid.api.dto.DataGenResponse;
import org.datarapid.api.dto.FileTransferDetails;
import org.datarapid.api.service.DataGenServiceBo;
import org.datarapid.core.services.DataGenCoreService;
import org.datarapid.core.util.Messages;
import org.datarapid.core.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class DataGenServiceBoImpl implements DataGenServiceBo, Messages {

    @Autowired
    DataGenCoreService datagenCoreService;

    @Autowired
    DBDataDetails detailsList;

    @Autowired
    UserConfiguration userConfiguration;

    @Autowired
    RoleConfiguration roleConfiguration;

    @Autowired
    JobConfiguration jobConfiguration;

    @Autowired
    DataSetConfiguration dataSetConfiguration;

    @Autowired
    DataSetConfigurations dataSetConfigurations;

    @Value("${dbTypeDropDownLimit}")
    private String dbTypeDropDownLimit;

    private static final Logger log = LoggerFactory.getLogger(DataGenServiceBoImpl.class);

    /**
     * @Description :-The test service method.
     */

    public String processTestData() {

        return "Data Generation Test Service";

    }

    /**
     * @Description :-The service method to get the configuration parameters for version 1.Not used in the latest version.
     */
    public ConfigDetails processConfigDetailsRetrieval() {

        ConfigDetails configDetails = new ConfigDetails();

        try {
            ConfigReader conf = new ConfigReader();
            configDetails = conf.readConfigParameters();
        } catch (Exception ex) {
            log.error("Error while data generating processConfigDetailsRetrieval :", ex);
        }

        return configDetails;

    }

    /**
     * @Description :-The service method to get the UI related dropdown data from database.
     */
    public DBDataDetails processDBDataRetrieval() {

        String dataLimit = dbTypeDropDownLimit;
        UIColumnMeta meta1 = new UIColumnMeta("firstname", dataLimit);
        UIColumnMeta meta2 = new UIColumnMeta("lastname", dataLimit);
        UIColumnMeta meta3 = new UIColumnMeta("emailaddress", dataLimit);
        UIColumnMeta meta4 = new UIColumnMeta("zipcode", dataLimit);
        UIColumnMeta meta5 = new UIColumnMeta("city", dataLimit);
        UIColumnMeta meta6 = new UIColumnMeta("state", dataLimit);
        UIColumnMeta meta7 = new UIColumnMeta("country", dataLimit);
        UIColumnMeta meta8 = new UIColumnMeta("location", dataLimit);

        List<UIColumnMeta> list = new ArrayList<UIColumnMeta>();
        list.add(meta1);
        list.add(meta2);
        list.add(meta3);
        list.add(meta4);
        list.add(meta5);
        list.add(meta6);
        list.add(meta7);
        list.add(meta8);

        UIFileInfo fileInfo = new UIFileInfo(list);
        UIConfiguration configuration = new UIConfiguration("getalldetails", dataLimit, fileInfo);

        try {
            detailsList.setDbDatadetailsList(datagenCoreService.retrieveData(configuration));
        } catch (Exception e) {
            log.error("Error while getting db data processDBDataRetrieval :", e);
        }

        return detailsList;

    }

    /**
     * @Description :-The service method to transfer the file.
     */
    public DataGenResponse processFileTransfer(FileTransferDetails fileTransferDetails) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            FileTransferConfigReader conf = new FileTransferConfigReader();
            fileTransferDetails = conf.readConfigParameters(fileTransferDetails);
            String fileTransferMessage = null;
            FileTransfer fileTransfer = new FileTransfer();

            if (fileTransferDetails.getDefaultFileTransferType().equalsIgnoreCase("download")) {
                fileTransferMessage = fileTransfer.startFTPDownload(fileTransferDetails.getFileName(),
                        fileTransferDetails.getHost(), fileTransferDetails.getUserId(),
                        fileTransferDetails.getPassword(), fileTransferDetails.getRemoteDirectory(),
                        fileTransferDetails.getLocalDirectory(), fileTransferDetails.getFileTransferType());
            }
            if (fileTransferDetails.getDefaultFileTransferType().equalsIgnoreCase("upload")) {
                fileTransferMessage = fileTransfer.startFTPUpload(fileTransferDetails.getFileName(),
                        fileTransferDetails.getHost(), fileTransferDetails.getUserId(),
                        fileTransferDetails.getPassword(), fileTransferDetails.getRemoteDirectory(),
                        fileTransferDetails.getLocalDirectory(), fileTransferDetails.getFileTransferType());
            }

            dataGenResponse.setDownloadURL(null);
            dataGenResponse.setMessage(fileTransferMessage);

        } catch (Throwable ex) {
            log.error("Error while data upload/download : processFileTransfer", ex);
            dataGenResponse.setMessage(ex.toString());
            dataGenResponse.setErrorCode("ERR-002");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to generate the data.
     */
    public DataGenResponse processData(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {
            String responseURL = datagenCoreService.buildData(configuration);
            dataGenResponse.setDownloadURL(responseURL);
            //dataGenResponse.setMessage("Data Generation Processing Successfully Executed");
            dataGenResponse.setMessage(responseURL);

        } catch (Exception ex) {
            log.error("Error while data generating : processGenAPIData", ex);
            dataGenResponse.setMessage(ex.toString());
            dataGenResponse.setErrorCode("ERROR-001");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to create an user.
     */
    public DataGenResponse createUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isUserCreated = datagenCoreService.userCreate(configuration);

            if (isUserCreated) {
                dataGenResponse.setMessage(SUCCESS_002);
            } else {
                dataGenResponse.setMessage(ERROR_002);
            }

        } catch (Exception ex) {
            log.error("Error while creating the user : createUser", ex);
            dataGenResponse.setMessage(ERROR_002 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-002");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to update an user.
     */
    public DataGenResponse updateUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isUserUpdated = datagenCoreService.userUpdate(configuration);

            if (isUserUpdated) {
                dataGenResponse.setMessage(SUCCESS_003);
            } else {
                dataGenResponse.setMessage(ERROR_003);
            }

        } catch (Exception ex) {
            log.error("Error while updatng the user : updateUser", ex);
            dataGenResponse.setMessage(ERROR_003 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-003");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to delete an user.
     */
    public DataGenResponse deleteUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isUserdeleted = datagenCoreService.userDelete(configuration);

            if (isUserdeleted) {
                dataGenResponse.setMessage(SUCCESS_004);
            } else {
                dataGenResponse.setMessage(ERROR_004);
            }

        } catch (Exception ex) {
            log.error("Error while deleting the user : deleteUser", ex);
            dataGenResponse.setMessage(ERROR_004 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-004");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to query an user.
     */
    public UserConfiguration queryUser(String userName) {

        try {

            userConfiguration = datagenCoreService.userQuery(userName);

        } catch (Exception ex) {
            log.error("Error while querying the user : queryUser", ex);
        }

        return userConfiguration;

    }

    /**
     * @Description :-The service method to query all user.
     */
    public UserConfiguration queryUsers() {

        try {

            userConfiguration = datagenCoreService.allUserQuery();

        } catch (Exception ex) {
            log.error("Error while querying the user : queryUsers", ex);
        }

        return userConfiguration;

    }

    /**
     * @Description :-The service method to create a role.
     */
    public DataGenResponse createRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isRoleCreated = datagenCoreService.userRoleCreate(configuration);

            if (isRoleCreated) {
                dataGenResponse.setMessage(SUCCESS_005);
            } else {
                dataGenResponse.setMessage(ERROR_005);
            }

        } catch (Exception ex) {
            log.error("Error while creating the role : createRole", ex);
            dataGenResponse.setMessage(ERROR_005 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-005");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to update a role.
     */
    public DataGenResponse updateRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isRoleUpdated = datagenCoreService.userRoleUpdate(configuration);

            if (isRoleUpdated) {
                dataGenResponse.setMessage(SUCCESS_006);
            } else {
                dataGenResponse.setMessage(ERROR_006);
            }

        } catch (Exception ex) {
            log.error("Error while updating the role : updateRole", ex);
            dataGenResponse.setMessage(ERROR_006 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-006");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to delete a role.
     */
    public DataGenResponse deleteRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isRoledeleted = datagenCoreService.userRoleDelete(configuration);

            if (isRoledeleted) {
                dataGenResponse.setMessage(SUCCESS_007);
            } else {
                dataGenResponse.setMessage(ERROR_007);
            }

        } catch (Exception ex) {
            log.error("Error while deleting the role : deleteRole", ex);
            dataGenResponse.setMessage(ERROR_007 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-007");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service method to query a role.
     */
    public RoleConfiguration queryRole(String userName) {

        try {

            roleConfiguration = datagenCoreService.userRoleQuery(userName);

        } catch (Exception ex) {
            log.error("Error while querying the role : queryRole", ex);
        }

        return roleConfiguration;

    }

    /**
     * @Description :-The service method to query all role.
     */
    public RoleConfiguration queryRoles() {

        try {

            roleConfiguration = datagenCoreService.allUserRoleQuery();

        } catch (Exception ex) {
            log.error("Error while querying the roles : queryRoles", ex);
        }

        return roleConfiguration;

    }

    /**
     * @Description :-The service for authenticating the user.
     */
    public DataGenResponse authenticateUser(String userName, String password, String operation) {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            boolean status = datagenCoreService.authenticateUser(userName, password, operation);

            if (status) {

                dataGenResponse.setMessage(SUCCESS_008);
            } else {

                dataGenResponse.setMessage(ERROR_008);
            }

        } catch (Exception ex) {
            log.error("Error while authenticating the user : authenticateUser", ex);
            dataGenResponse.setMessage(ERROR_008 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-008");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service for getting the user id.
     */
    public DataGenResponse getLoggedInUser() {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            String userId = datagenCoreService.getLoggedInUser();
            dataGenResponse.setDownloadURL(userId);
            dataGenResponse.setMessage(SUCCESS_013);

        } catch (Exception ex) {
            log.error("Error while getting the logged in user : getLoggedInUser", ex);
            dataGenResponse.setMessage(ERROR_013 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-013");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service for user logout.
     */
    public DataGenResponse userLogout() {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            boolean status = datagenCoreService.userLogout();

            if (status) {

                dataGenResponse.setMessage(SUCCESS_009);
            } else {

                dataGenResponse.setMessage(ERROR_009);
            }

        } catch (Exception ex) {
            log.error("Error while user log out : userLogout", ex);
            dataGenResponse.setMessage(ERROR_009 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-009");
        }

        return dataGenResponse;

    }

    /**
     * @Description :-The service to save the configuration.
     */
    public DataGenResponse saveConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {
            boolean saveStatus = datagenCoreService.saveConfiguration(configuration);

            if (saveStatus) {

                dataGenResponse.setMessage(SUCCESS_010);
            } else {

                dataGenResponse.setMessage(ERROR_010);
            }

        } catch (Exception ex) {
            log.error("Error while saving the configuration : saveConfiguration", ex);
            dataGenResponse.setMessage(ex.toString());
            dataGenResponse.setErrorCode("ERROR-010");
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The service method to query a dataset.
     */
    public JobConfiguration queryDataset(String datasetName) {

        try {

            jobConfiguration = datagenCoreService.dataSetQuery(datasetName);

        } catch (Exception ex) {
            log.error("Error while querying the dataset : queryDataset", ex);
        }

        return jobConfiguration;

    }

    /**
     * @Description :-The service method to query a datasets.
     */
    public DataSetConfigurations queryDatasets() {

        try {

            dataSetConfigurations = datagenCoreService.dataSetsQuery();

        } catch (Exception ex) {
            log.error("Error while querying the datasets : queryDatasets", ex);
        }

        return dataSetConfigurations;

    }

    /**
     * @Description :-The service to update the configuration.
     */
    public DataGenResponse updateConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {
            boolean updateStatus = datagenCoreService.updateConfiguration(configuration);

            if (updateStatus) {

                dataGenResponse.setMessage(SUCCESS_011);
            } else {

                dataGenResponse.setMessage(ERROR_011);
            }

        } catch (Exception ex) {
            log.error("Error while updating the configuration : updateConfiguration", ex);
            dataGenResponse.setMessage(ex.toString());
            dataGenResponse.setErrorCode("ERROR-011");
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The service method to delete a dataset configuration.
     */
    public DataGenResponse deleteConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            boolean isDataSetConfigDeleted = datagenCoreService.dataSetConfigurationDelete(configuration);

            if (isDataSetConfigDeleted) {
                dataGenResponse.setMessage(SUCCESS_012);
            } else {
                dataGenResponse.setMessage(ERROR_012);
            }

        } catch (Exception ex) {
            log.error("Error while deleting the dataset configuration : deleteConfiguration", ex);
            dataGenResponse.setMessage(ERROR_012 + " : " + ex.toString());
            dataGenResponse.setErrorCode("ERROR-012");
        }

        return dataGenResponse;

    }

}
