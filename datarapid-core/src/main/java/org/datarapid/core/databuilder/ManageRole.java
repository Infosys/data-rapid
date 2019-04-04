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

import org.datarapid.core.common.SpringContext;
import org.datarapid.core.persistence.model.RoleInformation;
import org.datarapid.core.persistence.transactionservice.RoleInfoService;
import org.datarapid.core.util.CommonUtils;
import org.datarapid.core.view.RoleConfiguration;
import org.datarapid.core.view.RoleInfo;
import org.datarapid.core.view.RoleMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description :- This class is for managing user role.
 */

public class ManageRole {

    private static final Logger logger = LoggerFactory.getLogger(ManageRole.class);

    public ManageRole() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Description For creating the user role
     */

    @Autowired
    private RoleInformation roleInformation;

    public boolean createRole(RoleConfiguration configuration) {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");
        RoleInfo roleInfo = configuration.getRoleInfo();
        List<RoleMeta> roleMeta = roleInfo.getRoleMeta();
        RoleMeta element = roleMeta.get(0);
        if (element.getUserAccessId()== 0){
            roleInformation =  new RoleInformation();
        }
        roleInformation.setUserName(element.getUserName());
        roleInformation.setRoleName(element.getRoleName());
        roleInformation.setRoleDesc(element.getRoleDesc());
        roleInformation.setRoleType(element.getRoleType());
        roleInformation.setGroupUsers(element.getGroupUsers());
        roleInformation.setUsageType(element.getUsageType());
        roleInformation.setStartDate(element.getStartDate());
        roleInformation.setEndDate(element.getEndDate());
        roleInformation.setRecordCountLimit(element.getRecordCountLimit());
        roleInformation.setUsageLimit(element.getUsageLimit());
        roleInformation.setDaysLimit(element.getDaysLimit());

        ManageRole manageRole = new ManageRole();
        roleInformation = manageRole.setDefaultValues(roleInformation);

        boolean roleAdd = infoService.addRole(roleInformation);

        return roleAdd;

    }

    /**
     * @Description For deleting the user role
     */

    public boolean deleteRole(RoleConfiguration configuration) {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");
        RoleInfo roleInfo = configuration.getRoleInfo();
        List<RoleMeta> roleMeta = roleInfo.getRoleMeta();
        RoleMeta element = roleMeta.get(0);
        roleInformation.setUserName(element.getUserName());

        boolean roleDel = infoService.deleteRole(roleInformation);

        return roleDel;
    }

    /**
     * @Description For updating the user role
     */

    public boolean updateRole(RoleConfiguration configuration) {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");
        RoleInfo roleInfo = configuration.getRoleInfo();
        List<RoleMeta> roleMeta = roleInfo.getRoleMeta();
        RoleMeta element = roleMeta.get(0);
        roleInformation.setUserName(element.getUserName());
        roleInformation.setRoleName(element.getRoleName());
        roleInformation.setRoleDesc(element.getRoleDesc());
        roleInformation.setRoleType(element.getRoleType());
        roleInformation.setGroupUsers(element.getGroupUsers());
        roleInformation.setUsageType(element.getUsageType());
        roleInformation.setStartDate(element.getStartDate());
        roleInformation.setEndDate(element.getEndDate());
        roleInformation.setRecordCountLimit(element.getRecordCountLimit());
        roleInformation.setUsageLimit(element.getUsageLimit());
        roleInformation.setDaysLimit(element.getDaysLimit());

        ManageRole manageRole = new ManageRole();
        roleInformation = manageRole.setDefaultValues(roleInformation);

        boolean roleUpdate = infoService.updateRole(roleInformation);

        return roleUpdate;
    }

    /**
     * @Description For querying the user role
     */

    public List<RoleInformation> queryRole(String userName) {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");

        List<RoleInformation> info = infoService.getRole(userName);

        return info;
    }

    /**
     * @Description For querying all user role information
     */

    public List<RoleInformation> queryAllRoles() {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");

        List<RoleInformation> infos = infoService.listRoles();

        return infos;

    }

    public RoleInformation setDefaultValues(RoleInformation roleInformation) {

        CommonUtils commonUtils = new CommonUtils();

        if (commonUtils.isNull(roleInformation.getRoleName())) {
            roleInformation.setRoleName(commonUtils.getDefaultRoleName());
        }

        if (commonUtils.isNull(roleInformation.getUsageType())) {
            roleInformation.setUsageType(commonUtils.getDefaultUsageType());
        }
        if (commonUtils.isNull(roleInformation.getStartDate())) {
            roleInformation.setStartDate(commonUtils.getSysDate());
        }
        if (commonUtils.isNull(roleInformation.getEndDate())) {
            roleInformation.setEndDate(commonUtils.getHighDate());
        }
        if (commonUtils.isNull(roleInformation.getRecordCountLimit())) {
            roleInformation.setRecordCountLimit(commonUtils.getMaxRecordCountLimit());
        }
        if (commonUtils.isNull(roleInformation.getUsageLimit())) {
            roleInformation.setUsageLimit(commonUtils.getMaxUsageLimit());
        }

        if (commonUtils.isNull(roleInformation.getDaysLimit())) {
            roleInformation.setDaysLimit(commonUtils.getMaxDaysLimit());
        } else {
            if (!roleInformation.getDaysLimit().equals(commonUtils.getMaxDaysLimit())) {
                roleInformation.setEndDate(commonUtils.addDaysString(roleInformation.getStartDate(), roleInformation.getDaysLimit()));
            }
        }

        return roleInformation;
    }


    /**
     * @Description For checking whether the role is active or not
     */

    public boolean isRoleActive(String userName) {

        RoleInfoService infoService = SpringContext.getBean("roleInfoService");

        boolean roleStatus = infoService.isRoleActive(userName);

        return roleStatus;
    }


}
