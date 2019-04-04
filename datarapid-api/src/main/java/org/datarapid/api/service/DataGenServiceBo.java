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

package org.datarapid.api.service;

import org.datarapid.api.dto.ConfigDetails;
import org.datarapid.api.dto.DBDataDetails;
import org.datarapid.api.dto.DataGenResponse;
import org.datarapid.api.dto.FileTransferDetails;
import org.datarapid.core.view.DataSetConfigurations;
import org.datarapid.core.view.JobConfiguration;
import org.datarapid.core.view.RoleConfiguration;
import org.datarapid.core.view.UserConfiguration;

public interface DataGenServiceBo {

    String processTestData();

    ConfigDetails processConfigDetailsRetrieval();

    DBDataDetails processDBDataRetrieval();

    DataGenResponse processFileTransfer(FileTransferDetails fileTransferDetails);

    DataGenResponse processData(JobConfiguration configuration);

    DataGenResponse createUser(UserConfiguration configuration);

    DataGenResponse updateUser(UserConfiguration configuration);

    DataGenResponse deleteUser(UserConfiguration configuration);

    UserConfiguration queryUser(String userName);

    UserConfiguration queryUsers();

    DataGenResponse createRole(RoleConfiguration configuration);

    DataGenResponse updateRole(RoleConfiguration configuration);

    DataGenResponse deleteRole(RoleConfiguration configuration);

    RoleConfiguration queryRole(String userName);

    RoleConfiguration queryRoles();

    DataGenResponse authenticateUser(String userName, String password, String operation);

    DataGenResponse userLogout();

    DataGenResponse saveConfiguration(JobConfiguration configuration);

    JobConfiguration queryDataset(String datasetName);

    DataSetConfigurations queryDatasets();

    DataGenResponse updateConfiguration(JobConfiguration configuration);

    DataGenResponse deleteConfiguration(JobConfiguration configuration);

    DataGenResponse getLoggedInUser();

}