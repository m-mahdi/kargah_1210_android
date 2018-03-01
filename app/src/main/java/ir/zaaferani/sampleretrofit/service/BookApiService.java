package ir.zaaferani.sampleretrofit.service;


import java.util.Map;

import ir.zaaferani.sampleretrofit.model.Book;
import ir.zaaferani.sampleretrofit.model.BookResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookApiService {
    @GET("/book/list")
    Call<BookResponse> list(@Header("Authorization") String token);

    @POST("/book/add")
    Call<Book> add(@Header("Authorization") String token, @Body Book book);

    @DELETE("/book/{id}")
    Call<Map<String, String>> remove(
            @Header("Authorization") String token, @Path("id") Integer id);
}
