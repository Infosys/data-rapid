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

import java.util.ArrayList;
import java.util.List;

import org.datarapid.core.services.DataGenCoreServiceImpl;
import org.datarapid.core.view.RoleInfo;
import org.datarapid.core.view.RoleConfiguration;
import org.datarapid.core.view.RoleMeta;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

/**
 * @Description
 *
 */
public class StarterRoleManage {

	@SuppressWarnings("resource")
	ApplicationContext context = new ClassPathXmlApplicationContext("datagen_core.xml");
	DataGenCoreServiceImpl dataGenCoreServiceImpl = (DataGenCoreServiceImpl) context
			.getBean("dataGenCoreServiceImpl");
	
	public StarterRoleManage() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
				
		String str = "";
		
		try {
			StarterRoleManage starterRoleManage = new StarterRoleManage();
			
			//starterRoleManage.createrole();
			
			//starterRoleManage.queryrole();
			
			//starterRoleManage.queryallroles();
			
			//starterRoleManage.updaterole();
			
			starterRoleManage.deleterole();
			
			//starterRoleManage.getjsonformat();
			
			System.out.println("Completed the StarterRoleManage Processing");
		}
		catch (Exception e)
		{
			System.out.println("in exception");
			e.printStackTrace();
		}

		
	}
	
	public void createrole() throws Exception{
		
		RoleConfiguration configuration = new RoleConfiguration();
		RoleInfo roleInfo =  new RoleInfo();
		List<RoleMeta> roleMeta = new ArrayList<RoleMeta>();
		RoleMeta element = new RoleMeta();				
		element.setUserName("user1");
		element.setRoleName("ROLE_NORMAL");
		element.setRoleDesc("user1");
		element.setRoleType("individual");
		element.setGroupUsers("group");
		element.setUsageType("unlimited");
		element.setStartDate("2016-06-01");
		element.setEndDate("2016-07-01");
		element.setRecordCountLimit("10000");
		element.setUsageLimit("1000000");
		element.setDaysLimit("30");						
		roleMeta.add(element);
		roleInfo.setRoleMeta(roleMeta);
		configuration.setRoleInfo(roleInfo );
		
		boolean isRoleCreated = dataGenCoreServiceImpl.userRoleCreate(configuration);
	
		System.out.println("Completed the createrole");
	}
	
	public void queryrole() throws Exception{
			
		String userName = "user1";
		RoleConfiguration configuration = dataGenCoreServiceImpl.userRoleQuery(userName);
		System.out.println(configuration);
		System.out.println("Completed the queryrole");
	}	
	
	public void queryallroles() throws Exception{
				
		RoleConfiguration configuration = dataGenCoreServiceImpl.allUserRoleQuery();
		System.out.println(configuration);
		System.out.println("Completed the queryallrole");
	}	
	
	public void updaterole() throws Exception{
		
		RoleConfiguration configuration = new RoleConfiguration();
		RoleInfo roleInfo =  new RoleInfo();		
		List<RoleMeta> roleMeta = new ArrayList<RoleMeta>();
		RoleMeta element = new RoleMeta();		
		element.setUserName("user1");
		element.setRoleName("ROLE_NORMAL");
		element.setRoleDesc("user1updated");
		element.setRoleType("individual");
		element.setGroupUsers("group");
		element.setUsageType("unlimited");
		element.setStartDate("2016-06-01");
		element.setEndDate("2016-08-01");
		element.setRecordCountLimit("100000");
		element.setUsageLimit("10000000");
		element.setDaysLimit("300");		
		roleMeta.add(element);
		roleInfo.setRoleMeta(roleMeta);
		configuration.setRoleInfo(roleInfo );
		
		boolean isRoleUpdated = dataGenCoreServiceImpl.userRoleUpdate(configuration);
	
		System.out.println("Completed the updaterole");
	}
		
	
	public void deleterole() throws Exception{
		
		RoleConfiguration configuration = new RoleConfiguration();
		RoleInfo roleInfo =  new RoleInfo();		
		List<RoleMeta> roleMeta = new ArrayList<RoleMeta>();
		RoleMeta element = new RoleMeta();
		element.setUserName("user1");		
		roleMeta.add(element);
		roleInfo.setRoleMeta(roleMeta);
		configuration.setRoleInfo(roleInfo );
		
		boolean isRoledeleted = dataGenCoreServiceImpl.userRoleDelete(configuration);
	
		System.out.println("Completed the deleterole");
	}	
	
	public void getjsonformat() throws Exception {
		
		RoleConfiguration configuration = new RoleConfiguration();
		RoleInfo roleInfo =  new RoleInfo();		
		List<RoleMeta> roleMeta = new ArrayList<RoleMeta>();
		RoleMeta element = new RoleMeta();
		element.setUserName("user1");
		element.setRoleName("ROLE_NORMAL");
		element.setRoleDesc("user1");
		element.setRoleType("individual");
		element.setGroupUsers("group");
		element.setUsageType("unlimited");
		element.setStartDate("2016-06-01");
		element.setEndDate("2016-07-01");
		element.setRecordCountLimit("10000");
		element.setUsageLimit("1000000");
		element.setDaysLimit("30");		
		roleMeta.add(element);
		roleInfo.setRoleMeta(roleMeta);
		configuration.setRoleInfo(roleInfo );
		
		Gson gson = new Gson();
		String json = gson.toJson(configuration);

		System.out.println(json);
		//{"roleInfo":{"roleMeta":[{"userName":"user1","roleName":"ROLE_NORMAL","roleDesc":"user1","roleType":"individual","groupUsers":"group","usageType":"unlimited","startDate":"2016-06-01","endDate":"2016-07-01","recordCountLimit":"10000","usageLimit":"1000000","daysLimit":"30"}]}}
	}
}
