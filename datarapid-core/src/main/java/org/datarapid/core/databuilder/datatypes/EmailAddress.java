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

package org.datarapid.core.databuilder.datatypes;

import org.datarapid.core.common.SpringContext;
import org.datarapid.core.databuilder.DataGenerator;
import org.datarapid.core.persistence.model.PersonalInfo;
import org.datarapid.core.persistence.transactionservice.PersonalInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Generates a list of email-addresses from the database.
 */
public class EmailAddress implements DataGenerator {

    public List<String> getColumn(String values, int numRows) {

        List<String> list = getPersonalInfoFromDB(numRows);
        return list;
    }

    public List<String> getPersonalInfoFromDB(int noOfValues) {

        PersonalInfoService addressService = SpringContext.getBean("personalInfoService");

        List<PersonalInfo> emailAddressBeans = (List<PersonalInfo>) addressService.findAllDetails();

        List<String> list = new ArrayList<String>();

        int interm = noOfValues / 5000;
        int remains = noOfValues % 5000;

        if (interm == 0) {

            for (int p = 1; p <= noOfValues; p++) {

                list.add(emailAddressBeans.get(p).getEmailAddress().trim());

            }
        } else {
            for (int i = 0; i < interm; i++) {

                for (int j = 0; j < 5000; j++) {
                    list.add(emailAddressBeans.get(j).getEmailAddress().trim());

                }
            }
            for (int i = 0; i < remains; i++) {
                list.add(emailAddressBeans.get(i).getEmailAddress().trim());
            }
        }

        return list;

    }

}
