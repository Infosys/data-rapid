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
/**
 * @Description This class implements LocationInfoService interface,performs the transaction and returns the result-set.
 */

import org.datarapid.core.persistence.dao.BaseDataGeneratorDAOImpl;
import org.datarapid.core.persistence.model.LocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("locationInfoService")
@Transactional
public class LocationInfoServiceImpl implements LocationInfoService {

    @Autowired
    private BaseDataGeneratorDAOImpl dao;

    final static Logger logger = LoggerFactory.getLogger(LocationInfoServiceImpl.class);

    public List<LocationInfo> findAllLocation() {
        return dao.findAllLocations();
    }

}
