package info.pauek.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MovieActivity extends AppCompatActivity {

    private movie movie;
    private Gson gson;
    private TextView titleview;
    private TextView year;
    private TextView rated;
    private TextView runtime;
    private TextView genre;
    private TextView dir;
    private TextView writer;
    private TextView actors;
    private TextView plot;
    private TextView plot1;
    private ImageView poster;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        gson = new Gson();

        queue = Volley.newRequestQueue(this);


        try {
            InputStream stream = getAssets().open("lord.json");
            InputStreamReader reader = new InputStreamReader(stream);
            movie = gson.fromJson(reader, movie.class);
        } catch (IOException e) {
            Toast.makeText(this,"NO FUNCIONA",Toast.LENGTH_SHORT).show();
        }

        StringRequest req = new StringRequest(Request.Method.GET,
                "https://www.omdbapi.com/?apikey=70b16692&i=tt0486592", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                movie = gson.fromJson(response, movie.class);
                UpdateMovie();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieActivity.this,"Error de xarxa",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(req);

        poster = findViewById(R.id.posterview);

        Glide.with(this)
                .load("file:///android_asset/lord.jpg")
                .into(poster);

        plot1 = findViewById(R.id.plotview);
        plot1.setText(movie.getPlot());
    }

    private void UpdateMovie() {

        titleview = findViewById(R.id.titleview);
        titleview.setText(movie.getTitle());

        rated = findViewById(R.id.ratedview);
        rated.setText(movie.getRated());


        runtime = findViewById(R.id.runtimeview);
        runtime.setText(movie.getRuntime());

        genre = findViewById(R.id.genreview);
        genre.setText(movie.getGenre());

        dir = findViewById(R.id.directorview);
        dir.setText(movie.getDirector());

        writer = findViewById(R.id.writerview);
        writer.setText(movie.getWriter());

        actors = findViewById(R.id.actorsview);
        actors.setText(movie.getActors().replace(", ","\n"));


        year = findViewById(R.id.yearview);
        year.setText(movie.getYear());
    }
}

