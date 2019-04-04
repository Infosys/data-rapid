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

import java.io.Serializable;
import java.util.List;

/**
 * @Description :- This class contains information about the column to be
 * bucketed.It contains fields such as bucketedColumnHeader,
 * and list of columnMapper.
 */
public class BucketedColumn implements Serializable {

    private String bucketedColumnHeader;
    private List<ColumnMapper> columnMapper;

    public BucketedColumn() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the bucketedColumnHeader
     */
    public String getBucketedColumnHeader() {
        return bucketedColumnHeader;
    }

    /**
     * @param bucketedColumnHeader the bucketedColumnHeader to set
     */
    public void setBucketedColumnHeader(String bucketedColumnHeader) {
        this.bucketedColumnHeader = bucketedColumnHeader;
    }

    /**
     * @return the columnMapper
     */
    public List<ColumnMapper> getColumnMapper() {
        return columnMapper;
    }

    /**
     * @param columnMapper the columnMapper to set
     */
    public void setColumnMapper(List<ColumnMapper> columnMapper) {
        this.columnMapper = columnMapper;
    }

    public BucketedColumn(String bucketedColumnHeader, List<ColumnMapper> columnMapper) {
        super();
        this.bucketedColumnHeader = bucketedColumnHeader;
        this.columnMapper = columnMapper;
    }

}
