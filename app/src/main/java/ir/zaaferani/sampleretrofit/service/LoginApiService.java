package ir.zaaferani.sampleretrofit.service;

import ir.zaaferani.sampleretrofit.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApiService {
    @POST("/login")
    Call<LoginResponse> login(@Header("Authorization") String authorization);
}
