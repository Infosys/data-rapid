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

package org.datarapid.core.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtility {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtility.class);

    /**
     * @Description :-For initializing the security manager.
     */

    private SecurityUtility() {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory();
        org.apache.shiro.mgt.SecurityManager securityManager = factory
                .getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        System.out.println("Initializing SecurityUtility");
    }

    private static class SecurityUtil {
        private static final SecurityUtility INSTANCE = new SecurityUtility();
    }

    public static SecurityUtility getInstance() {
        return SecurityUtil.INSTANCE;
    }

    /**
     * @Description :-The method for authenticating user.
     */
    public boolean authenticateUser(String username, String password, String requestType) {

        boolean operationStatus = false;
        try {

            if (requestType.equalsIgnoreCase("login")) {
                log.info("Trying to login for the user " + username);
                operationStatus = userLogin(username, password, true);
                System.out.println("User logged in successfully.");
            }
            if (requestType.equalsIgnoreCase("logout")) {
                log.info("Trying to logout for the user " + username);
                org.apache.shiro.subject.Subject currentUser = SecurityUtils
                        .getSubject();
                currentUser.logout();
                operationStatus = true;
                System.out.println("User logged out successfully.");
            }

        } catch (Exception ex) {
            log.error("Error while authenticating : authenticateUser", ex);
        }
        return operationStatus;
    }

    /**
     * @Description :-The method for user login.
     */
    public boolean userLogin(String username, String password, Boolean rememberMe) {
        // get the currently executing user:
        org.apache.shiro.subject.Subject currentUser = SecurityUtils
                .getSubject();

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    password);
            token.setRememberMe(rememberMe);
            try {
                currentUser.login(token);
                System.out.println("User " + currentUser.getPrincipal().toString() + " logged in successfully.");
                currentUser.getSession().setAttribute("username", username);
                return true;
            } catch (UnknownAccountException uae) {
                System.out.println("No User : username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                System.out.println("Password " + token.getPrincipal() + " is incorrect!");
            } catch (LockedAccountException lae) {
                System.out.println("The username " + token.getPrincipal() + " is locked.");
            }
        } else {
            return true; // already logged in
        }
        return false;
    }

    /**
     * @Description :-The method for getting the current logged in user.
     */
    public String getCurrentUser() {
        // get the currently executing user:
        org.apache.shiro.subject.Subject currentUser = SecurityUtils
                .getSubject();
        String currentUserName = currentUser.getPrincipal().toString();
        if (!currentUser.isAuthenticated()) {
            System.out.println("User not logged in :" + currentUserName);
        } else {
            System.out.println("User already logged in :" + currentUserName);
        }
        return currentUserName;
    }

    /**
     * @Description :-The method for user logout.
     */
    public boolean userLogout() {

        boolean operationStatus = false;
        org.apache.shiro.subject.Subject currentUser = SecurityUtils
                .getSubject();
        String currentUserName = currentUser.getPrincipal().toString();
        log.info("Trying to logout for the user " + currentUserName);
        if (!currentUser.isAuthenticated()) {
            System.out.println("User not logged in : " + currentUserName);
        } else {
            System.out.println("User already logged in : " + currentUserName);
            currentUser.getSession().removeAttribute(currentUserName);
            currentUser.logout();
            operationStatus = true;
            System.out.println("User logged out successfully : " + currentUserName);
        }
        return operationStatus;
    }
    public String getEncryptedPassword(String plainTextPassword) {
        PasswordService service = new DefaultPasswordService();
        String hashedPasswordBase64 = service.encryptPassword(plainTextPassword);
        System.out.println(hashedPasswordBase64);
        return hashedPasswordBase64;
    }
    public static void main(String[] args) {
        PasswordService service = new DefaultPasswordService();
        String hashedPasswordBase64 = service.encryptPassword("password");
        System.out.println(hashedPasswordBase64);
    }
}