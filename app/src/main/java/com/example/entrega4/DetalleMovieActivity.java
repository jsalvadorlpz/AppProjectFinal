package com.example.entrega4;

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
import com.example.entrega4.Adapter.RecyclerAdapterActores;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleMovieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company,idioma;
    ImageView poster;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    RecyclerView recyclerView;
    List<CreditResults.Cast> listaActores,lista10Actores;
    RecyclerAdapterActores recyclerAdapterActores;
    Integer idpelicula,id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemovie);
        //RECYCLER Y ADAPTER DE ACTORES
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewActoresMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerAdapterActores = new RecyclerAdapterActores(this,new ArrayList<>(),id2);
        recyclerView.setAdapter(recyclerAdapterActores);
        listaActores = new ArrayList<CreditResults.Cast>();
        lista10Actores = new ArrayList<CreditResults.Cast>();


        titulo = findViewById(R.id.Title);
        genre = findViewById(R.id.Genre);
        date = findViewById(R.id.date);
        sinopsis = findViewById(R.id.sinopsis);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageView);
        company = findViewById(R.id.prod_compa);
        idioma = findViewById(R.id.idioma);

        Integer id =Integer.parseInt(getIntent().getStringExtra("idMovie"));
        idpelicula = id;
        id2 = id;
        Intent actores  = new Intent(this,ActoresMovie.class);
        Bundle extras = new Bundle();
        extras.putInt("idepeli",idpelicula);


        //actores.putExtra(extras);
        getCredits(id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

          Call<MovieDetailsResults> call3 = myInterface.listOfMovieDetails(id, API_KEY);
           call3.enqueue(new Callback<MovieDetailsResults>() {
               @Override
               public void onResponse(Call<MovieDetailsResults> call, Response<MovieDetailsResults> response) {
                   int iteradorListaCompanys = 0;
                   int iteradorListaGeneros = 0;
                   String GenreName = "";
                   String CompnayName = "";
                  MovieDetailsResults results = response.body();
                   List<MovieDetailsResults.Genre> listOfGenre = results.getGenres();
                   int SizeGenreList = listOfGenre.size();
                   List<MovieDetailsResults.ProductionCompany> listOfCompanys = results.getProductionCompanies();
                   int SizeCompanysList = listOfCompanys.size();
                   if(SizeGenreList==1){
                       GenreName = listOfGenre.get(iteradorListaGeneros).getName();
                   } else {
                       while (iteradorListaGeneros < SizeGenreList) {
                           if(iteradorListaGeneros+1==SizeGenreList){
                               GenreName += listOfGenre.get(iteradorListaGeneros).getName();
                           }else{
                               GenreName += listOfGenre.get(iteradorListaGeneros).getName() + ", ";
                           }
                           //CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           iteradorListaGeneros++;
                       }
                   }
                   if(SizeCompanysList==1) {
                       CompnayName = listOfCompanys.get(iteradorListaCompanys).getName();
                   }else{
                       while (iteradorListaCompanys < SizeCompanysList) {
                           if(iteradorListaCompanys+1==SizeCompanysList){
                               CompnayName += listOfCompanys.get(iteradorListaCompanys).getName();
                           }else{
                               CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           }
                           //CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           iteradorListaCompanys++;
                       }
                   }
                   Log.e("",GenreName);
                   Log.e("",CompnayName);
                   genre.setText(GenreName);
                   company.setText(CompnayName);
                   titulo.setText(results.getTitle()+ "("+ results.getOriginalTitle()+")");
                   sinopsis.setText(results.getOverview());
                   date.setText(results.getReleaseDate());
                   idioma.setText(results.getOriginalLanguage());
                   //image = (String) results.getPosterPath();


               }
               @Override
               public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

               }
          });

        String image_peli = getIntent().getStringExtra("imageMovie");
        Glide.with(this).load(url_imagenes+image_peli).into(poster);


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

        Call<CreditResults> call = myInterface.listOfCredit(id,API_KEY);
        call.enqueue(new Callback<CreditResults>() {
            @Override
            public void onResponse(Call<CreditResults> call, Response<CreditResults> response) {
                CreditResults results = response.body();
                List<CreditResults.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                int iterador = 0;
                while(iterador < 10) {
                    lista10Actores.add(listaActores.get(iterador));
                    iterador++;
                }
                recyclerAdapterActores.updateDataActores(lista10Actores,id2);
            }
            @Override
            public void onFailure(Call<CreditResults> call, Throwable t) {

            }
        });

    }


}