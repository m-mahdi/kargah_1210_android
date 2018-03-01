package ir.zaaferani.sampleretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.zaaferani.sampleretrofit.adapter.BookAdapter;
import ir.zaaferani.sampleretrofit.model.Book;
import ir.zaaferani.sampleretrofit.model.BookResponse;
import ir.zaaferani.sampleretrofit.service.BookApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        final BookApiService service = G.retrofit.create(BookApiService.class);
        books = new ArrayList<>();

        final BookAdapter adapter = new BookAdapter(books, this, service);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Call<BookResponse> call = service.list(G.getBearerToken());

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful()){
                    books.clear();
                    books.addAll(response.body().getBooks());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BookActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(BookActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Book> call = service.add(G.getBearerToken(), new Book(null, "Book two", "Hassan"));
                call.enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        if(response.isSuccessful()){
                            books.add(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        Toast.makeText(BookActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
