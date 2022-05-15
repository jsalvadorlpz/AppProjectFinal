package com.example.entrega4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleSerieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company,idioma;
    ImageView poster;
    Activity activity;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    LayoutInflater inflater;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);

        titulo = findViewById(R.id.TitleSerie);
        genre = findViewById(R.id.GenreSerie);
        date = findViewById(R.id.dateSerie);
        sinopsis = findViewById(R.id.sinopsisSerie);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageViewSerie);
        company = findViewById(R.id.prod_compa_Serie);
        idioma = findViewById(R.id.idio);

        Integer id =Integer.parseInt(getIntent().getStringExtra("idSerie"));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

        Call<SeriesDetailsResults> call3 = myInterface.listOfSerieDetals(id, API_KEY);
        call3.enqueue(new Callback<SeriesDetailsResults>() {
            @Override
            public void onResponse(Call<SeriesDetailsResults> call, Response<SeriesDetailsResults> response) {
                SeriesDetailsResults results = response.body();

                titulo.setText(results.getName());

                //List<SeriesDetailsResults.Genre>  generos = ;
               // genre.setText();
                //company.setText(CompnayName);
                //titulo.setText(results.getName() "("+ results.getName()+")");
                //sinopsis.setText(results.get);
                //date.setText(results.getReleaseDate());
                //idioma.setText(results.getOriginalLanguage());
                //image = (String) results.getPosterPath();


            }
            @Override
            public void onFailure(Call<SeriesDetailsResults> call, Throwable t) {

            }
        });

        String image_peli = getIntent().getStringExtra("imageSerie");
        Glide.with(this).load(url_imagenes+image_peli).into(poster);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("","se destruye el detalle movie");
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }
}