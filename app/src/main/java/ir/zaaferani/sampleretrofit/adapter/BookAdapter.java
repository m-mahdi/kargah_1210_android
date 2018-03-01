package ir.zaaferani.sampleretrofit.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import ir.zaaferani.sampleretrofit.G;
import ir.zaaferani.sampleretrofit.R;
import ir.zaaferani.sampleretrofit.model.Book;
import ir.zaaferani.sampleretrofit.service.BookApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Book>  books;
    private Activity activity;
    private BookApiService bookApiService;

    public BookAdapter(List<Book> books, Activity activity, BookApiService bookApiService) {
        this.books = books;
        this.activity = activity;
        this.bookApiService = bookApiService;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.book_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Book book = getItem(i);
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Map<String, String>> call = bookApiService.remove(G.getBearerToken(), book.getId());
                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()){
                            books.remove(i);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }

    static class ViewHolder{
        TextView title;
        TextView author;
        Button delete;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.title);
            author = (TextView) v.findViewById(R.id.author);
            delete = (Button) v.findViewById(R.id.delete);
        }
    }
}
