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

import org.datarapid.core.services.DataGenCoreServiceImpl;
import org.datarapid.core.view.UIColumnMeta;
import org.datarapid.core.view.UIConfiguration;
import org.datarapid.core.view.UIFileInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : Test Main Class
 */
public class StarterUIRetrieval {

    public static void main(String[] args) {

        UIColumnMeta meta1 = new UIColumnMeta("firstname", "1000");
        UIColumnMeta meta2 = new UIColumnMeta("lastname", "1000");
        UIColumnMeta meta3 = new UIColumnMeta("emailaddress", "1000");
        UIColumnMeta meta4 = new UIColumnMeta("zipcode", "1000");
        UIColumnMeta meta5 = new UIColumnMeta("city", "1000");
        UIColumnMeta meta6 = new UIColumnMeta("state", "1000");
        UIColumnMeta meta7 = new UIColumnMeta("country", "1000");
        UIColumnMeta meta8 = new UIColumnMeta("location", "1000");

        List<UIColumnMeta> list = new ArrayList<UIColumnMeta>();
        list.add(meta1);
        list.add(meta2);
        list.add(meta3);
        list.add(meta4);
        list.add(meta5);
        list.add(meta6);
        list.add(meta7);
        list.add(meta8);

        UIFileInfo fileInfo = new UIFileInfo(list);
        UIConfiguration configuration = new UIConfiguration("firstname", "1000", fileInfo);

        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("datagen_core.xml");
        DataGenCoreServiceImpl dataGenCoreServiceImpl = (DataGenCoreServiceImpl) context
                .getBean("dataGenCoreServiceImpl");

        Map<String, List<String>> finalList = null;

        try {

            finalList = dataGenCoreServiceImpl.retrieveData(configuration);
        } catch (Exception e) {
            System.out.println("in exception");
            e.printStackTrace();
        }

        System.out.println("over and out");
    }
}
