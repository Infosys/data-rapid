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
 * @Description :- This class contains information about the column to be
 * bucketed.It contains fields such as valuesFromSet,type,
 * valueType,dataType and percentageOfrows.
 */
public class ColumnMapper implements Serializable {

    String valuesFromSet;
    String type;
    String valueType;
    String dataType;
    String percentageOfrows;
    String values;

    public ColumnMapper() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the valuesFromSet
     */
    public String getValuesFromSet() {
        return valuesFromSet;
    }

    /**
     * @param valuesFromSet the valuesFromSet to set
     */
    public void setValuesFromSet(String valuesFromSet) {
        this.valuesFromSet = valuesFromSet;
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
     * @return the percentageOfrows
     */
    public String getPercentageOfrows() {
        return percentageOfrows;
    }

    /**
     * @param percentageOfrows the percentageOfrows to set
     */
    public void setPercentageOfrows(String percentageOfrows) {
        this.percentageOfrows = percentageOfrows;
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

    public ColumnMapper(String valuesFromSet, String type, String valueType, String dataType, String percentageOfrows,
                        String values) {
        this.valuesFromSet = valuesFromSet;
        this.type = type;
        this.valueType = valueType;
        this.dataType = dataType;
        this.percentageOfrows = percentageOfrows;
        this.values = values;
    }

}
