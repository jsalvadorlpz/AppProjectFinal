package com.example.entrega4.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter3;
import com.example.entrega4.DetalleTrendingActivity;
import com.example.entrega4.GenreResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.TrendingResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrendingFragment extends Fragment implements RecyclerAdapter3.botonCargarMasTrending {

    RecyclerAdapter3 recyclerAdapter3;
    RecyclerView recyclerView3;

    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static int PAGE = 1;
    public static String MEDIA_TYPE = "movie";
    public static String TIME_WINDOW = "week";
    public List<String> listaGeneros, generos;
    public int cantidadTrending;
    public String GenreName, titulo;
    public List<TrendingResults.Result> trendings, listatrendings, trendings2;
    public List<GenreResults.Genre> ResultadoGeneros;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenreName = "";
        listaGeneros = new ArrayList<String>();
        ResultadoGeneros = new ArrayList<GenreResults.Genre>();
        trendings = new ArrayList<TrendingResults.Result>();
        generos = new ArrayList<String>();
        listatrendings = new ArrayList<TrendingResults.Result>();
        trendings2 = new ArrayList<TrendingResults.Result>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);

        recyclerView3 = view.findViewById(R.id.recyclerview_trending);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter3 = new RecyclerAdapter3(getContext(), new ArrayList<>(), new ArrayList<>(), this);
        recyclerView3.setAdapter(recyclerAdapter3);
        recyclerView3.setVisibility(View.INVISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<TrendingResults> call = myInterface.listOfTrending(MEDIA_TYPE, TIME_WINDOW, API_KEY, PAGE);
        call.enqueue(new Callback<TrendingResults>() {
            @Override
            public void onResponse(Call<TrendingResults> call, Response<TrendingResults> response) {
                TrendingResults results = response.body();
                List<TrendingResults.Result> listOfTrending = results.getResults();
                trendings = listOfTrending;
                cantidadTrending = listOfTrending.size();
                getGenres();

            }

            @Override
            public void onFailure(Call<TrendingResults> call, Throwable t) {
                Log.e("", "entra en el fallo");
            }
        });


        return view;
    }

    private void getGeneros() {
        Log.e("", "Entramos en el getGeneros");
        int iteradorPeliculas = 0;
        while (iteradorPeliculas < trendings.size()) {
            List<Integer> ListaGenerosPelicula = trendings.get(iteradorPeliculas).getGenreIds();
            if (ListaGenerosPelicula.size() == 1) {
                int generoId = ListaGenerosPelicula.get(0);
                int iteradorListaGeneros = 0;
                while (iteradorListaGeneros < ResultadoGeneros.size()) {
                    if (ResultadoGeneros.get(iteradorListaGeneros).getId() == generoId) {
                        GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName();
                    }
                    iteradorListaGeneros++;
                }
                generos.add(GenreName);
                Log.e("", GenreName);
                GenreName = "";
            } else {
                int iteradorIds = 0;
                while (ListaGenerosPelicula.size() > iteradorIds) {
                    int generoId = ListaGenerosPelicula.get(iteradorIds);
                    int iteradorListaGeneros = 0;
                    while (iteradorListaGeneros < ResultadoGeneros.size()) {
                        if (ResultadoGeneros.get(iteradorListaGeneros).getId() == generoId) {
                            if (ListaGenerosPelicula.size() - 1 == iteradorIds) {
                                GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName();
                            } else {
                                GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName() + ", ";
                            }
                        }
                        iteradorListaGeneros++;
                    }
                    iteradorIds++;
                }
                generos.add(GenreName);
                Log.e("", GenreName);
                GenreName = "";

            }
            GenreName = "";
            iteradorPeliculas++;
        }
        initValues();
        recyclerView3.setVisibility(View.VISIBLE);
        Log.e("", "recyclerview Visible");

    }


    private void initValues() {
        listatrendings = getItems();
        listaGeneros = returnGeneros();
        recyclerAdapter3.updateDataTrending(listatrendings, listaGeneros);

        recyclerAdapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detalle = new Intent(getContext(), DetalleTrendingActivity.class);
                detalle.putExtra("imageTrending", listatrendings.get(recyclerView3.getChildAdapterPosition(view)).getPosterPath());
                detalle.putExtra("idTrending", String.valueOf(listatrendings.get(recyclerView3.getChildAdapterPosition(view)).getId()));
                startActivity(detalle);

            }
        });

    }

    public void getGenres() {
        Log.e("", "Entramos en la llamada a la lista de todos los generos");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<GenreResults> callGenres = myInterface.listOfGenres(API_KEY);
        callGenres.enqueue(new Callback<GenreResults>() {

            @Override
            public void onResponse(Call<GenreResults> call, Response<GenreResults> response) {
                GenreResults results = response.body();
                List<GenreResults.Genre> listOfGenre = results.getGenres();
                ResultadoGeneros = listOfGenre;
                Log.e("", "Obtenemos la lista con todos los generos con tamaño: " + String.valueOf(ResultadoGeneros.size()));
                getGeneros();
            }

            @Override
            public void onFailure(Call<GenreResults> call, Throwable t) {

            }
        });

    }

    private List<String> returnGeneros() {
        return generos;
    }

    private List<TrendingResults.Result> getItems() {
        return trendings;
    }

    @Override
    public void funcionCargarMasTrending(int page) {
        Log.e("", "interfaz funciona, para la pagina " + String.valueOf(page));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<TrendingResults> callTrending = myInterface.listOfTrending(MEDIA_TYPE, TIME_WINDOW, API_KEY, page);
        Log.e("", "crea la call");
        callTrending.enqueue(new Callback<TrendingResults>() {
            @Override
            public void onResponse(Call<TrendingResults> call, Response<TrendingResults> response) {
                Log.e("", "hacemos la llamada del cargar mas");
                TrendingResults results = response.body();
                List<TrendingResults.Result> listOfTrending = results.getResults();
                trendings2 = listOfTrending;
                listatrendings.addAll(trendings2);
                recyclerAdapter3.updateTrending(trendings2, listaGeneros);
            }

            @Override
            public void onFailure(Call<TrendingResults> call, Throwable t) {
                Log.e("", "entra fallo");
            }

        });
    }
}