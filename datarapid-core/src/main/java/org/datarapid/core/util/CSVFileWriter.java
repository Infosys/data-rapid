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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Description This class receives the final List of lists to be written to
 * the CSV file. This class has a parameterized constructor which
 * initializes the file header,number of rows to be printed and
 * file name.
 */
public class CSVFileWriter {

    static final String COMMA_DELIMITER = ",";
    static final String NEW_LINE_SEPARATOR = "\n";
    final static Logger logger = LoggerFactory.getLogger(CSVFileWriter.class);

    private int noOfValues;

    private String FILE_HEADER;
    private String FILE_NAME;

    public CSVFileWriter(int noOfRows, List<String> fileHeader, String fILE_NAME) {

        this.noOfValues = noOfRows;
        FILE_NAME = fILE_NAME;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fileHeader.size(); i++) {
            builder.append(fileHeader.get(i)).append(COMMA_DELIMITER);
        }
        builder.replace(builder.lastIndexOf(COMMA_DELIMITER), builder.length(), "");
        FILE_HEADER = builder.toString();

    }


    /**
     * @param finalList
     * @throws Exception
     * @Description This method opens the FileWriter and writes the rows one by one to the
     * CSV file and after it has finished writing it closes the file.
     */
    public void writeFile(List<List<String>> finalList) throws Exception {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(FILE_NAME);

            // Write the CSV file header
            fileWriter.append(FILE_HEADER);

            // Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (int k = 0; k < noOfValues; k++) {

                for (int i = 0; i < finalList.size(); i++) {

                    if (finalList.get(i).size() > 0) {
                        fileWriter.append(finalList.get(i).get(k));
                        if (!(i == finalList.size() - 1)) {
                            fileWriter.append(COMMA_DELIMITER);

                        }
                    }
                }

                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file created successfully !!!");

        } catch (Exception e) {

            logger.error("Error in CsvFileWriter !!!", e);
            throw e;

        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                logger.error("Error while flushing/closing fileWriter !!!", e);
            }

        }

    }

}
