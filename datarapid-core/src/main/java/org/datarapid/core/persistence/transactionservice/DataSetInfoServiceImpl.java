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

import org.datarapid.core.persistence.dao.DataGeneratorDataSetDAO;
import org.datarapid.core.persistence.model.DataSetInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description This class implements DataSetInfoService interface,performs the
 * transaction and returns the result-set.
 */

@Service("dataSetInfoService")
@Transactional
public class DataSetInfoServiceImpl implements DataSetInfoService {

    @Autowired
    private DataGeneratorDataSetDAO dao;

    /**
     * @Description To retrieve all the datasets
     */
    public List<DataSetInformation> listDataSets() {

        return dao.listDataSets();

    }

    /**
     * @Description To add a dataSet
     */
    public boolean addDataSet(DataSetInformation dataSetInformation) {

        return dao.addDataSet(dataSetInformation);

    }

    /**
     * @Description To retrieve a specific dataSet
     */
    public List<DataSetInformation> getDataSet(String fileName) {

        return dao.getDataSet(fileName);

    }

    /**
     * @Description To delete the dataSet
     */
    public boolean deleteDataSet(DataSetInformation dataSetInformation) {

        return dao.deleteDataSet(dataSetInformation);

    }

    /**
     * @Description To update an dataSet
     */
    public boolean updateDataSet(DataSetInformation dataSetInformation) {

        //Check whether the data set exist . If exist update will be fired . else insert will be fired.
        boolean status = false;

        List<DataSetInformation> availableDataSet = dao.getDataSet(dataSetInformation.getFileName());
        if (availableDataSet.size() > 0) {
            status = dao.updateDataSet(dataSetInformation);
        } else {
            status = dao.addDataSet(dataSetInformation);
        }
        return status;
    }

}
