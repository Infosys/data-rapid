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

package org.datarapid.core.services;

import org.datarapid.core.view.*;

import java.util.List;
import java.util.Map;

/**
 * @Description :- Interface being implemented by DataGenCoreServiceImpl.
 * Contains method buildData to build all columns, retrieveData to retireve the UI related information
 */

public interface DataGenCoreService {

    /**
     * @Description : For building /generating the data
     */
    public String buildData(JobConfiguration configuration) throws Exception;

    /**
     * @Description : For retrieving the data related to UI
     */

    public Map<String, List<String>> retrieveData(UIConfiguration configuration) throws Exception;

    /**
     * @Description : For User Specific Operations
     */

    public boolean userCreate(UserConfiguration configuration) throws Exception;

    public boolean userUpdate(UserConfiguration configuration) throws Exception;

    public UserConfiguration userQuery(String userName) throws Exception;

    public UserConfiguration allUserQuery() throws Exception;

    public boolean userDelete(UserConfiguration configuration) throws Exception;

    /**
     * @Description : For User Role Specific Operations
     */

    public boolean userRoleCreate(RoleConfiguration configuration) throws Exception;

    public boolean userRoleUpdate(RoleConfiguration configuration) throws Exception;

    public RoleConfiguration userRoleQuery(String userName) throws Exception;

    public RoleConfiguration allUserRoleQuery() throws Exception;

    public boolean userRoleDelete(RoleConfiguration configuration) throws Exception;

    /**
     * @Description : For Session Related Operations
     */

    public boolean authenticateUser(String userName, String password, String operation) throws Exception;

    public boolean userLogout() throws Exception;

    public String getLoggedInUser() throws Exception;

    /**
     * @Description : For data set configuration related Operations
     */

    public boolean saveConfiguration(JobConfiguration configuration) throws Exception;

    public JobConfiguration dataSetQuery(String datasetName) throws Exception;

    public DataSetConfigurations dataSetsQuery() throws Exception;

    public boolean updateConfiguration(JobConfiguration configuration) throws Exception;

    public boolean dataSetConfigurationDelete(JobConfiguration configuration) throws Exception;

}
