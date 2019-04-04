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
import org.datarapid.core.persistence.model.LocationInfo;
import org.datarapid.core.persistence.transactionservice.LocationInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Returns a list of countries from the database.
 */
public class Country implements DataGenerator {

    public List<String> getColumn(String values, int numRows) {

        List<String> list = getLocationInfoFromDB(numRows);
        return list;
    }

    public List<String> getLocationInfoFromDB(int noOfValues) {
        LocationInfoService infoService = SpringContext.getBean("locationInfoService");

        List<LocationInfo> infos = infoService.findAllLocation();
        List<String> list = new ArrayList<String>();

        int interm = noOfValues / 80000;
        int remains = noOfValues % 80000;

        if (interm == 0) {

            for (int j = 1; j <= noOfValues; j++) {
                list.add(infos.get(j).getCountry());
            }
        } else {
            for (int m = 0; m < interm; m++) {

                // if we limit this number of entries from database to 5000
                // ,else you can change your value
                for (int j = 0; j < 80000; j++) {

                    list.add(infos.get(j).getCountry());

                }

            }
            for (int j = 0; j < remains; j++) {
                list.add(infos.get(j).getCountry());
            }
        }

        return list;

    }

}
