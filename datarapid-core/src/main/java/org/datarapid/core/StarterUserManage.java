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

package org.datarapid.core;

import com.google.gson.Gson;
import org.datarapid.core.services.DataGenCoreServiceImpl;
import org.datarapid.core.view.UserConfiguration;
import org.datarapid.core.view.UserInfo;
import org.datarapid.core.view.UserMeta;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 */
public class StarterUserManage {

    @SuppressWarnings("resource")
    ApplicationContext context = new ClassPathXmlApplicationContext("datagen_core.xml");
    DataGenCoreServiceImpl dataGenCoreServiceImpl = (DataGenCoreServiceImpl) context
            .getBean("dataGenCoreServiceImpl");

    public StarterUserManage() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {

        String str = "";

        try {
            StarterUserManage starterUserManage = new StarterUserManage();

            //starterUserManage.createuser();

            //starterUserManage.queryuser();

            //starterUserManage.queryalluser();

            //starterUserManage.updateuser();

            //starterUserManage.deleteuser();

            starterUserManage.getjsonformat();

            System.out.println("Completed the StarterUserManage Processing");
        } catch (Exception e) {
            System.out.println("in exception");
            e.printStackTrace();
        }


    }

    public void createuser() throws Exception {

        UserConfiguration configuration = new UserConfiguration();
        UserInfo userInfo = new UserInfo();
        List<UserMeta> userMeta = new ArrayList<UserMeta>();
        UserMeta element = new UserMeta();
        element.setUserName("user1");
        element.setPassword("user1");
        element.setUserDesc("user1");
        userMeta.add(element);
        userInfo.setUserMeta(userMeta);
        configuration.setUserInfo(userInfo);

        boolean isUserCreated = dataGenCoreServiceImpl.userCreate(configuration);

        System.out.println("Completed the createuser");
    }

    public void queryuser() throws Exception {

        String userName = "user1";
        UserConfiguration configuration = dataGenCoreServiceImpl.userQuery(userName);
        System.out.println(configuration);
        System.out.println("Completed the queryuser");
    }

    public void queryalluser() throws Exception {

        UserConfiguration configuration = dataGenCoreServiceImpl.allUserQuery();
        System.out.println(configuration);
        System.out.println("Completed the queryalluser");
    }

    public void updateuser() throws Exception {

        UserConfiguration configuration = new UserConfiguration();
        UserInfo userInfo = new UserInfo();
        List<UserMeta> userMeta = new ArrayList<UserMeta>();
        UserMeta element = new UserMeta();
        element.setUserName("user1");
        element.setPassword("updatedpassword");
        element.setUserDesc("updateddesc");
        userMeta.add(element);
        userInfo.setUserMeta(userMeta);
        configuration.setUserInfo(userInfo);

        boolean isUserUpdated = dataGenCoreServiceImpl.userUpdate(configuration);

        System.out.println("Completed the updateuser");
    }


    public void deleteuser() throws Exception {

        UserConfiguration configuration = new UserConfiguration();
        UserInfo userInfo = new UserInfo();
        List<UserMeta> userMeta = new ArrayList<UserMeta>();
        UserMeta element = new UserMeta();
        element.setUserName("user1");
        userMeta.add(element);
        userInfo.setUserMeta(userMeta);
        configuration.setUserInfo(userInfo);

        boolean isUserdeleted = dataGenCoreServiceImpl.userDelete(configuration);

        System.out.println("Completed the deleteuser");
    }

    public void getjsonformat() throws Exception {

        UserConfiguration configuration = new UserConfiguration();
        UserInfo userInfo = new UserInfo();
        List<UserMeta> userMeta = new ArrayList<UserMeta>();
        UserMeta element = new UserMeta();
        element.setUserName("user1");
        element.setUserDesc("user1");
        element.setCreatedBy("admin");
        element.setCreatedTime("2016-06-07 19:29:11");
        element.setUpdatedBy("admin");
        element.setUpdatedTime("2016-06-08 19:29:11");
        element.setPassword("password");
        userMeta.add(element);
        userInfo.setUserMeta(userMeta);
        configuration.setUserInfo(userInfo);

        Gson gson = new Gson();
        String json = gson.toJson(configuration);

        System.out.println(json);
        //{"userInfo":{"userMeta":[{"userName":"user1","userDesc":"user1","createdBy":"admin","createdTime":"2016-06-07 19:29:11","updatedBy":"admin","updatedTime":"2016-06-08 19:29:11","password":"password"}]}}
    }
}
