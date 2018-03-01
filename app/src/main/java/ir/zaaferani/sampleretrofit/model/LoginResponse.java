package ir.zaaferani.sampleretrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse  implements Serializable {

    @SerializedName("token")
    private String token;

    @SerializedName("duration")
    private Integer duration;

    public LoginResponse() {
    }

    public LoginResponse(String token, Integer duration) {
        this.token = token;
        this.duration = duration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
