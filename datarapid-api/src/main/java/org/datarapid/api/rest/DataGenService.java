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

package org.datarapid.api.rest;

import org.datarapid.api.builder.ResponseBuilder;
import org.datarapid.api.dto.ConfigDetails;
import org.datarapid.api.dto.DBDataDetails;
import org.datarapid.api.dto.DataGenResponse;
import org.datarapid.api.dto.FileTransferDetails;
import org.datarapid.core.view.DataSetConfigurations;
import org.datarapid.core.view.JobConfiguration;
import org.datarapid.core.view.RoleConfiguration;
import org.datarapid.core.view.UserConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Component
@Path("/service")
public class DataGenService {

    private static final Logger log = LoggerFactory.getLogger(DataGenService.class);

    @Autowired
    ResponseBuilder responseBuilder;

    @Value("${tempDirectory}")
    private String tempDirectory;

    /**
     * @param
     * @throws Exception
     * @Description :-This is the test service.
     */

    @GET
    @Path("/testservice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response processTestDetails() {

        log.debug("Executing the Test Service");
        DataGenResponse processResponse = responseBuilder.processTestResponse();
        log.debug("Completed the Test Service Execution");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to get the configuration details.
     */

    @GET
    @Path("/configdetailsretrieval")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfigDetails() {

        log.debug("Executing the Configuration Retrieval Service");
        ConfigDetails configDetails = responseBuilder.processConfigDetailsRetrieval();
        log.debug("Completed the Configuration Retrieval Service");
        return Response.status(200).entity(configDetails).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to get the drop down of data for the standard data types like first name,last name,city The details will be fetched from the database.
     */

    @GET
    @Path("/dbdataretrieval")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDBDataDetails() {

        log.debug("Executing the Database data Retrieval Service");
        DBDataDetails detailsList = responseBuilder.processDBDataRetrieval();
        log.debug("Completed the Database data Retrieval Service");
        return Response.status(200).entity(detailsList).build();

    }


    /**
     * @param
     * @throws Exception
     * @Description :-The service to transfer the generated file.
     */

    @POST
    @Path("/datagenfiletransfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processFileTransfer(final FileTransferDetails fileTransferDetails) {

        log.debug("Executing the File Transfer Service");

        if (!tempDirectory.endsWith(File.separator)) {
            tempDirectory = tempDirectory + File.separator;
        }

        fileTransferDetails.setLocalDirectory(tempDirectory);
        fileTransferDetails.setFileName(fileTransferDetails.getFileName() + ".csv");

        DataGenResponse processResponse = responseBuilder.processFileTransfer(fileTransferDetails);

        log.debug("Completed the File Transfer Service");

        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to locally download the file.
     */

    @Path("/download/{fileNameDetails}")
    @GET
    @Produces({"text/csv"})
    public Response getFile(@PathParam("fileNameDetails") final String fileNameDetails) {

        if (!tempDirectory.endsWith(File.separator)) {
            tempDirectory = tempDirectory + File.separator;
        }

        String fileDetails = tempDirectory + fileNameDetails;
        File file = new File(fileDetails);

        javax.ws.rs.core.Response.ResponseBuilder response = Response.ok(file);

        String argument = "attachment; filename=\"" + fileNameDetails + "\"";

        response.header("Content-Disposition", argument);

        return response.build();
    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to generate the bulk data for version 2.
     */

    @POST
    @Path("/dataprocessservice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processData(final JobConfiguration configuration) {

        log.debug("Executing the Data Gen Process Service");
        log.debug("fileName :" + configuration.getFileName());
        log.debug("numberOfRows: " + configuration.getNumberOfRows());
        log.debug("fileType: " + configuration.getFileType());
        DataGenResponse processResponse = responseBuilder.processData(configuration);

        log.debug("Completed executing the Data Gen Process Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to create an user.
     */

    @POST
    @Path("/createuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(final UserConfiguration configuration) {

        log.debug("Executing the Data Gen createUser Service");

        DataGenResponse processResponse = responseBuilder.createUser(configuration);

        log.debug("Completed executing the Data Gen createUser Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to update an user.
     */

    @POST
    @Path("/updateuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(final UserConfiguration configuration) {

        log.debug("Executing the Data Gen updateUser Service");

        DataGenResponse processResponse = responseBuilder.updateUser(configuration);

        log.debug("Completed executing the Data Gen updateUser Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to delete an user.
     */

    @POST
    @Path("/deleteuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(final UserConfiguration configuration) {

        log.debug("Executing the Data Gen deleteUser Service");
        DataGenResponse processResponse = responseBuilder.deleteUser(configuration);
        log.debug("Completed executing the Data Gen deleteUser Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query an user.
     */

    @GET
    @Path("/queryuser/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryUser(@PathParam("userName") final String userName) {

        log.debug("Executing the Data Gen queryUser Service");

        UserConfiguration configuration = responseBuilder.queryUser(userName);

        log.debug("Completed the Data Gen queryUser Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query all users.
     */
    @GET
    @Path("/queryusers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryUsers() {

        log.debug("Executing the Data Gen queryUsers Service");

        UserConfiguration configuration = responseBuilder.queryUsers();

        log.debug("Completed the Data Gen queryUser Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to create a role.
     */

    @POST
    @Path("/createrole")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(final RoleConfiguration configuration) {

        log.debug("Executing the Data Gen createRole Service");

        DataGenResponse processResponse = responseBuilder.createRole(configuration);

        log.debug("Completed executing the Data Gen createRole Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to update a role.
     */

    @POST
    @Path("/updaterole")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(final RoleConfiguration configuration) {

        log.debug("Executing the Data Gen updateRole Service");

        DataGenResponse processResponse = responseBuilder.updateRole(configuration);

        log.debug("Completed executing the Data Gen updateRole Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to delete a role.
     */

    @POST
    @Path("/deleterole")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRole(final RoleConfiguration configuration) {

        log.debug("Executing the Data Gen deleteRole Service");

        DataGenResponse processResponse = responseBuilder.deleteRole(configuration);

        log.debug("Completed executing the Data Gen deleteRole Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query a role.
     */

    @GET
    @Path("/queryrole/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryRole(@PathParam("userName") final String userName) {

        log.debug("Executing the Data Gen queryRole Service");

        RoleConfiguration configuration = responseBuilder.queryRole(userName);

        log.debug("Completed the Data Gen queryRole Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query all roles.
     */
    @GET
    @Path("/queryroles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryRoles() {

        log.debug("Executing the Data Gen queryRoles Service");

        RoleConfiguration configuration = responseBuilder.queryRoles();

        log.debug("Completed the Data Gen queryRoles Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to get the login info.
     */
    @GET
    @Path("/authenticatelogin/{userName}/{password}/{operation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@PathParam("userName") final String userName, @PathParam("password") final String password, @PathParam("operation") final String operation) {

        log.debug("Executing the Authentication Service");
        DataGenResponse processResponse = responseBuilder.authenticateUser(userName, password, operation);
        log.debug("Completed the Authentication Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to get the logged in user.
     */
    @GET
    @Path("/getloggedinuser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoggedInUser() {

        log.debug("Executing the getLoggedInUser Service");
        DataGenResponse processResponse = responseBuilder.getLoggedInUser();
        log.debug("Completed the getLoggedInUser Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service for user logout
     */
    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogout() {

        log.debug("Executing the logout Service");
        DataGenResponse processResponse = responseBuilder.userLogout();
        log.debug("Completed the logout Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to save the data set configuration.
     */

    @POST
    @Path("/savedataset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveConfiguration(final JobConfiguration configuration) {

        log.debug("Executing the saveConfiguration Service");
        DataGenResponse processResponse = responseBuilder.saveConfiguration(configuration);

        log.debug("Completed executing the saveConfiguration Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query the data set configuration.
     */

    @GET
    @Path("/querydataset/{datasetName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryDataset(@PathParam("datasetName") final String datasetName) {

        log.debug("Executing the Data Gen querydataset Service");

        JobConfiguration configuration = responseBuilder.queryDataset(datasetName);

        log.debug("Completed the Data Gen querydataset Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to query all datasets.
     */
    @GET
    @Path("/querydatasets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryDatasets() {

        log.debug("Executing the Data Gen queryDatasets Service");

        DataSetConfigurations configuration = responseBuilder.queryDatasets();

        log.debug("Completed the Data Gen queryDatasets Service");
        return Response.status(200).entity(configuration).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to update the data set configuration.
     */

    @POST
    @Path("/updatedataset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConfiguration(final JobConfiguration configuration) {

        log.debug("Executing the updateConfiguration Service");
        DataGenResponse processResponse = responseBuilder.updateConfiguration(configuration);

        log.debug("Completed executing the updateConfiguration Service");
        return Response.status(200).entity(processResponse).build();

    }

    /**
     * @param
     * @throws Exception
     * @Description :-The service to delete the dataset.
     */

    @POST
    @Path("/deletedataset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteConfiguration(final JobConfiguration configuration) {

        log.debug("Executing the Data Gen deleteConfiguration Service");

        DataGenResponse processResponse = responseBuilder.deleteConfiguration(configuration);

        log.debug("Completed executing the Data Gen deleteConfiguration Service");
        return Response.status(200).entity(processResponse).build();

    }

}