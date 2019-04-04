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

import org.datarapid.core.databuilder.DataGenerator;
import org.datarapid.core.util.Rconnection;

import java.util.List;

/**
 * @Description Generates a list of unique numbers between the user input range.
 */
public class UniqueValues implements DataGenerator {

    public List<String> getColumn(String uniqueValueGeneration, int numRows) {

        List<String> list = getUniqueValues(uniqueValueGeneration.trim(), numRows);
        return list;
    }

    private List<String> getUniqueValues(String uniqueValueGeneration, int noOfRows) {

        Rconnection rconnection = new Rconnection();
        List<String> list = rconnection.createUniquekeys(uniqueValueGeneration, noOfRows);

        return list;
    }

}
