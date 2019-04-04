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

package org.datarapid.core.persistence.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description : Hibernate Configuration
 */

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {

    @Autowired
    private Environment envDetails;

    /* Data Source Details*/
    @Bean
    public DataSource dataSourceDetails() {
        DriverManagerDataSource dsDetails = new DriverManagerDataSource();
        dsDetails.setDriverClassName(envDetails.getRequiredProperty("jdbc.driverClassName"));
        dsDetails.setUrl(envDetails.getRequiredProperty("jdbc.url"));
        dsDetails.setUsername(envDetails.getRequiredProperty("jdbc.username"));
        dsDetails.setPassword(envDetails.getRequiredProperty("jdbc.password"));
        return dsDetails;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSourceDetails());
        sf.setPackagesToScan(new String[]{"org.datarapid.core.persistence.model"});
        sf.setHibernateProperties(hibProp());
        return sf;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    private Properties hibProp() {
        Properties prop = new Properties();
        prop.put("hibernate.dialect", envDetails.getRequiredProperty("hibernate.dialect"));
        prop.put("hibernate.show_sql", envDetails.getRequiredProperty("hibernate.show_sql"));
        prop.put("hibernate.format_sql", envDetails.getRequiredProperty("hibernate.format_sql"));
        return prop;
    }

}


