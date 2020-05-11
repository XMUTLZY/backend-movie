package sch.personal.backendmovie.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminRequest {
    @JsonProperty("user_name")
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
