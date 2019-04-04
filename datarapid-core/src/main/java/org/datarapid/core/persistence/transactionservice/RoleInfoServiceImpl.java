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

package org.datarapid.core.persistence.transactionservice;

import org.datarapid.core.persistence.dao.DataGeneratorRoleDAO;
import org.datarapid.core.persistence.model.RoleInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description This class implements RoleInfoService interface,performs the
 * transaction and returns the result-set.
 */

@Service("roleInfoService")
@Transactional
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private DataGeneratorRoleDAO dao;

    /**
     * @Description To retrieve all the roles
     */
    public List<RoleInformation> listRoles() {

        return dao.listRoles();

    }

    /**
     * @Description To add a role
     */
    public boolean addRole(RoleInformation roleInformation) {

        return dao.addRole(roleInformation);

    }

    /**
     * @Description To retrieve a specific role
     */
    public List<RoleInformation> getRole(String userName) {

        return dao.getRole(userName);

    }

    /**
     * @Description To delete the role
     */
    public boolean deleteRole(RoleInformation roleInformation) {

        return dao.deleteRole(roleInformation);

    }


    /**
     * @Description To update an role
     */
    public boolean updateRole(RoleInformation roleInformation) {

        return dao.updateRole(roleInformation);

    }


    /**
     * @Description checking whether the role is active or not
     */
    public boolean isRoleActive(String userName) {

        return dao.isRoleActive(userName);

    }

}
