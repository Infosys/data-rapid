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

import org.datarapid.core.persistence.model.UserInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description :-Generic DAO class to get/post user information from the database.
 */
@Repository("dataGeneratorUserDAO")
public class DataGeneratorUserDAOImpl extends AbstractDao implements DataGeneratorUserDAO {

    @SuppressWarnings("unchecked")
    public <T> List<T> listUsers() {

        final List userInfo = getSession().createQuery("from UserInformation").list();
        return (List<T>) userInfo;

    }

    public boolean addUser(UserInformation userInformation) {

        getSession().persist(userInformation);
        return true;
    }

    public <T> List<T> getUser(String userName) {
        // TODO Auto-generated method stub
        @SuppressWarnings("rawtypes")
        final List userInfo = getSession().createQuery("from UserInformation where user_name=?").setString(0, userName).list();
        return (List<T>) userInfo;
    }

    public boolean deleteUser(UserInformation userInformation) {
        // TODO Auto-generated method stub
        getSession().delete(userInformation);
        return true;
    }

    public boolean updateUser(UserInformation userInformation) {
        // TODO Auto-generated method stub
        getSession().update(userInformation);
        return true;
    }
}
