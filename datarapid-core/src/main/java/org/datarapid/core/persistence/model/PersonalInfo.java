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

package org.datarapid.core.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description Bean class for personalInfo which includes firstname, lastname
 * and email address.
 */

@Entity
@Table(name = "personal_info")
public class PersonalInfo {

    @Id
    @Column(name = "SrNo")
    private String SrNo;

    @Column(name = "Firstname")
    private String FirstName;

    @Column(name = "LastName")
    private String LastName;

    @Column(name = "EmailAddress")
    private String EmailAddress;

    /**
     * @return the srNo
     */
    public String getSrNo() {
        return SrNo;
    }

    /**
     * @param srNo the srNo to set
     */
    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return EmailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hashout = 1;

        hashout = 31 * hashout +
                ((EmailAddress == null) ? 0 : EmailAddress.hashCode());
        hashout = 31 * hashout +
                ((FirstName == null) ? 0 : FirstName.hashCode());
        hashout = 31 * hashout +
                ((LastName == null) ? 0 : LastName.hashCode());
        hashout = 31 * hashout +
                ((SrNo == null) ? 0 : SrNo.hashCode());
        return hashout;

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        PersonalInfo other = (PersonalInfo) obj;

        if (EmailAddress == null) {
            if (other.EmailAddress != null)
                return false;
        } else if (!EmailAddress.equals(other.EmailAddress))
            return false;
        if (FirstName == null) {
            if (other.FirstName != null)
                return false;
        } else if (!FirstName.equals(other.FirstName))
            return false;
        if (LastName == null) {
            if (other.LastName != null)
                return false;
        } else if (!LastName.equals(other.LastName))
            return false;
        if (SrNo == null) {
            if (other.SrNo != null)
                return false;
        } else if (!SrNo.equals(other.SrNo))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmailAddress [SrNo=").append(SrNo).append(", FirstName=").append(FirstName)
                .append(", LastName=").append(LastName).append(", EmailAddress=").append(EmailAddress).append("]");
        return builder.toString();
    }

}
