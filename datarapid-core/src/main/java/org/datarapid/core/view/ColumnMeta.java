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

/**
 * @Description:-Contains information of each column to be generated.Contains
 * columnName,type,valueType,dataType,values and BucketedColumn
 * object.
 */
public class ColumnMeta implements Serializable {

    private String columnName;
    private String type;
    private String valueType;
    private String dataType;
    private String values;
    private String order;
    private BucketedColumn bucketedColumn;

    public ColumnMeta() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the valueType
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * @param valueType the valueType to set
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the values
     */
    public String getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(String values) {
        this.values = values;
    }

    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * @return the bucketedColumn
     */
    public BucketedColumn getBucketedColumn() {
        return bucketedColumn;
    }

    /**
     * @param bucketedColumn the bucketedColumn to set
     */
    public void setBucketedColumn(BucketedColumn bucketedColumn) {
        this.bucketedColumn = bucketedColumn;
    }

    public ColumnMeta(String columnName, String type, String valueType, String dataType, String values, String order,
                      BucketedColumn bucketedColumn) {
        super();
        this.columnName = columnName;
        this.type = type;
        this.valueType = valueType;
        this.dataType = dataType;
        this.values = values;
        this.order = order;
        this.bucketedColumn = bucketedColumn;
    }

}
