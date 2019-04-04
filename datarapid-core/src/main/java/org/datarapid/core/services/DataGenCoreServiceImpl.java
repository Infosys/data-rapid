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

package org.datarapid.core.services;

import com.google.gson.Gson;
import org.datarapid.core.databuilder.*;
import org.datarapid.core.exception.DataGenException;
import org.datarapid.core.persistence.model.DataSetInformation;
import org.datarapid.core.persistence.model.RoleInformation;
import org.datarapid.core.persistence.model.UserInformation;
import org.datarapid.core.security.SecurityUtility;
import org.datarapid.core.util.CSVFileWriter;
import org.datarapid.core.util.CommonUtils;
import org.datarapid.core.util.Messages;
import org.datarapid.core.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description :-This is the service layer which is called from the API and
 * will pass the configuration object to populate the columns.Once
 * data has been generated it calls the CSVWriter class to write
 * the files to the CSV.
 */

@Service
public class DataGenCoreServiceImpl implements DataGenCoreService, Messages {

    /**
     * @throws Exception
     */
    private static final Logger logger = LoggerFactory.getLogger(DataGenCoreServiceImpl.class);

    @Value("${tempDirectory}")
    private String tempDirectory;

    @Autowired
    private BuildData buildData;

    @Autowired
    private RetrieveData retrieveData;

    @Autowired
    private ManageUser manageUser;

    @Autowired
    private ManageConfiguration manageConfiguration;

    @Autowired
    UserConfiguration userConfiguration;

    @Autowired
    RoleConfiguration roleConfiguration;

    @Autowired
    JobConfiguration jobConfiguration;

    @Autowired
    DataSetConfiguration dataSetConfiguration;

    @Autowired
    DataSetConfigurations dataSetConfigurations;

    @Autowired
    private ManageRole manageRole;

    @Autowired
    private ManageActivityLog manageActivityLog;

    @Autowired
    private ActivityMeta activityMeta;

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class. It receives the configuration object
     * from the UI side and then validates the fileInfo from
     * it.Once validated,it then calls the Build Data class to
     * build all the columns one by one.Finally as it receives all
     * the columns,it calls the CSV writer class and sends this
     * list of columns for writing to the CSV file.
     */
    public String buildData(JobConfiguration configuration) throws Exception {

        String processingMessage = null;

        SecurityUtility securityUtility = SecurityUtility.getInstance();
        String currentUser = securityUtility.getCurrentUser();

        boolean roleStatus = isRoleActive(currentUser);

        if (roleStatus == false) {
            logger.error("Role Inactive due to license expiry / exceeded usage ");
            processingMessage = ROLE_INACTIVE;
            throw new DataGenException(ROLE_INACTIVE);
        }

        boolean validateJobConfig = validate(configuration.getFileInfo());
        String fileName = null;
        if (validateJobConfig == true) {
            try {

                String numberOfRows = configuration.getNumberOfRows();
                int intRows = Integer.parseInt(numberOfRows);

                // TODO Spring go for constructor injection in Spring
                buildData.setNoOfValues(intRows);

                List<List<String>> finalList = buildData.startBuilding(configuration);

                List<String> headersList = new ArrayList<String>();

                final List<ColumnMeta> listOfColumns = configuration.getFileInfo().getListOfColumns();
                for (ColumnMeta columnMeta : listOfColumns) {
                    boolean columnBucketingEnabled = false;
                    if (columnMeta.getBucketedColumn() == null) {
                        columnBucketingEnabled = false;
                    } else {
                        if ((null != columnMeta.getBucketedColumn()) &&
                                (null != columnMeta.getBucketedColumn().getBucketedColumnHeader())
                                && ((columnMeta.getBucketedColumn().getBucketedColumnHeader()).length() != 0)
                                ) {
                            columnBucketingEnabled = true;
                            headersList.add(columnMeta.getColumnName());
                            headersList.add(columnMeta.getBucketedColumn().getBucketedColumnHeader());
                        }
                    }

                    if (!columnBucketingEnabled) {
                        headersList.add(columnMeta.getColumnName());
                    }
                }

                fileName = tempDirectory + File.separator + configuration.getFileName() + "." + configuration.getFileType();

                CSVFileWriter csvFileWriter = new CSVFileWriter(intRows, headersList, fileName);
                csvFileWriter.writeFile(finalList);

                processingMessage = FILE_CREATION_MESSAGE + configuration.getFileName() + "." + configuration.getFileType();

				/* Setting the activity log details */
                CommonUtils commonUtils = new CommonUtils();
                activityMeta.setTriggeredUserName(currentUser);
                activityMeta.setActivityDate(commonUtils.getSysDate());
                activityMeta.setGeneratedFileName(configuration.getFileName() + "." + configuration.getFileType());
                activityMeta.setProcessedRecordCount(numberOfRows);
                activityMeta.setProcessedUsage(Long.toString(commonUtils.getFileSize(fileName)));
                boolean logActitityStatus = logActivity(activityMeta);
                logger.info("logActitityStatus :   " + logActitityStatus);

            } catch (Exception e) {
                logger.error("Error in Service class  " + e);
                processingMessage = FAILURE_MESSAGE + " : " + e;
                throw new DataGenException(processingMessage);
            }

        } else {
            logger.error("Validation Failure . ColumnName/Data Type/Values/Advanced is Null");
            processingMessage = FAILURE_MESSAGE;
            throw new DataGenException(VALIDATION_FAILURE);
        }

        return processingMessage;
    }

