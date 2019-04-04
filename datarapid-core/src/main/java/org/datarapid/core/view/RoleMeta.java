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

package org.datarapid.core.view;

/**
 * @Description :- This class contains information about the role meta information.
 */
public class RoleMeta {

    private int userAccessId;
    private String userName;
    private String roleName;
    private String roleDesc;
    private String roleType;
    private String groupUsers;
    private String usageType;
    private String startDate;
    private String endDate;
    private String recordCountLimit;
    private String usageLimit;
    private String daysLimit;

    public RoleMeta() {
        // TODO Auto-generated constructor stub
    }

    public int getUserAccessId() {
        return userAccessId;
    }

    public void setUserAccessId(int userAccessId) {
        this.userAccessId = userAccessId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * @param roleDesc the roleDesc to set
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * @return the groupUsers
     */
    public String getGroupUsers() {
        return groupUsers;
    }

    /**
     * @param groupUsers the groupUsers to set
     */
    public void setGroupUsers(String groupUsers) {
        this.groupUsers = groupUsers;
    }

    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * @return the usageType
     */
    public String getUsageType() {
        return usageType;
    }

    /**
     * @param usageType the usageType to set
     */
    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the recordCountLimit
     */
    public String getRecordCountLimit() {
        return recordCountLimit;
    }

    /**
     * @param recordCountLimit the recordCountLimit to set
     */
    public void setRecordCountLimit(String recordCountLimit) {
        this.recordCountLimit = recordCountLimit;
    }

    /**
     * @return the usageLimit
     */
    public String getUsageLimit() {
        return usageLimit;
    }

    /**
     * @param usageLimit the usageLimit to set
     */
    public void setUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
    }

    /**
     * @return the daysLimit
     */
    public String getDaysLimit() {
        return daysLimit;
    }

    /**
     * @param daysLimit the daysLimit to set
     */
    public void setDaysLimit(String daysLimit) {
        this.daysLimit = daysLimit;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RoleMeta [userName=" + userName + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", roleType="
                + roleType + ", groupUsers=" + groupUsers + ", usageType=" + usageType + ", startDate=" + startDate
                + ", endDate=" + endDate + ", recordCountLimit=" + recordCountLimit + ", usageLimit=" + usageLimit
                + ", daysLimit=" + daysLimit + "]";
    }

    /**
     * @param userName
     * @param roleName
     * @param roleDesc
     * @param roleType
     * @param groupUsers
     * @param usageType
     * @param startDate
     * @param endDate
     * @param recordCountLimit
     * @param usageLimit
     * @param daysLimit
     */
    public RoleMeta(String userName, String roleName, String roleDesc, String roleType, String groupUsers,
                    String usageType, String startDate, String endDate, String recordCountLimit, String usageLimit,
                    String daysLimit) {
        super();
        this.userName = userName;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.roleType = roleType;
        this.groupUsers = groupUsers;
        this.usageType = usageType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recordCountLimit = recordCountLimit;
        this.usageLimit = usageLimit;
        this.daysLimit = daysLimit;
    }


}
