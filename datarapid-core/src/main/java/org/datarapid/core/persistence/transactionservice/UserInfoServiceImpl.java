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

import org.datarapid.core.persistence.dao.DataGeneratorUserDAO;
import org.datarapid.core.persistence.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description This class implements UserInfoService interface,performs the
 * transaction and returns the result-set.
 */

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private DataGeneratorUserDAO dao;

    /**
     * @Description To retrieve all the users
     */
    public List<UserInformation> listUsers() {

        return dao.listUsers();

    }

    /**
     * @Description To add a user
     */
    public boolean addUser(UserInformation userInformation) {

        return dao.addUser(userInformation);

    }

    /**
     * @Description To retrieve a specific user
     */
    public List<UserInformation> getUser(String userName) {

        return dao.getUser(userName);

    }

    /**
     * @Description To delete the user
     */
    public boolean deleteUser(UserInformation userInformation) {

        return dao.deleteUser(userInformation);

    }


    /**
     * @Description To update an user
     */
    public boolean updateUser(UserInformation userInformation) {

        return dao.updateUser(userInformation);

    }
}
