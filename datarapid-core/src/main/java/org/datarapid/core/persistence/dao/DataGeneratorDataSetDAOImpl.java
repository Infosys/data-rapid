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

import org.datarapid.core.persistence.model.DataSetInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description :-Generic DAO class to get/post dataset information from the database.
 */
@Repository("dataGeneratorDataSetDAO")
public class DataGeneratorDataSetDAOImpl extends AbstractDao implements DataGeneratorDataSetDAO {

    @SuppressWarnings("unchecked")
    public <T> List<T> listDataSets() {

        final List roleInfo = getSession().createQuery("from DataSetInformation ORDER BY created_time DESC").list();
        return (List<T>) roleInfo;

    }

    public boolean addDataSet(DataSetInformation dataSetInformation) {

        getSession().persist(dataSetInformation);
        return true;
    }

    public <T> List<T> getDataSet(String fileName) {
        // TODO Auto-generated method stub
        @SuppressWarnings("rawtypes")
        final List roleInfo = getSession().createQuery("from DataSetInformation where file_name=?").setString(0, fileName).list();
        return (List<T>) roleInfo;
    }

    public boolean deleteDataSet(DataSetInformation dataSetInformation) {
        // TODO Auto-generated method stub
        getSession().delete(dataSetInformation);
        return true;
    }

    public boolean updateDataSet(DataSetInformation dataSetInformation) {
        // TODO Auto-generated method stub
        getSession().update(dataSetInformation);
        return true;
    }

}
