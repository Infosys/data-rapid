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
import org.datarapid.core.persistence.model.UserInformation;
import org.datarapid.core.persistence.transactionservice.UserInfoService;
import org.datarapid.core.security.SecurityUtility;
import org.datarapid.core.view.UserConfiguration;
import org.datarapid.core.view.UserInfo;
import org.datarapid.core.view.UserMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description :- This class is for managing user.
 */

public class ManageUser {

    private static final Logger logger = LoggerFactory.getLogger(ManageUser.class);

    public ManageUser() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Description For creating the user
     */

    @Autowired
    private UserInformation userInformation;

    @Value("${datagenadmin}")
    private String datagenadmin;

    public boolean createUser(UserConfiguration configuration) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        SecurityUtility securityUtility = SecurityUtility.getInstance();

        UserInfoService infoService = SpringContext.getBean("userInfoService");
        UserInfo userInfo = configuration.getUserInfo();
        List<UserMeta> userMeta = userInfo.getUserMeta();
        UserMeta element = userMeta.get(0);
        if (element.getUserId()== 0){
            userInformation =  new UserInformation();
        }
        userInformation.setUserName(element.getUserName());
        String encPassword = securityUtility.getEncryptedPassword(element.getPassword());
        userInformation.setPassword(encPassword);
        userInformation.setUserDesc(element.getUserDesc());
        userInformation.setCreatedBy(datagenadmin);
        userInformation.setCreatedTime(dateFormat.format(date));

        boolean userAdd = infoService.addUser(userInformation);

        return userAdd;

    }

    /**
     * @Description For deleting the user
     */

    public boolean deleteUser(UserConfiguration configuration) {

        UserInfoService infoService = SpringContext.getBean("userInfoService");
        UserInfo userInfo = configuration.getUserInfo();
        List<UserMeta> userMeta = userInfo.getUserMeta();
        UserMeta element = userMeta.get(0);
        userInformation.setUserName(element.getUserName());

        boolean userdel = infoService.deleteUser(userInformation);

        return userdel;
    }

    /**
     * @Description For updating the user
     */

    public boolean updateUser(UserConfiguration configuration) {


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        SecurityUtility securityUtility = SecurityUtility.getInstance();

        UserInfoService infoService = SpringContext.getBean("userInfoService");
        UserInfo userInfo = configuration.getUserInfo();
        List<UserMeta> userMeta = userInfo.getUserMeta();
        UserMeta element = userMeta.get(0);
        userInformation.setUserName(element.getUserName());
        String encPassword = securityUtility.getEncryptedPassword(element.getPassword());
        userInformation.setCreatedBy(element.getCreatedBy());
        userInformation.setCreatedTime(element.getCreatedTime());
        userInformation.setPassword(encPassword);
        userInformation.setUserDesc(element.getUserDesc());
        userInformation.setUpdatedBy(datagenadmin);
        userInformation.setUpdatedTime(dateFormat.format(date));

        boolean userUpdate = infoService.updateUser(userInformation);

        return userUpdate;
    }

    /**
     * @Description For querying the user
     */

    public List<UserInformation> queryUser(String userName) {

        UserInfoService infoService = SpringContext.getBean("userInfoService");

        List<UserInformation> info = infoService.getUser(userName);

        return info;
    }

    /**
     * @Description For querying all user information
     */

    public List<UserInformation> queryAllUser() {

        UserInfoService infoService = SpringContext.getBean("userInfoService");

        List<UserInformation> infos = infoService.listUsers();

        return infos;

    }

    /**
     * @Description For authenticating the user
     */

    public boolean authenticateUser(String userName, String password, String operation) {

        SecurityUtility securityUtility = SecurityUtility.getInstance();

        boolean authenticationStatus = securityUtility.authenticateUser(userName, password, operation);

        return authenticationStatus;

    }

    /**
     * @Description For getting the logged in user
     */

    public String getLoggedInUser() {

        SecurityUtility securityUtility = SecurityUtility.getInstance();

        String userId = securityUtility.getCurrentUser();

        return userId;

    }

    /**
     * @Description For user logout
     */

    public boolean userLogout() {

        SecurityUtility securityUtility = SecurityUtility.getInstance();

        boolean logoutStatus = securityUtility.userLogout();

        return logoutStatus;

    }

}
