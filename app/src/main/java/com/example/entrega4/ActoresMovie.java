package com.example.entrega4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapterAllActores;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActoresMovie extends AppCompatActivity implements RecyclerAdapterAllActores.verTodoslosActores {

    RecyclerAdapterAllActores recyclerAdapterAllActores;
    RecyclerView recyclerViewActores;
    List<MovieResults.ResultsBean> listapeliculas;
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    List<CreditResults.Cast> listaActores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actores_movie);
        recyclerViewActores = findViewById(R.id.recyclerviewActoresMovies);
        recyclerViewActores.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapterAllActores = new RecyclerAdapterAllActores(this,new ArrayList<>());
        recyclerViewActores.setAdapter(recyclerAdapterAllActores);
        listaActores = new ArrayList<CreditResults.Cast>();
        Bundle extras = new Bundle();
        Integer id =getIntent().getExtras().getInt("idpelicula");
        getCredits(id);

    }


    public void getCredits(Integer id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

        Call<CreditResults> call = myInterface.listOfCredit(id,API_KEY);
        call.enqueue(new Callback<CreditResults>() {
            @Override
            public void onResponse(Call<CreditResults> call, Response<CreditResults> response) {
                CreditResults results = response.body();
                List<CreditResults.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                recyclerAdapterAllActores.updateDataActores(listaActores);
            }
            @Override
            public void onFailure(Call<CreditResults> call, Throwable t) {

            }
        });

    }

    @Override
    public void funcionVerTodos(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

        Call<CreditResults> call = myInterface.listOfCredit(id,API_KEY);
        call.enqueue(new Callback<CreditResults>() {
            @Override
            public void onResponse(Call<CreditResults> call, Response<CreditResults> response) {
                CreditResults results = response.body();
                List<CreditResults.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                recyclerAdapterAllActores.updateDataActores(listaActores);
            }
            @Override
            public void onFailure(Call<CreditResults> call, Throwable t) {

            }
        });
    }
}