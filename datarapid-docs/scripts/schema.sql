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
 
/*
SQLyog Community v10.11 
MySQL - 5.1.73 : Database - datarapid
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`datarapid` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `datarapid`;

/*Table structure for table `location_info` */

DROP TABLE IF EXISTS `location_info`;

CREATE TABLE `location_info` (
  `srno` varchar(11) NOT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `locationtext` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`srno`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `personal_info` */

DROP TABLE IF EXISTS `personal_info`;

CREATE TABLE `personal_info` (
  `srno` int(11) NOT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `emailaddress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`srno`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users`;

CREATE TABLE users (
	user_id INT(11) NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(30) NOT NULL,
	user_desc VARCHAR(30) NOT NULL,
	created_by VARCHAR(30),
	created_time DATETIME,
	updated_by VARCHAR(30),
	updated_time DATETIME,
	user_password VARCHAR(100) NOT NULL,
	PRIMARY KEY (user_id)
);


DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE user_roles (
	user_access_id 		INT(11) NOT NULL AUTO_INCREMENT,	
	user_name		VARCHAR(30) NOT NULL,
	role_name		VARCHAR(30) NOT NULL,
	role_desc 		VARCHAR(30) NOT NULL,
	role_type		VARCHAR(30) NOT NULL,
	group_users		VARCHAR(1000) ,
	usage_type		VARCHAR(30) NOT NULL,
	start_date		DATETIME,
	end_date		DATETIME,
	record_count_limit 	DECIMAL(20,0),
	usage_limit		DECIMAL(22,2),
	days_limit		DECIMAL(10,0),
	PRIMARY KEY (user_access_id)
);

DROP TABLE IF EXISTS `activity_log`;

CREATE TABLE activity_log (
	log_id INT(11) NOT NULL AUTO_INCREMENT,
	triggered_user_name VARCHAR(30) NOT NULL,
	activity_date DATETIME,
	generated_file_name VARCHAR(200),
	processed_record_count DECIMAL(20,0),
	processed_usage DECIMAL(22,2),
	PRIMARY KEY (log_id)
);

DROP TABLE IF EXISTS `dataset_configuration`;

CREATE TABLE dataset_configuration (
	config_id 		INT(11) NOT NULL AUTO_INCREMENT,			
	file_name 		VARCHAR(200) NOT NULL,
	user_name 		VARCHAR(30) NOT NULL,	
	created_time 		DATETIME,
	file_type 		VARCHAR(30),
	configuration_json 	LONGTEXT,
	configuration_details 	BLOB,
	PRIMARY KEY (config_id)
);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
