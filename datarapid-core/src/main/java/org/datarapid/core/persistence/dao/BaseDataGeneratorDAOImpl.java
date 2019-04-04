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

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description :-Generic DAO class to get back data from the database.Retrieves
 * the personal information and the location information.
 */
@Repository("baseDataGeneratorDAO")
public class BaseDataGeneratorDAOImpl extends AbstractDao implements BaseDataGeneratorDAO {

    @SuppressWarnings("unchecked")
    public <T> List<T> findAllDetails() {

        @SuppressWarnings("rawtypes")
        final List personalInfo = getSession().createQuery("from PersonalInfo").list();

        return (List<T>) personalInfo;

    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAllLocations() {

        @SuppressWarnings("rawtypes")
        final List locationInfo = getSession().createQuery("from LocationInfo").list();
        return (List<T>) locationInfo;

    }

}
