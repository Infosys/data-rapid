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
 * @Description Bean class for LocationInfo such as city,country,zip-code and
 * location.
 */

@Entity
@Table(name = "location_info")
public class LocationInfo {

    @Id
    @Column(name = "SrNo")
    private String srNo;

    @Column(name = "Zipcode")
    private String Zipcode;

    @Column(name = "City")
    private String City;

    @Column(name = "State")
    private String State;

    @Column(name = "Country")
    private String Country;

    @Column(name = "LocationText")
    private String LocationText;

    @Column(name = "Location")
    private String Location;

    /**
     * @return the srNo
     */
    public String getSrNo() {
        return srNo;
    }

    /**
     * @param srNo the srNo to set
     */
    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return Zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return City;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        City = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return State;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        State = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        Country = country;
    }

    /**
     * @return the locationText
     */
    public String getLocationText() {
        return LocationText;
    }

    /**
     * @param locationText the locationText to set
     */
    public void setLocationText(String locationText) {
        LocationText = locationText;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        Location = location;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LocationInfo [srNo=").append(srNo).append(", Zipcode=").append(Zipcode).append(", City=")
                .append(City).append(", State=").append(State).append(", Country=").append(Country)
                .append(", LocationText=").append(LocationText).append(", Location=").append(Location).append("]");
        return builder.toString();
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
                ((City == null) ? 0 : City.hashCode());
        hashout = 31 * hashout +
                ((Country == null) ? 0 : Country.hashCode());
        hashout = 31 * hashout +
                ((Location == null) ? 0 : Location.hashCode());
        hashout = 31 * hashout +
                ((LocationText == null) ? 0 : LocationText.hashCode());
        hashout = 31 * hashout +
                ((State == null) ? 0 : State.hashCode());
        hashout = 31 * hashout +
                ((Zipcode == null) ? 0 : Zipcode.hashCode());
        hashout = 31 * hashout +
                ((srNo == null) ? 0 : srNo.hashCode());
        return hashout;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object datagenObj) {

        if (this == datagenObj) {
            return true;
        }

        if (datagenObj == null) {
            return false;
        }

        if (getClass() != datagenObj.getClass()) {
            return false;
        }

        LocationInfo other = (LocationInfo) datagenObj;

        if (City == null) {
            if (other.City != null)
                return false;
        } else if (!City.equals(other.City))
            return false;
        if (Country == null) {
            if (other.Country != null)
                return false;
        } else if (!Country.equals(other.Country))
            return false;
        if (Location == null) {
            if (other.Location != null)
                return false;
        } else if (!Location.equals(other.Location))
            return false;
        if (LocationText == null) {
            if (other.LocationText != null)
                return false;
        } else if (!LocationText.equals(other.LocationText))
            return false;
        if (State == null) {
            if (other.State != null)
                return false;
        } else if (!State.equals(other.State))
            return false;
        if (Zipcode == null) {
            if (other.Zipcode != null)
                return false;
        } else if (!Zipcode.equals(other.Zipcode))
            return false;
        if (srNo == null) {
            if (other.srNo != null)
                return false;
        } else if (!srNo.equals(other.srNo))
            return false;
        return true;
    }

}
