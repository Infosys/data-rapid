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

package org.datarapid.core.persistence.dao;

import org.datarapid.core.persistence.model.RoleInformation;
import org.datarapid.core.util.CommonUtils;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description :-Generic DAO class to get/post role information from the
 * database.
 */
@Repository("dataGeneratorRoleDAO")
public class DataGeneratorRoleDAOImpl extends AbstractDao implements DataGeneratorRoleDAO {
    private static final Logger logger = LoggerFactory.getLogger(DataGeneratorRoleDAOImpl.class);
    @SuppressWarnings("unchecked")
    public <T> List<T> listRoles() {

        final List roleInfo = getSession().createQuery("from RoleInformation").list();
        return (List<T>) roleInfo;

    }

    public boolean addRole(RoleInformation roleInformation) {

        getSession().persist(roleInformation);
        return true;
    }

    public <T> List<T> getRole(String userName) {
        // TODO Auto-generated method stub
        @SuppressWarnings("rawtypes")
        final List roleInfo = getSession().createQuery("from RoleInformation where user_name=?").setString(0, userName)
                .list();
        return (List<T>) roleInfo;
    }

    public boolean deleteRole(RoleInformation roleInformation) {
        // TODO Auto-generated method stub
        getSession().delete(roleInformation);
        return true;
    }

    public boolean updateRole(RoleInformation roleInformation) {
        // TODO Auto-generated method stub
        getSession().update(roleInformation);
        return true;
    }

