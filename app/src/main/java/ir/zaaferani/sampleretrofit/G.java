package ir.zaaferani.sampleretrofit;

import android.app.Application;

import retrofit2.Retrofit;

public class G extends Application {
    public static String baseUrl = "http://192.168.189.140:5000/";
    public static Retrofit retrofit = null;
    public static String token;

    protected static G instance;

    public G() {
        super();
        instance = this;
    }

    public static String getBearerToken(){
        return "Bearer " + token;
    }
}
