package com.example.entrega4.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter;
import com.example.entrega4.DetalleMovieActivity;
import com.example.entrega4.MovieDetailsResults;
import com.example.entrega4.MovieResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    //List<ItemList> itemList;
    List<MovieResults.ResultsBean> listapeliculas;

    Button anterior,siguiente;
    Activity actividad;
    ProgressBar progessBar;



    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    public int id,cantidadMovies;
    public String titulo,poster_path,GenreName;
    public List<MovieResults.ResultsBean> peliculas;
    public List<String> generos;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        generos = new ArrayList<String>();
        peliculas = new ArrayList<MovieResults.ResultsBean>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.main_fragment,container, false);
        //initViews
        recyclerView = view.findViewById(R.id.recyclerview_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setVisibility(View.INVISIBLE);
        progessBar = view.findViewById(R.id.progessBar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<MovieResults> callMovies = myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);
        Log.e("","crea la call");
        callMovies.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                Log.e("","Entra en el OnResponse");
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();
                peliculas = listOfMovies;
                cantidadMovies = listOfMovies.size();
                getGeneros();
                //initValues();

            }
            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e("","entra fallo");
            }

        });
        progessBar.setVisibility(View.GONE);
       // recyclerView.setVisibility(View.VISIBLE);
        return view;
    }
    private void getGeneros(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        int iterador = 0;
        while(iterador< peliculas.size()) {
            int id = peliculas.get(iterador).getId();
            Call<MovieDetailsResults> call3 = myInterface.listOfMovieDetails(id, API_KEY);
            call3.enqueue(new Callback<MovieDetailsResults>() {
                @Override
                public void onResponse(Call<MovieDetailsResults> call, Response<MovieDetailsResults> response) {
                    int iteradorListaGeneros = 0;

                    MovieDetailsResults results = response.body();
                    List<MovieDetailsResults.Genre> listOfGenre = results.getGenres();
                    int SizeGenreList = listOfGenre.size();

                    if (SizeGenreList == 1) {
                        GenreName = listOfGenre.get(iteradorListaGeneros).getName();
                        generos.add(GenreName);
                    } else {
                        while (iteradorListaGeneros < SizeGenreList) {
                            if (iteradorListaGeneros + 1 == SizeGenreList) {
                                GenreName += listOfGenre.get(iteradorListaGeneros).getName();
                            } else {
                                GenreName += listOfGenre.get(iteradorListaGeneros).getName() + ", ";
                                if (SizeGenreList > 6 && SizeGenreList == 6) {
                                    iteradorListaGeneros = SizeGenreList;
                                }
                            }
                            generos.add(GenreName);
                            Log.e("",GenreName);
                            iteradorListaGeneros++;
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailsResults> call, Throwable t) {
                }
            });
            iterador++;
        }
        initValues();
        recyclerView.setVisibility(View.VISIBLE);
        Log.e("","recyclerview Visible");
    }
    private void initValues(){
        listapeliculas = getItems();
        recyclerAdapter.updateData(listapeliculas);

        recyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detalle = new Intent(getContext(), DetalleMovieActivity.class);
                detalle.putExtra("idMovie",String.valueOf(listapeliculas.get(recyclerView.getChildAdapterPosition(view)).getId()));
                detalle.putExtra("imageMovie",listapeliculas.get(recyclerView.getChildAdapterPosition(view)).getPoster_path());
                startActivity(detalle);

            }

        });


    }

    private List<MovieResults.ResultsBean> getItems(){
        return peliculas;
    }






}