    public boolean isRoleActive(String userName) {

        boolean roleActiveStatus = false;
        String currentLoggedInUser = userName;
        String sql = "SELECT user_name,active_status,IFNULL(processed_record_count,0) AS processed_record_count,IFNULL(processed_usage,0) AS processed_usage,group_users,record_count_limit,usage_limit,role_type " +
                "FROM (SELECT CASE WHEN  (CURDATE() >= DATE(start_date) AND CURDATE() <= DATE(end_date) AND  ur.record_count_limit > al.processed_record_count AND ur.usage_limit > al.processed_usage) THEN 'Y' WHEN  (al.triggered_user_name IS NULL) THEN 'Y' ELSE 'N' END AS active_status,al.*,ur.* " +
                "FROM user_roles ur LEFT JOIN (SELECT SUM(processed_record_count) processed_record_count,SUM(processed_usage) processed_usage ,triggered_user_name FROM activity_log GROUP BY triggered_user_name ) al ON (ur.user_name = al.triggered_user_name) ) details";
        SQLQuery query = getSession().createSQLQuery(sql);
        List<Object[]> roleDetails = query.list();
        if (roleDetails.size() > 0) {
            for (Object[] obj : roleDetails) {
                String user = (String) obj[0];
                if (user.equals(currentLoggedInUser)) {
                    String activeStatus = (String) obj[1];
                    String groupUsers = (String) obj[4];
                    String loggedInUserRoleType = (String) obj[7];
                    if (activeStatus.equals("Y")) {
                        CommonUtils commonUtils = new CommonUtils();
                        // Checking for child group user
                        if (loggedInUserRoleType.equalsIgnoreCase("group")) {
                            if (commonUtils.isNull(groupUsers)) {
                                // Get the logged in group users parent id
                                for (Object[] innerObj : roleDetails) {
                                    String parentUser = (String) innerObj[0];
                                    String innerGroupUsers = (String) innerObj[4];
                                    if (!commonUtils.isNull(innerGroupUsers)) {
                                        innerGroupUsers = innerGroupUsers + "," + parentUser;
                                        String[] splitter = innerGroupUsers.split(",");
                                        BigDecimal totalProcessedRecordCount = new BigDecimal(0);
                                        BigDecimal totalProcessedUsage = new BigDecimal(0);
                                        BigDecimal individualRecordCountLimit = new BigDecimal(0);
                                        BigDecimal groupRecordCountLimit = new BigDecimal(0);
                                        BigDecimal individualUsageLimit = new BigDecimal(0);
                                        BigDecimal groupUsageLimit = new BigDecimal(0);
                                        BigDecimal individualProcessedRecordCount = new BigDecimal(0);
                                        BigDecimal individualProcessedUsage = new BigDecimal(0);

                                        for (int i = 0; i < splitter.length; i++) {
                                            String innerGroupuser = splitter[i];
                                            for (Object[] innerRoleObj : roleDetails) {

                                                String innerRoleUser = (String) innerRoleObj[0];
                                                if (innerRoleUser.equals(innerGroupuser)) {
                                                    BigDecimal individualRecordCount = new BigDecimal(0);
                                                    BigDecimal individualUsage = new BigDecimal(0);
                                                    if ((BigDecimal) innerRoleObj[2] != null) {
                                                        individualRecordCount = (BigDecimal) innerRoleObj[2];
                                                    }
                                                    if ((BigDecimal) innerRoleObj[3] != null) {
                                                        individualUsage = (BigDecimal) innerRoleObj[3];
                                                    }
                                                    totalProcessedRecordCount = totalProcessedRecordCount
                                                            .add(individualRecordCount);
                                                    totalProcessedUsage = totalProcessedUsage.add(individualUsage);
                                                }
                                                if (innerRoleUser.equals(currentLoggedInUser)) {
                                                    individualRecordCountLimit = (BigDecimal) innerRoleObj[5];
                                                    individualUsageLimit = (BigDecimal) innerRoleObj[6];
                                                    if ((BigDecimal) innerRoleObj[2] != null) {
                                                        individualProcessedRecordCount = (BigDecimal) innerRoleObj[2];
                                                    }
                                                    if ((BigDecimal) innerRoleObj[3] != null) {
                                                        individualProcessedUsage = (BigDecimal) innerRoleObj[3];
                                                    }
                                                }

                                                if (innerRoleUser.equals(parentUser)) {
                                                    groupRecordCountLimit = (BigDecimal) innerRoleObj[5];
                                                    groupUsageLimit = (BigDecimal) innerRoleObj[6];
                                                }

                                            }

                                        }

                                        // Check for group users limit
                                        if (totalProcessedRecordCount.compareTo(groupRecordCountLimit) <= 0
                                                && totalProcessedUsage.compareTo(groupUsageLimit) <= 0) {
                                            logger.info(
                                                    "Condition Satisfied for the user Group: Usage is within the allowed limit ");
                                            // Check for individual user limit as well
                                            if (individualProcessedRecordCount
                                                    .compareTo(individualRecordCountLimit) <= 0
                                                    && individualProcessedUsage.compareTo(individualUsageLimit) <= 0) {
                                                logger.info(
                                                        "Condition Satisfied for the user Individual: Usage is within the allowed limit ");
                                                roleActiveStatus = true;
                                            }

                                        }

                                    }

                                }

                            } else {  // Since already the group parent user is active
                                roleActiveStatus = true;
                            }
                        }

                        // Checking the grand total of all users in the group
                        else if ((!commonUtils.isNull(groupUsers))
                                && (loggedInUserRoleType.equalsIgnoreCase("group"))) {

                            BigDecimal totalProcessedRecordCount = (BigDecimal) obj[2];
                            BigDecimal totalProcessedUsage = (BigDecimal) obj[3];
                            BigDecimal recordCountLimit = (BigDecimal) obj[5];
                            BigDecimal usageLimit = (BigDecimal) obj[6];

                            String[] splitter = groupUsers.split(",");

                            for (int i = 0; i < splitter.length; i++) {
                                String groupuser = splitter[i];
                                for (Object[] innerObj : roleDetails) {

                                    String innerUser = (String) innerObj[0];
                                    if (innerUser.equals(groupuser)) {
                                        BigDecimal individualProcessedRecordCount = new BigDecimal(0);
                                        BigDecimal individualProcessedUsage = new BigDecimal(0);
                                        if ((BigDecimal) innerObj[2] != null) {
                                            individualProcessedRecordCount = (BigDecimal) innerObj[2];
                                        }
                                        if ((BigDecimal) innerObj[3] != null) {
                                            individualProcessedUsage = (BigDecimal) innerObj[3];
                                        }
                                        totalProcessedRecordCount = totalProcessedRecordCount
                                                .add(individualProcessedRecordCount);
                                        totalProcessedUsage = totalProcessedUsage.add(individualProcessedUsage);
                                    }
                                }
                            }
                            if (totalProcessedRecordCount.compareTo(recordCountLimit) <= 0
                                    && totalProcessedUsage.compareTo(usageLimit) <= 0) {
                                logger.info("Condition Satisfied  : Usage is within the allowed limit ");
                                roleActiveStatus = true;
                            }
                        } else {
                            roleActiveStatus = true;
                        }
                    }
                }

            }
        }
        return roleActiveStatus;
    }
}
