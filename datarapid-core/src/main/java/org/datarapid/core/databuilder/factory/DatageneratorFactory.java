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

package org.datarapid.core.databuilder.factory;

import org.datarapid.core.databuilder.DataGenerator;
import org.datarapid.core.databuilder.datatypes.*;

/**
 * @Description This class will return the class corresponding to the data-type
 * given.
 */
public class DatageneratorFactory {

    /**
     * @param dataType
     * @Description This method will return the class corresponding to the
     * data-type given.
     */
    public DataGenerator getDataGenerator(String dataType) {

        if (dataType.equalsIgnoreCase("ipaddress")) {
            return new IPAddress();
        } else if (dataType.equalsIgnoreCase("alphanumeric")) {
            return new AlphaNumeric();
        } else if (dataType.equalsIgnoreCase("numberformat")) {
            return new NumberFormat();
        } else if (dataType.equalsIgnoreCase("digitformat")) {
            return new DigitFormat();
        } else if (dataType.equalsIgnoreCase("phonenumber")) {
            return new PhoneNumber();
        } else if (dataType.equalsIgnoreCase("firstname")) {
            return new FirstName();
        } else if (dataType.equalsIgnoreCase("lastname")) {
            return new LastName();
        } else if (dataType.equalsIgnoreCase("emailaddress")) {
            return new EmailAddress();
        } else if (dataType.equalsIgnoreCase("city")) {
            return new City();
        } else if (dataType.equalsIgnoreCase("state")) {
            return new State();
        } else if (dataType.equalsIgnoreCase("zipcode")) {
            return new ZipCode();
        } else if (dataType.equalsIgnoreCase("country")) {
            return new Country();
        } else if (dataType.equalsIgnoreCase("location")) {
            return new Location();
        } else if (dataType.equalsIgnoreCase("hexadecimalcode")) {
            return new HexadecimalCode();
        } else if (dataType.equalsIgnoreCase("password")) {
            return new Password();
        } else if (dataType.equalsIgnoreCase("visacreditcardnumber")) {
            return new VisaCreditCardNumber();

        } else if (dataType.equalsIgnoreCase("mastercardnumber")) {
            return new MastercardNumber();
        } else if (dataType.equalsIgnoreCase("americancard")) {
            return new AmericanCard();
        } else if (dataType.equalsIgnoreCase("pancardnumber")) {
            return new PanCardNumber();
        } else if (dataType.equalsIgnoreCase("cvv")) {
            return new CVV();
        } else if (dataType.equalsIgnoreCase("hexcolors")) {
            return new HexColors();
        } else if (dataType.equalsIgnoreCase("ssn")) {
            return new SSN();
        } else if (dataType.equalsIgnoreCase("guid")) {
            return new Guid();
        } else if (dataType.equalsIgnoreCase("integerrange")) {
            return new IntegerRange();
        } else if (dataType.equalsIgnoreCase("defaultset")) {
            return new DefaultSet();
        } else if (dataType.equalsIgnoreCase("uniquevalues")) {
            return new UniqueValues();
        } else if (dataType.equalsIgnoreCase("date")) {
            return new Date();
        } else if (dataType.equalsIgnoreCase("timestamp")) {
            return new Timestamp();
        } else if (dataType.equalsIgnoreCase("rcommand")) {
            return new RCommand();
        } else if (dataType.equalsIgnoreCase("phonenumberwithext")) {
            return new PhoneNumberWithExt();
        } else if (dataType.equalsIgnoreCase("userdefinedregex")) {
            return new UserDefinedRegexPattern();
        } else if (dataType.equalsIgnoreCase("macaddress")) {
            return new MACAddress();
        } else if (dataType.equalsIgnoreCase("floatrange")) {
            return new FloatRange();
        } else if (dataType.equalsIgnoreCase("imei")) {
            return new IMEI();
        } else if (dataType.equalsIgnoreCase("DiscoveryCreditCard")) {
            return new DiscoveryCreditCard();
        } else if (dataType.equalsIgnoreCase("incrementalUniqueValues")) {
            return new IncrementalUniqueValues();
        }

        return null;

    }

}
