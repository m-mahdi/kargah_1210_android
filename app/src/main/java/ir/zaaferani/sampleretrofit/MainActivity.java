package ir.zaaferani.sampleretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import ir.zaaferani.sampleretrofit.model.LoginResponse;
import ir.zaaferani.sampleretrofit.service.LoginApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        if (G.retrofit == null){
            G.retrofit = new Retrofit.Builder()
                    .baseUrl(G.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginApiService service = G.retrofit.create(LoginApiService.class);
                String d = username.getText().toString() + ":" + password.getText().toString();

                try {
                    byte[] bytes = d.getBytes("UTF-8");
                    String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                    base64 = "Basic " + base64;
                    Call<LoginResponse> call = service.login(base64);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()){
                                G.token = response.body().getToken();
                                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                                startActivity(intent);
                                finish();
//                                Toast.makeText(MainActivity.this, "login ok :))))", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
