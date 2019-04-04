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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Property Util
 */
public class PropertyUtil {

    private static final Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * Loading contents from the file into a {@link java.util.Properties}
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public Properties loadPropertyFromClasspath(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (null == stream) {
            log.error("{} is not available in classpath", fileName);
            throw new IOException(fileName + " is not available in classpath");
        }
        properties.load(stream);
        return properties;
    }

}
