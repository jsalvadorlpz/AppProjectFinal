package com.example.entrega4.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter;
import com.example.entrega4.DetalleMovieActivity;
import com.example.entrega4.GenreResults;
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

public class MainFragment extends Fragment implements RecyclerAdapter.botonCargarMas{

    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    List<MovieResults.ResultsBean> listapeliculas;
    ProgressBar progessBar;

    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    public int id,cantidadMovies;
    public String titulo,GenreName;
    public List<MovieResults.ResultsBean> peliculas;
    public List<String> generos,listaGeneros;
    public List<GenreResults.Genre> ResultadoGeneros;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GenreName = "";
        generos = new ArrayList<String>();
        peliculas = new ArrayList<MovieResults.ResultsBean>();
        listaGeneros = new ArrayList<String>();
        ResultadoGeneros = new ArrayList<GenreResults.Genre>();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.main_fragment,container, false);
        //initViews
        recyclerView = view.findViewById(R.id.recyclerview_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext(),new ArrayList<>(),new ArrayList<>(),this);
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

                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();
                peliculas = listOfMovies;
                cantidadMovies = listOfMovies.size();
                Log.e("","Recibimos la lista de peliculas con tamaño: " + String.valueOf(peliculas.size()));
                getGenres();


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
        Log.e("","Entramos en el getGeneros");
        int iteradorPeliculas = 0;
        while(iteradorPeliculas< peliculas.size()) {
            List<Integer> ListaGenerosPelicula = peliculas.get(iteradorPeliculas).getGenre_ids();
            if(ListaGenerosPelicula.size()==1){
                int generoId = ListaGenerosPelicula.get(0);
                int iteradorListaGeneros = 0;
                while(iteradorListaGeneros< ResultadoGeneros.size()){
                    if(ResultadoGeneros.get(iteradorListaGeneros).getId()==generoId){
                        GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName();
                    }
                    iteradorListaGeneros++;
                }
                generos.add(GenreName);
                Log.e("",GenreName);
                GenreName = "";
            }
            else{
                int iteradorIds = 0;
                while(ListaGenerosPelicula.size()>iteradorIds){
                    int generoId = ListaGenerosPelicula.get(iteradorIds);
                    int iteradorListaGeneros = 0;
                    while(iteradorListaGeneros< ResultadoGeneros.size()){
                        if(ResultadoGeneros.get(iteradorListaGeneros).getId()==generoId){
                            if(ListaGenerosPelicula.size()-1==iteradorIds){
                                GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName();
                            }else{
                                GenreName += ResultadoGeneros.get(iteradorListaGeneros).getName() + ", " ;
                            }
                        }
                        iteradorListaGeneros++;
                    }
                    iteradorIds++;
                }
                generos.add(GenreName);
                Log.e("",GenreName);
                GenreName = "";

            }
            GenreName = "";
            iteradorPeliculas++;
        }
        initValues();
        recyclerView.setVisibility(View.VISIBLE);
        Log.e("","recyclerview Visible");

    }


    private void initValues(){
        listapeliculas = getItems();
        listaGeneros = returnGeneros();
        recyclerAdapter.updateData(listapeliculas,listaGeneros);

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
    private List<String> returnGeneros(){return generos;}
    private List<MovieResults.ResultsBean> getItems(){
        return peliculas;
    }

    public void getGenres() {
        Log.e("","Entramos en la llamada a la lista de todos los generos");
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
                Log.e("","Obtenemos la lista con todos los generos con tamaño: "+String.valueOf(ResultadoGeneros.size()));
                getGeneros();
            }

            @Override
            public void onFailure(Call<GenreResults> call, Throwable t) {

            }
        });

    }




    @Override
    public void funcionCargarMas() {
        Log.e("","interfaz funciona");
    }
}
