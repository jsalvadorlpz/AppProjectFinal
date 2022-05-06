package com.example.entrega4;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDatos extends AppCompatActivity {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";

    public List<Integer> MovieIds;
    public List<String> StringMovieIds, companys;
    public Integer id;
    public String title, originaTitle;
    ListView listview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_datos);
        listview = (ListView)findViewById(R.id.listview);
        MovieIds = new ArrayList<Integer>();
        StringMovieIds = new ArrayList<String>();
        companys = new ArrayList<String>();
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface2 = retrofit2.create(TheMovieDatasetApi.class);


        Call<MovieResults> call2 = myInterface2.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call2.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                Log.e("","entra en el onResponse");
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();
                int cantidad = listOfMovies.size();
                int i =0;
                while (i < cantidad) {
                    id= listOfMovies.get(i).getId();
                    Log.e("",String.valueOf(id));
                    MovieIds.add(id);
                    StringMovieIds.add(String.valueOf(id));
                    i++;
                }

            }
            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }

        });

        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.activity_get_datos,StringMovieIds);
        //listview.setAdapter(adapter);
        int iteradorMovie = 0;
        Log.e("",String.valueOf(MovieIds.size()));
       while (iteradorMovie<MovieIds.size()) {
            Integer MOVIE_ID = MovieIds.get(iteradorMovie);
            Call<MovieDetailsResults> call3 = myInterface2.listOfMovieDetails(919689, API_KEY);
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
                            GenreName += listOfGenre.get(iteradorListaGeneros).getName()+ ",";
                            iteradorListaGeneros++;
                        }
                    }
                    if(SizeCompanysList==1) {
                        CompnayName = listOfCompanys.get(iteradorListaCompanys).getName();
                    }else{
                        while (iteradorListaCompanys < SizeCompanysList) {
                            CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ",";
                            iteradorListaCompanys++;
                        }
                    }
                    Log.e("",GenreName);
                    Log.e("",CompnayName);

                }
                @Override
                public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

                }

            });
            iteradorMovie++;
        }

        //    Call<GenreResults> call2 = myInterface.listOfGenre(API_KEY);
        //    call2.enqueue(new Callback<GenreResults>() {
        //    @Override
        //    public void onResponse(Call<GenreResults> call, Response<GenreResults> response) {
        //        GenreResults results = response.body();
        //        List<GenreResults.Genre> listOfGenre = results.getGenres();
        //        int cantidad = listOfGenre.size();
        //        cantidad2 = cantidad;

        //        while(i<cantidad){
        //            generos4.add(listOfGenre.get(i));
        //      }
        //        i=0;
        //        j=0;

        //    while(i<generos2.size()){
        //         generosid = generos2.get(i);
        //    while(j<generosid.size()){
        //     int id = generosid.get(j);
        //     encontrado = false;
        //    z=0;
        //    while(encontrado != true){
        //         int id2 = listOfGenre.get(z).getId();
        //         if(id == id2){
        //             nombregeneros = listOfGenre.get(z).getName();
        ///              encontrado = true;
        //            }
        //              z++;
        //            }
        //               gen += nombregeneros + " ";
        //                j++;

        //  }
        //    generos_final.add(gen);
        //      i++;
        //    }
        //      initValues();
        //    }

        //    @Override
        //     public void onFailure(Call<GenreResults> call, Throwable t) {

        //     }
        // });
    }
}
