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
 * Generic Constants Interface for Data gen.
 */
public interface Constants {

    public static final String STANDARD_INPUT_FORMAT = "dd/MM/yyyy";
    public static final String STANDARD_TIMESTAMP_OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String COMMA_SPLIT = ",";
    public static final String CVVREGEX = "^[1-9]{3}$";
    public static final String GUIDREGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
    public static final String HEXADECIMALREGEX = "^#([a-f0-9]{6}|[a-f0-9]{3})$";
    public static final String IPADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static final String HEXCOLORSREGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    public static final String MACADDRESS = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    public static final String AMERICANCARDREGEX = "^3[47][0-9]{13}$";
    public static final String MASTERCARDNUMBER = "^5[1-5][0-9]{14}$";
    public static final String VISACREDITCARDNUMBER = "^4[0-9]{12}(?:[0-9]{3})?$";
    public static final String PANCARDNUMBERREGEX = "^[A-Z0-9]{10}$";
    public static final String PASSWORDREGEX = "[a-zA-Z_@0-9]{8}";
    public static final String PHONENUMBERREGEX = "^((?!000)(?!666)(?:[0-6]\\d{2}|7[0-2][0-9]|73[0-3]|7[5-6][0-9]|77[0-2]))-((?!00)\\d{2})-((?!0000)\\d{4})$";
    public static final String PHONENUMBERWITHEXTREGEX = "^([2-9]\\d{2})( )([2-9]\\d{2})( )(\\d{4})( ext)(\\d{3})$";
    public static final String SSNREGEX = "^((?!000)(?!666)(?:[0-6]\\d{2}|7[0-2][0-9]|73[0-3]|7[5-6][0-9]|77[0-2]))-((?!00)\\d{2})-((?!0000)\\d{4})$";
    public static final String IMEIREGEX = "^[1-9]{15}$";
    public static final String DISCOVERYCARDREGEXPATTERN = "^6(?:011|5[0-9]{2})[0-9]{12}$";

    public static final String TIMESTAMP_OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String HIGHDATE = "2099-12-31 23:59:59";
    public static final String MAX_RECORD_COUNT_LIMIT = "99999999999999999999";
    public static final String MAX_USAGE_LIMIT = "99999999999999999999.99";
    public static final String MAX_DAYS_LIMIT = "9999999999";
    public static final String DEFAULT_ROLE_NAME = "ROLE_NORMAL_USER";
    public static final String DEFAULT_USAGE_TYPE = "UNLIMITED";
}
