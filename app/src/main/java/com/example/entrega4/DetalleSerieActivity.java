package com.example.entrega4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.Adapter.RecyclerAdapterActoresTv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleSerieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company,idioma;
    ImageView poster,poster2;
    Activity activity;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    RecyclerView recyclerView;
    List<CreditResultTv.Cast> listaActores,lista10Actores;
    RecyclerAdapterActoresTv recyclerAdapterActoresTv;
    Integer idSerie,id2;
    public  String back,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);

        //RECYCLER Y ADAPTER DE ACTORES
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewActoresSeries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerAdapterActoresTv = new RecyclerAdapterActoresTv(this,new ArrayList<>(),id2);
        recyclerView.setAdapter(recyclerAdapterActoresTv);
        listaActores = new ArrayList<CreditResultTv.Cast>();
        lista10Actores = new ArrayList<CreditResultTv.Cast>();

        titulo = findViewById(R.id.TitleSerie);
        genre = findViewById(R.id.GenreSerie);
        date = findViewById(R.id.dateSerie);
        sinopsis = findViewById(R.id.sinopsisSerie);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageViewSerieBack);
        poster2 = findViewById(R.id.imageViewSerie);
        company = findViewById(R.id.prod_compa_Serie);
        idioma = findViewById(R.id.idio);

        Integer id =Integer.parseInt(getIntent().getStringExtra("idSerie"));
        idSerie = id;
        id2 = id;
        Intent actores  = new Intent(this,ActoresMovie.class);
        Bundle extras = new Bundle();
        extras.putInt("idepeli",idSerie);

        getCredits(idSerie);

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

                titulo.setText(results.getName()+ "(" +results.getOriginalName()+ ")");

                //List<SeriesDetailsResults.Genre>  generos = ;
               // genre.setText();
                //company.setText(CompnayName);
                //titulo.setText(results.getName() "("+ results.getName()+")");
                sinopsis.setText(results.getOverview());
                date.setText(results.getFirstAirDate());
                idioma.setText(results.getOriginalLanguage());
                //image = (String) results.getPosterPath();
                back = results.getBackdropPath();
                image = results.getPosterPath();
                setImage(back,image);

            }
            @Override
            public void onFailure(Call<SeriesDetailsResults> call, Throwable t) {

            }
        });

    }
    public void setImage(String back,String image){
        Glide.with(this).load(url_imagenes+back).into(poster);
        Glide.with(this).load(url_imagenes+image).into(poster2);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("","se destruye el detalle movie");
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

    public void getCredits(Integer id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

        Call<CreditResultTv> call = myInterface.listOfCreditTv(id,API_KEY);
        call.enqueue(new Callback<CreditResultTv>() {
            @Override
            public void onResponse(Call<CreditResultTv> call, Response<CreditResultTv> response) {
                CreditResultTv results = response.body();
                List<CreditResultTv.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                if(listaActores.size() !=0) {
                    Log.e("","Esta lista tiene: "+String.valueOf(listOfActors.size()));
                    int iterador = 0;
                    if (listaActores.size() >= 10) {
                        while (iterador < 10) {
                            lista10Actores.add(listaActores.get(iterador));
                            iterador++;
                        }
                    } else {
                        while (iterador < listaActores.size()) {
                            lista10Actores.add(listaActores.get(iterador));
                            iterador++;
                        }
                    }
                    recyclerAdapterActoresTv.updateDataActoresTv(lista10Actores, id2);
                }else{
                    Log.e("","Esta serie no tiene lista de actores disponible");
                }
            }
            @Override
            public void onFailure(Call<CreditResultTv> call, Throwable t) {

            }
        });

    }
}