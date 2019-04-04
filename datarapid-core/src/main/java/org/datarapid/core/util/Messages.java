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

package org.datarapid.core.util;

/**
 * Generic Messages Interface for Data gen.
 */
public interface Messages {

    public static final String SUCCESS_MESSAGE = "Data Generation Processed Successfully";
    public static final String FAILURE_MESSAGE = "Data Generation Processing Failed. Please check the log for more details";
    public static final String FILE_CREATION_MESSAGE = "Generated the data file ";
    public static final String VALIDATION_FAILURE_MESSAGE = "Data Generation Processing Failed in validation check. Please check the log for more details";
    public static final String ERROR_002 = "Error in User Creation";
    public static final String SUCCESS_002 = "User Created Successfully";
    public static final String ERROR_003 = "Error in User Updation";
    public static final String SUCCESS_003 = "User Updated Successfully";
    public static final String ERROR_004 = "Error in User Deletion";
    public static final String SUCCESS_004 = "User Deleted Successfully";
    public static final String ERROR_005 = "Error in Role Creation";
    public static final String SUCCESS_005 = "Role Created Successfully";
    public static final String ERROR_006 = "Error in Role Updation";
    public static final String SUCCESS_006 = "Role Updated Successfully";
    public static final String ERROR_007 = "Error in Role Deletion";
    public static final String SUCCESS_007 = "Role Deleted Successfully";
    public static final String ROLE_INACTIVE = "License expired / usage exceeded ";
    public static final String SUCCESS_008 = "User Authenticated Successfully";
    public static final String ERROR_008 = "Error in User Authentication";
    public static final String SUCCESS_009 = "User Logged out Successfully";
    public static final String ERROR_009 = "Error while user log out";
    public static final String SUCCESS_010 = "Configuration saved successfully";
    public static final String ERROR_010 = "Error while saving the configuration";
    public static final String VALIDATION_FAILURE = "Validation Failure . Please check the configuration values entered";
    public static final String SUCCESS_011 = "Configuration updated successfully";
    public static final String ERROR_011 = "Error while updating the configuration";
    public static final String SUCCESS_012 = "Configuration deleted successfully";
    public static final String ERROR_012 = "Error while deleting the configuration";
    public static final String SUCCESS_013 = "Successfully retrieved the logged in user";
    public static final String ERROR_013 = "Error while getting the logged in user";
}
