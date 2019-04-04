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

package org.datarapid.core.databuilder;

import org.datarapid.core.common.SpringContext;
import org.datarapid.core.persistence.model.ActivityLogInformation;
import org.datarapid.core.persistence.transactionservice.ActivityLogInfoService;
import org.datarapid.core.view.ActivityMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description :- This class is for managing activity log.
 */

public class ManageActivityLog {

    private static final Logger logger = LoggerFactory.getLogger(ManageActivityLog.class);

    public ManageActivityLog() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Description For creating the activity log
     */

    @Autowired
    private ActivityLogInformation activityLogInformation;

    public boolean logActivity(ActivityMeta activityMeta) {

        ActivityLogInfoService infoService = SpringContext.getBean("activityLogInfoService");
        if (activityMeta.getLogId() ==0){
            activityLogInformation =  new ActivityLogInformation();
        }
        activityLogInformation.setLogId(activityMeta.getLogId());
        activityLogInformation.setTriggeredUserName(activityMeta.getTriggeredUserName());
        activityLogInformation.setActivityDate(activityMeta.getActivityDate());
        activityLogInformation.setGeneratedFileName(activityMeta.getGeneratedFileName());
        activityLogInformation.setProcessedRecordCount(activityMeta.getProcessedRecordCount());
        activityLogInformation.setProcessedUsage(activityMeta.getProcessedUsage());
        boolean logActivity = infoService.logActivity(activityLogInformation);
        return logActivity;

    }

}
