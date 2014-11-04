package com.conserata.beer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Entity
@Table(name = "usersession")
public class UserSession extends BaseEntity {

    private String sessionToken;


    // should be linked to the User entity in real projects
    private String username;

    // normally this should be handled with DateTime or similar
    private Boolean expired;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        if (expired != null ? !expired.equals(that.expired) : that.expired != null) return false;
        if (sessionToken != null ? !sessionToken.equals(that.sessionToken) : that.sessionToken != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionToken != null ? sessionToken.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "sessionToken='" + sessionToken + '\'' +
                ", username='" + username + '\'' +
                ", expired=" + expired +
                '}';
    }
}
