package com.bbc.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {

    private String username;
    private String accessToken;
    private Date creationTime;

    private int numOfNotificationsPushed;

    public User(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
        creationTime = new Date();
        numOfNotificationsPushed = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date getCreationTime() {
        return creationTime;
    }

    public int getNumOfNotificationsPushed() {
        return numOfNotificationsPushed;
    }

    public void incrementNumOfNotificationsPushed() {
        numOfNotificationsPushed++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result
                + ((creationTime == null) ? 0 : creationTime.hashCode());
        result = prime * result + numOfNotificationsPushed;
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (accessToken == null) {
            if (other.accessToken != null)
                return false;
        } else if (!accessToken.equals(other.accessToken))
            return false;
        if (creationTime == null) {
            if (other.creationTime != null)
                return false;
        } else if (!creationTime.equals(other.creationTime))
            return false;
        if (numOfNotificationsPushed != other.numOfNotificationsPushed)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
