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

import org.datarapid.core.persistence.dao.BaseDataGeneratorDAO;
import org.datarapid.core.persistence.model.PersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description This class implements PersonalInfoService interface,performs the
 * transaction and returns the result-set.
 */

@Service("personalInfoService")
@Transactional
public class PersonalInfoServiceImpl implements PersonalInfoService {

    @Autowired
    private BaseDataGeneratorDAO dao;

    public List<PersonalInfo> findAllDetails() {
        // TODO Auto-generated method stub
        return dao.findAllDetails();
    }

}
