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

package org.datarapid.core.view;

/**
 * @Description :- Contains all the file information such as
 * fileName,numberOfRows,fileType and FileInfo object.
 */
public class UIConfiguration {

    private String dataTypeName;
    private String dataLimit;
    private UIFileInfo uiFileInfo;

    public UIConfiguration() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the dataTypeName
     */
    public String getDataTypeName() {
        return dataTypeName;
    }

    /**
     * @param dataTypeName the dataTypeName to set
     */
    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    /**
     * @return the dataLimit
     */
    public String getDataLimit() {
        return dataLimit;
    }

    /**
     * @param dataLimit the dataLimit to set
     */
    public void setDataLimit(String dataLimit) {
        this.dataLimit = dataLimit;
    }

    public UIFileInfo getUiFileInfo() {
        return uiFileInfo;
    }

    public void setUiFileInfo(UIFileInfo uiFileInfo) {
        this.uiFileInfo = uiFileInfo;
    }

    public UIConfiguration(String dataTypeName, String dataLimit, UIFileInfo uiFileInfo) {
        super();
        this.dataTypeName = dataTypeName;
        this.dataLimit = dataLimit;
        this.uiFileInfo = uiFileInfo;
    }

}
