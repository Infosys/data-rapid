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

package org.datarapid.api.builder;

import org.datarapid.api.dto.ConfigDetails;
import org.datarapid.api.dto.DBDataDetails;
import org.datarapid.api.dto.DataGenResponse;
import org.datarapid.api.dto.FileTransferDetails;
import org.datarapid.api.service.DataGenServiceBo;
import org.datarapid.core.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ResponseBuilder {

    @Autowired
    DataGenServiceBo serviceBo;

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

    private static final Logger log = LoggerFactory.getLogger(ResponseBuilder.class);

    /**
     * @Description :-The method to test the TestService.
     */

    public DataGenResponse processTestResponse(/**/) {

        String testData = serviceBo.processTestData();
        DataGenResponse response = new DataGenResponse();
        response.setMessage(testData);
        return response;
    }

    /**
     * @Description :-The method to get the configuration details from file.
     */

    public ConfigDetails processConfigDetailsRetrieval() {

        ConfigDetails configDetails = new ConfigDetails();

        try {

            configDetails = serviceBo.processConfigDetailsRetrieval();
        } catch (Throwable ex) {
            log.error("Error while data processing config details :", ex);
        }

        return configDetails;
    }

    /**
     * @Description :-The method to get the configuration details from database.
     */

    public DBDataDetails processDBDataRetrieval() {

        try {

            detailsList = serviceBo.processDBDataRetrieval();
        } catch (Throwable ex) {
            log.error("Error while db data retrieval :", ex);
        }

        return detailsList;
    }

    /**
     * @Description :-The method to process the data and generate the file for the version 2.
     */

    public DataGenResponse processData(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.processData(configuration);

        } catch (Throwable ex) {
            log.error("Error while data generating :", ex);
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The method to transfer the file.
     */

    public DataGenResponse processFileTransfer(FileTransferDetails fileTransferDetails) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {
            dataGenResponse = serviceBo.processFileTransfer(fileTransferDetails);
        } catch (Throwable ex) {
            log.error("Error while data transfer :", ex);
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The method to create an user.
     */

    public DataGenResponse createUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.createUser(configuration);

        } catch (Throwable ex) {
            log.error("Error while creating user :", ex);
        }

        return dataGenResponse;
    }


    /**
     * @Description :-The method to update an user.
     */

    public DataGenResponse updateUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.updateUser(configuration);

        } catch (Throwable ex) {
            log.error("Error while updating user :", ex);
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The method to delete an user.
     */

    public DataGenResponse deleteUser(UserConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.deleteUser(configuration);

        } catch (Throwable ex) {
            log.error("Error while deleting user :", ex);
        }

        return dataGenResponse;
    }


    /**
     * @Description :-The method to query the user.
     */
    public UserConfiguration queryUser(String userName) {

        try {

            userConfiguration = serviceBo.queryUser(userName);
        } catch (Throwable ex) {
            log.error("Error while querying user :", ex);
        }

        return userConfiguration;
    }

    /**
     * @Description :-The method to query all user.
     */
    public UserConfiguration queryUsers() {

        try {

            userConfiguration = serviceBo.queryUsers();
        } catch (Throwable ex) {
            log.error("Error while querying users :", ex);
        }

        return userConfiguration;
    }

    /**
     * @Description :-The method to create a role.
     */

    public DataGenResponse createRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.createRole(configuration);

        } catch (Throwable ex) {
            log.error("Error while creating role :", ex);
        }

        return dataGenResponse;
    }


    /**
     * @Description :-The method to update a role.
     */

    public DataGenResponse updateRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.updateRole(configuration);

        } catch (Throwable ex) {
            log.error("Error while updating role :", ex);
        }

        return dataGenResponse;
    }

    /**
     * @Description :-The method to delete a role.
     */

    public DataGenResponse deleteRole(RoleConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.deleteRole(configuration);

        } catch (Throwable ex) {
            log.error("Error while deleting role :", ex);
        }

        return dataGenResponse;
    }


    /**
     * @Description :-The method to query the role.
     */
    public RoleConfiguration queryRole(String userName) {

        try {

            roleConfiguration = serviceBo.queryRole(userName);
        } catch (Throwable ex) {
            log.error("Error while querying role :", ex);
        }

        return roleConfiguration;
    }

    /**
     * @Description :-The method to query all roles.
     */
    public RoleConfiguration queryRoles() {

        try {

            roleConfiguration = serviceBo.queryRoles();
        } catch (Throwable ex) {
            log.error("Error while querying roles :", ex);
        }

        return roleConfiguration;
    }

    /**
     * @Description :-The method for user authentication.
     */

    public DataGenResponse authenticateUser(String userName, String password, String operation) {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            dataGenResponse = serviceBo.authenticateUser(userName, password, operation);
        } catch (Throwable ex) {
            log.error("Error while user authentication :", ex);
        }
        return dataGenResponse;
    }


    /**
     * @Description :-The method for get the logged in user.
     */

    public DataGenResponse getLoggedInUser() {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            dataGenResponse = serviceBo.getLoggedInUser();
        } catch (Throwable ex) {
            log.error("Error while user getLoggedInUser :", ex);
        }
        return dataGenResponse;
    }

    /**
     * @Description :-The method for user logout.
     */

    public DataGenResponse userLogout() {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            dataGenResponse = serviceBo.userLogout();
        } catch (Throwable ex) {
            log.error("Error while user logout :", ex);
        }
        return dataGenResponse;
    }

    /**
     * @Description :-The method to save the configuration.
     */

    public DataGenResponse saveConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            dataGenResponse = serviceBo.saveConfiguration(configuration);
        } catch (Throwable ex) {
            log.error("Error while saving the configuration :", ex);
        }
        return dataGenResponse;
    }


    /**
     * @Description :-The method to query the dataset.
     */
    public JobConfiguration queryDataset(String datasetName) {

        try {

            jobConfiguration = serviceBo.queryDataset(datasetName);
        } catch (Throwable ex) {
            log.error("Error while querying the dataset :", ex);
        }

        return jobConfiguration;
    }

    /**
     * @Description :-The method to query the datasets.
     */
    public DataSetConfigurations queryDatasets() {

        try {

            dataSetConfigurations = serviceBo.queryDatasets();
        } catch (Throwable ex) {
            log.error("Error while querying the dataset :", ex);
        }

        return dataSetConfigurations;
    }

    /**
     * @Description :-The method to update the configuration.
     */

    public DataGenResponse updateConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();
        try {

            dataGenResponse = serviceBo.updateConfiguration(configuration);
        } catch (Throwable ex) {
            log.error("Error while updating the configuration :", ex);
        }
        return dataGenResponse;
    }

    /**
     * @Description :-The method to delete a configuration.
     */

    public DataGenResponse deleteConfiguration(JobConfiguration configuration) {

        DataGenResponse dataGenResponse = new DataGenResponse();

        try {

            dataGenResponse = serviceBo.deleteConfiguration(configuration);

        } catch (Throwable ex) {
            log.error("Error while deleting a configuration :", ex);
        }

        return dataGenResponse;
    }

}
