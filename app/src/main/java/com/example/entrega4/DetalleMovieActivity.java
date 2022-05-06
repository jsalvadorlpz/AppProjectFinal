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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleMovieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company,idioma;
    ImageView poster;
    Activity activity;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    LayoutInflater inflater;
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public List<String> companys,generos3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemovie);



        titulo = findViewById(R.id.Title);
        genre = findViewById(R.id.Genre);
        date = findViewById(R.id.date);
        sinopsis = findViewById(R.id.sinopsis);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageView);
        company = findViewById(R.id.prod_compa);
        idioma = findViewById(R.id.idioma);

        Integer id =Integer.parseInt(getIntent().getStringExtra("id"));
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

               }
               @Override
               public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

               }
          });


        String titulo_peli = getIntent().getStringExtra("titulo");
        String genre_peli = getIntent().getStringExtra("genre");
        String sinopsis_peli = getIntent().getStringExtra("sinopsis");
        String date_peli = getIntent().getStringExtra("date");
        String image_peli = getIntent().getStringExtra("image");
        String company_peli = getIntent().getStringExtra("company");
        //titulo.setText(titulo_peli);
        //genre.setText(GenreName);
        //sinopsis.setText(sinopsis_peli);
        date.setText(date_peli);
        //company.setText(CompanyName);
        //View view = inflater.inflate(R.layout.activity_detallemovie,null,false);
        Glide.with(this).load(url_imagenes+image_peli).into(poster);


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("","se destruye el detalle movie");
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

}