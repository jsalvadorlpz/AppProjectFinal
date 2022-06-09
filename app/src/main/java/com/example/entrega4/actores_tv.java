package com.example.entrega4;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapterAllActoresTv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class actores_tv extends AppCompatActivity implements RecyclerAdapterAllActoresTv.verTodoslosActores {
    RecyclerAdapterAllActoresTv recyclerAdapterAllActoresTv;
    RecyclerView recyclerViewActores;
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    List<CreditResultTv.Cast> listaActores;

    TextView volverPrincipal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actores_tv);
        recyclerViewActores = findViewById(R.id.recyclerviewActoresTv);
        recyclerViewActores.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapterAllActoresTv = new RecyclerAdapterAllActoresTv(this,new ArrayList<>());
        recyclerViewActores.setAdapter(recyclerAdapterAllActoresTv);
        listaActores = new ArrayList<CreditResultTv.Cast>();
        Bundle extras = new Bundle();
        Integer id =getIntent().getExtras().getInt("idpelicula");
        getCredits(id);

        volverPrincipal = findViewById(R.id.vovlerPrincipalTv);
        volverPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                recyclerAdapterAllActoresTv.updateDataAllActoresTv(listaActores);
            }
            @Override
            public void onFailure(Call<CreditResultTv> call, Throwable t) {

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

        Call<CreditResultTv> call = myInterface.listOfCreditTv(id,API_KEY);
        call.enqueue(new Callback<CreditResultTv>() {
            @Override
            public void onResponse(Call<CreditResultTv> call, Response<CreditResultTv> response) {
                CreditResultTv results = response.body();
                List<CreditResultTv.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                recyclerAdapterAllActoresTv.updateDataAllActoresTv(listaActores);
            }
            @Override
            public void onFailure(Call<CreditResultTv> call, Throwable t) {

            }
        });
    }


}