    /**
     * @param fileInfo
     * @Description :-This method validates whether the fileInfo has some
     * contents in it or not. If the fileContents are empty then it
     * returns false ,else true.
     */
    private boolean validate(FileInfo fileInfo) {
        boolean b = false;
        if (null != fileInfo) {
            int size = fileInfo.getListOfColumns().size();
            if (size > 0) {
                ColumnMeta testColumn = fileInfo.getListOfColumns().get(0);
                String columnName = testColumn.getColumnName();
                String type = testColumn.getType();
                String value = testColumn.getValues();
                String valueType = testColumn.getValueType();
                String dataType = testColumn.getDataType();

                if (null != columnName && null != type && null != valueType && null != dataType) {
                    b = true;
                }
            }
        }
        return b;
    }


    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class. Used to retrieve the databases related datatypes dataset for the User Interface.
     */

    public Map<String, List<String>> retrieveData(UIConfiguration configuration) throws Exception {

        Map<String, List<String>> finalList = null;
        try {
            String dataLimit = configuration.getDataLimit();
            int intdataLimit = Integer.parseInt(dataLimit);

            retrieveData.setDataLimit(intdataLimit);
            finalList = retrieveData.startRetrieving(configuration);

        } catch (Exception e) {
            logger.error("Error in Service class  " + e);
        }

        return finalList;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for creating an user.
     */
    public boolean userCreate(UserConfiguration configuration) throws Exception {

        boolean createStatus = manageUser.createUser(configuration);

        return createStatus;
    }


    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for updating an user.
     */
    public boolean userUpdate(UserConfiguration configuration) throws Exception {

        boolean updateStatus = manageUser.updateUser(configuration);

        return updateStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying an user.
     */
    public UserConfiguration userQuery(String userName) throws Exception {

        List<UserInformation> info = manageUser.queryUser(userName);

        userConfiguration = remapUserConfiguration(info);

        return userConfiguration;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying all user.
     */
    public UserConfiguration allUserQuery() throws Exception {

        List<UserInformation> info = manageUser.queryAllUser();

        userConfiguration = remapUserConfiguration(info);

        return userConfiguration;
    }


    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for deleting a user.
     */
    public boolean userDelete(UserConfiguration configuration) throws Exception {

        boolean deleteStatus = manageUser.deleteUser(configuration);

        return deleteStatus;
    }

    public UserConfiguration remapUserConfiguration(List<UserInformation> info) throws Exception {

        if (info.size() > 0) {
            UserInfo userInfo = new UserInfo();
            List<UserMeta> userMeta = new ArrayList<UserMeta>();
            for (int i = 0; i < info.size(); i++) {
                UserMeta element = new UserMeta();
                element.setUserName(info.get(i).getUserName());
                element.setUserDesc(info.get(i).getUserDesc());
                element.setCreatedBy(info.get(i).getCreatedBy());
                element.setCreatedTime(info.get(i).getCreatedTime());
                element.setUpdatedBy(info.get(i).getUpdatedBy());
                element.setUpdatedTime(info.get(i).getUpdatedTime());
                element.setPassword(info.get(i).getPassword());
                userMeta.add(element);
            }
            userInfo.setUserMeta(userMeta);
            userConfiguration.setUserInfo(userInfo);
        }

        return userConfiguration;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for creating an userrole.
     */
    public boolean userRoleCreate(RoleConfiguration configuration) throws Exception {

        boolean createStatus = manageRole.createRole(configuration);

        return createStatus;
    }


    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for updating an userrole.
     */
    public boolean userRoleUpdate(RoleConfiguration configuration) throws Exception {

        boolean updateStatus = manageRole.updateRole(configuration);

        return updateStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying an userrole.
     */
    public RoleConfiguration userRoleQuery(String userName) throws Exception {

        List<RoleInformation> info = manageRole.queryRole(userName);

        if (info.size() > 0) {
            roleConfiguration = remapRoleConfiguration(info);
        } else {
            roleConfiguration = new RoleConfiguration();
        }
        return roleConfiguration;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying all userroles.
     */
    public RoleConfiguration allUserRoleQuery() throws Exception {

        List<RoleInformation> info = manageRole.queryAllRoles();

        roleConfiguration = remapRoleConfiguration(info);

        return roleConfiguration;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for deleting a userrole.
     */
    public boolean userRoleDelete(RoleConfiguration configuration) throws Exception {

        boolean deleteStatus = manageRole.deleteRole(configuration);

        return deleteStatus;
    }

    public RoleConfiguration remapRoleConfiguration(List<RoleInformation> info) throws Exception {

        if (info.size() > 0) {
            RoleInfo roleInfo = new RoleInfo();
            List<RoleMeta> roleMeta = new ArrayList<RoleMeta>();
            for (int i = 0; i < info.size(); i++) {
                RoleMeta element = new RoleMeta();
                element.setUserName(info.get(i).getUserName());
                element.setRoleName(info.get(i).getRoleName());
                element.setRoleDesc(info.get(i).getRoleDesc());
                element.setRoleType(info.get(i).getRoleType());
                element.setGroupUsers(info.get(i).getGroupUsers());
                element.setUsageType(info.get(i).getUsageType());
                element.setStartDate(info.get(i).getStartDate());
                element.setEndDate(info.get(i).getEndDate());
                element.setRecordCountLimit(info.get(i).getRecordCountLimit());
                element.setUsageLimit(info.get(i).getUsageLimit());
                element.setDaysLimit(info.get(i).getDaysLimit());
                roleMeta.add(element);
            }
            roleInfo.setRoleMeta(roleMeta);
            roleConfiguration.setRoleInfo(roleInfo);
        }

        return roleConfiguration;
    }


    /**
     * @param configuration
     * @throws Exception
     * @Description :-The method to log the activity
     */
    public boolean logActivity(ActivityMeta activityMeta) throws Exception {

        boolean createStatus = manageActivityLog.logActivity(activityMeta);

        return createStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-The method to check the role is active
     */
    public boolean isRoleActive(String userName) throws Exception {

        boolean roleStatus = manageRole.isRoleActive(userName);

        return roleStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-The method to authenticate the user
     */
    public boolean authenticateUser(String userName, String password, String operation) throws Exception {

        boolean authenticationStatus = manageUser.authenticateUser(userName, password, operation);

        return authenticationStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-The method to get the logged in user
     */
    public String getLoggedInUser() throws Exception {

        String userId = manageUser.getLoggedInUser();

        return userId;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-The method for user logout
     */
    public boolean userLogout() throws Exception {

        boolean authenticationStatus = manageUser.userLogout();

        return authenticationStatus;
    }

    /**
     * @param configuration
     * @throws Exception
     * @Description Method to save the configuration
     */
    public boolean saveConfiguration(JobConfiguration configuration) throws Exception {

        boolean saveStatus = manageConfiguration.createDataSetConfiguration(configuration);
        return saveStatus;

    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying the dataset.
     */
    public JobConfiguration dataSetQuery(String datasetName) throws Exception {

        List<DataSetInformation> info = manageConfiguration.queryDataSet(datasetName);

        jobConfiguration = remapJobConfiguration(info);

        return jobConfiguration;
    }


    public JobConfiguration remapJobConfiguration(List<DataSetInformation> info) throws Exception {

        if (info.size() > 0) {
            for (int i = 0; i < info.size(); i++) {
                try {
                    //Blob dataSetBlob = info.get(i).getConfigurationDetails();
                    //byte[] byteArray = dataSetBlob.getBytes(1, (int) (dataSetBlob.length()));
                    //CommonUtils commonUtils = new CommonUtils();
                    //JobConfiguration jobConfiguration = (JobConfiguration) commonUtils.deserialize(byteArray);
                    Gson gson = new Gson();
                    String configDetails = info.get(i).getConfigurationJson();
                    jobConfiguration = gson.fromJson(configDetails, JobConfiguration.class);
                } catch (Exception e) {
                    logger.error("Error in remapDataSetConfiguration" + e);
                }
            }
        }
        return jobConfiguration;

    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for querying all datasets.
     */
    public DataSetConfigurations dataSetsQuery() throws Exception {

        List<DataSetInformation> info = manageConfiguration.queryAllDataSets();

        dataSetConfigurations = remapDataSetConfiguration(info);

        return dataSetConfigurations;
    }

    public DataSetConfigurations remapDataSetConfiguration(List<DataSetInformation> info) throws Exception {
						
		/*if (info.size() > 0) {			
			List<JobConfiguration> jobConfigurations = new ArrayList<JobConfiguration>();	
			for (int i = 0; i < info.size(); i++) {										
				JobConfiguration element = new JobConfiguration();											
				Gson gson = new Gson();					
				String configDetails= info.get(i).getConfigurationJson();
				String fileName = info.get(i).getFileName();
				String fileType = info.get(i).getFileType();
				String userName = info.get(i).getUserName();
				String createdTime = info.get(i).getCreatedTime();
				element = gson.fromJson(configDetails, JobConfiguration.class);							
				jobConfigurations.add(element);				
			}
			dataSetConfiguration.setJobConfiguration(jobConfigurations);				
		}*/


        if (info.size() > 0) {
            List<DataSetConfiguration> dataSetConfigurationList = new ArrayList<DataSetConfiguration>();
            for (int i = 0; i < info.size(); i++) {
                DataSetConfiguration element = new DataSetConfiguration();
                Gson gson = new Gson();

                String fileName = info.get(i).getFileName();
                String fileType = info.get(i).getFileType();
                String userName = info.get(i).getUserName();
                String createdTime = info.get(i).getCreatedTime();
                element.setCreatedTime(createdTime);
                element.setFileName(fileName);
                element.setFileType(fileType);
                element.setUserName(userName);
                String configDetails = info.get(i).getConfigurationJson();

                JobConfiguration jobConf = new JobConfiguration();
                jobConf = gson.fromJson(configDetails, JobConfiguration.class);
                element.setJobConfiguration(jobConf);
                dataSetConfigurationList.add(element);
            }
            dataSetConfigurations.setDataSetConfigurations(dataSetConfigurationList);
            ;
        }

        return dataSetConfigurations;

    }


    /**
     * @param configuration
     * @throws Exception
     * @Description Method to update the configuration
     */
    public boolean updateConfiguration(JobConfiguration configuration) throws Exception {

        boolean updateStatus = manageConfiguration.updateDataSetConfiguration(configuration);

        return updateStatus;

    }

    /**
     * @param configuration
     * @throws Exception
     * @Description :-This method is invoked by from the data-gen-api service
     * implementation class for deleting a data set configuration.
     */
    public boolean dataSetConfigurationDelete(JobConfiguration configuration) throws Exception {

        boolean deleteStatus = manageConfiguration.deleteDataSet(configuration);

        return deleteStatus;

    }
}
