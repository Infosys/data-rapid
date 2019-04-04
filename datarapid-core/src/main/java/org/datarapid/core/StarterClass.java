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

package org.datarapid.core;

import org.datarapid.core.services.DataGenCoreServiceImpl;
import org.datarapid.core.view.ColumnMeta;
import org.datarapid.core.view.FileInfo;
import org.datarapid.core.view.JobConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : Starter Class
 */
public class StarterClass {

    public static void main(String[] args) {

        //ColumnMapper columnMapper1 = new ColumnMapper("Aaron", "cnst","expression", "defaultset", "50","");

        ColumnMeta meta1 = new ColumnMeta("firstname", "cnst", "expression", "FirstName", "Aaron,Arun", "1", null);
        ColumnMeta meta2 = new ColumnMeta("LastNames", "cnst", "expression", "lastname", "", "2", null);

        ColumnMeta meta3 = new ColumnMeta("Location", "cnst", "expression", "location", "", "3", null);
        ColumnMeta meta4 = new ColumnMeta("IpAddress", "cnst", "expression", "IpAddress", "", "4", null);
        ColumnMeta meta5 = new ColumnMeta("EmailAddresses", "cnst", "expression", "emailaddress", "", "5", null);
        ColumnMeta meta6 = new ColumnMeta("Problem Areas", "cnst", "expression", "defaultset", "A,B,C,D,E,F", "6", null);

        ColumnMeta meta7 = new ColumnMeta("IntegerRange", "cnst", "expression", "IntegerRange", "-1000to500", "7",
                null);
        ColumnMeta meta8 = new ColumnMeta("NumberFormat", "cnst", "expression", "NumberFormat", "4,2", "8", null);
        ColumnMeta meta9 = new ColumnMeta("Digits", "cnst", "expression", "digitFormat", "6", "9", null);
        ColumnMeta meta10 = new ColumnMeta("PhoneNumber", "cnst", "expression", "phonenumber", "", "10", null);

        ColumnMeta meta11 = new ColumnMeta("Date", "cnst", "expression", "Date", "01/01/2010-01/01/2015#dd/MMM/yyyy",
                "11", null);

        ColumnMeta meta12 = new ColumnMeta("TimeStamp", "cnst", "expression", "TimeStamp", "01/01/2010-01/01/2015",
                "12", null);

        ColumnMeta meta13 = new ColumnMeta("alphanumeric", "cnst", "expression", "alphanumeric", "2,2", "13", null);

        ColumnMeta meta14 = new ColumnMeta("city", "cnst", "expression", "city", "", "14", null);
        ColumnMeta meta15 = new ColumnMeta("Zipcode", "cnst", "expression", "zipcode", "", "15", null);
        ColumnMeta meta16 = new ColumnMeta("country", "cnst", "expression", "country", "", "16", null);

        ColumnMeta meta17 = new ColumnMeta("HexadecimalCode", "cnst", "expression", "HexadecimalCode", "", "17", null);

        ColumnMeta meta18 = new ColumnMeta("Password", "cnst", "expression", "Password", "", "18", null);
        ColumnMeta meta19 = new ColumnMeta("visaCreditCardNumber", "cnst", "expression", "visaCreditCardNumber", "",
                "19", null);
        ColumnMeta meta20 = new ColumnMeta("mastercardNumber", "cnst", "expression", "mastercardNumber", "", "20",
                null);
        ColumnMeta meta21 = new ColumnMeta("AmericanCard", "cnst", "expression", "AmericanCard", "", "21", null);
        ColumnMeta meta22 = new ColumnMeta("PanCardNumber", "cnst", "expression", "PanCardNumber", "", "22", null);
        ColumnMeta meta23 = new ColumnMeta("cvv", "cnst", "expression", "cvv", "", "23", null);
        ColumnMeta meta24 = new ColumnMeta("hexColors", "cnst", "expression", "hexColors", "", "24", null);
        ColumnMeta meta25 = new ColumnMeta("ssn", "cnst", "expression", "ssn", "", "25", null);
        ColumnMeta meta26 = new ColumnMeta("PhoneNumberWithExt", "cnst", "expression", "PhoneNumberWithExt", "", "26",
                null);

        ColumnMeta meta27 = new ColumnMeta("guid", "cnst", "expression", "guid", "", "27", null);

        ColumnMeta meta28 = new ColumnMeta("UniqueValues", "cnst", "expression", "UniqueValues", "1-1000", "28", null);
        ColumnMeta meta29 = new ColumnMeta("Rcolumn1", "cnst", "expression", "RCommand",
                "rnorm(n=100000,mean=105,sd=5.2)", "29", null);
        ColumnMeta meta30 = new ColumnMeta("User defined Pattern", "cnst", "expression", "UserDefinedRegex",
                "[1-9]{3}\\.[0-9]{2}", "30", null);

        ColumnMeta meta31 = new ColumnMeta("MACaddress", "cnst", "expression", "MACaddress", "", "31", null);

        ColumnMeta meta32 = new ColumnMeta("FloatRange", "cnst", "expression", "FLOATRANGE", "-250 to 1000", "32",
                null);

        // *************************************************************************************************************************************//

        // for bucketing purpose only

		/*
         * ColumnMapper columnMapper1 = new ColumnMapper("a", "cnst",
		 * "expression", "Rcommand", "10","rnorm(n=10000,mean=101,sd=5.2)");
		 *
		 * ColumnMapper columnMapper2 = new ColumnMapper("b", "cnst",
		 * "expression", "NumberFormat", "10", "4,2"); ColumnMapper
		 * columnMapper3 = new ColumnMapper("c", "cnst", "expression",
		 * "DigitFormat", "10", "4"); ColumnMapper columnMapper4 = new
		 * ColumnMapper("d", "cnst", "expression", "Ipaddress", "10", "4,2");
		 * ColumnMapper columnMapper5 = new ColumnMapper("e", "cnst",
		 * "expression", "PanCardNumber", "10", ""); ColumnMapper columnMapper6
		 * = new ColumnMapper("f", "cnst", "expression", "mastercardNumber",
		 * "10", ""); ColumnMapper columnMapper7 = new ColumnMapper("g", "cnst",
		 * "expression", "HexColors", "10", ""); ColumnMapper columnMapper8 =
		 * new ColumnMapper("h", "cnst", "expression", "FirstName", "10", "");
		 * ColumnMapper columnMapper9 = new ColumnMapper("i", "cnst",
		 * "expression", "EmailAddress", "10", ""); ColumnMapper columnMapper10
		 * = new ColumnMapper("j", "cnst", "expression", "VisaCreditCardNumber",
		 * "10", "");
		 *
		 *
		 * // ColumnMapper columnMapper3=new //
		 * ColumnMapper(valuesFromSet,type,valueType, //
		 * dataType,percentageOfrows,values)
		 *
		 * // ColumnMapper columnMapper3 = new ColumnMapper("CCC", "cnst", //
		 * "expression", "defaultSet", "a,b,c,d,e", "");
		 *
		 * List<ColumnMapper> listMapper = new ArrayList<ColumnMapper>();
		 * listMapper.add(columnMapper1); listMapper.add(columnMapper2);
		 * listMapper.add(columnMapper3); listMapper.add(columnMapper4);
		 * listMapper.add(columnMapper5); listMapper.add(columnMapper6);
		 * listMapper.add(columnMapper7); listMapper.add(columnMapper8);
		 * listMapper.add(columnMapper9); listMapper.add(columnMapper10);
		 *
		 * BucketedColumn bucketedColumn = new
		 * BucketedColumn("BucketedColumnName", listMapper); ColumnMeta
		 * metaBucket = new ColumnMeta("ColumnParent", "cnst", "EXPRESION",
		 * "firstName", "", "1", bucketedColumn);
		 *
		 * ColumnMeta metaBucket2 = new ColumnMeta("ColumnParent", "cnst",
		 * "EXPRESION", "firstName", "", "1",bucketedColumn);
		 */

        // ******************************************************************************************************************

        List<ColumnMeta> list = new ArrayList<ColumnMeta>();
		/*
		 * list.add(metaBucket2); list.add(metaBucket);
		 */
        list.add(meta1);
        list.add(meta2);
        list.add(meta3);
        list.add(meta4);
        list.add(meta5);
        list.add(meta6);
        list.add(meta7);
        list.add(meta8);
        list.add(meta9);
        list.add(meta10);
        list.add(meta11);
        list.add(meta12);
        list.add(meta13);
        list.add(meta14);
        list.add(meta15);
        list.add(meta16);
        list.add(meta17);
        list.add(meta18);
        list.add(meta19);
        list.add(meta20);
        list.add(meta21);
        list.add(meta22);
        list.add(meta23);
        list.add(meta24);
        list.add(meta25);
        list.add(meta26);
        list.add(meta27);
        list.add(meta28);
        list.add(meta29);
        list.add(meta30);
        list.add(meta31);
        list.add(meta32);

        FileInfo fileInfo = new FileInfo(list);
        JobConfiguration configuration = new JobConfiguration("testingFile.csv", fileInfo, "1000", "CSV");

        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("datagen_core.xml");
        DataGenCoreServiceImpl dataGenCoreServiceImpl = (DataGenCoreServiceImpl) context
                .getBean("dataGenCoreServiceImpl");

        try {

            String finalMessage = dataGenCoreServiceImpl.buildData(configuration);
            System.out.println(finalMessage);
        } catch (Exception e) {
            System.out.println("in exception");
            e.printStackTrace();
        }

        System.out.println("over and out");
    }
}
