package com.example.entrega4.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter2;
import com.example.entrega4.DetalleSerieActivity;
import com.example.entrega4.GenreTvResults;
import com.example.entrega4.R;
import com.example.entrega4.SeriesResults;
import com.example.entrega4.TheMovieDatasetApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeriesFragment extends Fragment implements RecyclerAdapter2.botonCargarMas2{
    RecyclerAdapter2 recyclerAdapter2;
    RecyclerView recyclerView2;

    Button anterior,siguiente;
    Activity actividad;



    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    public int id;
    public int cantidad2  = 0;
    public List<GenreTvResults.Genre> ResultadoGenerosTv;
    public List<String> generos,listaGeneros;
    public String GenreName;
    public List<SeriesResults.Result> series,listSeries,series2;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ResultadoGenerosTv = new ArrayList<GenreTvResults.Genre>();
        GenreName = "";
        listaGeneros = new ArrayList<String>();
        series2 = new ArrayList<SeriesResults.Result>();
        generos = new ArrayList<String>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.series_fragment,container, false);
        //initViews
        recyclerView2 = view.findViewById(R.id.recyclerview_series2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerAdapter2= new RecyclerAdapter2(getContext(),new ArrayList<>(),new ArrayList<>(),this);
        recyclerView2.setAdapter(recyclerAdapter2);
        series = new ArrayList<SeriesResults.Result>();
        listSeries = new ArrayList<SeriesResults.Result>();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<SeriesResults> call = myInterface.listOfSeries(CATEGORY,API_KEY,LANGUAGE,PAGE);
        call.enqueue(new Callback<SeriesResults>() {
            @Override
            public void onResponse(Call<SeriesResults> call, Response<SeriesResults> response) {
                Log.e("", "entra en el onResponse");
                SeriesResults results = response.body();
                List<SeriesResults.Result> listOfSeries = results.getResults();
                series = listOfSeries;

                getGenres();

            }
            @Override
            public void onFailure(Call<SeriesResults> call, Throwable t) {

            }
        });

        return view;
    }

    public void getGenres() {
        Log.e("","Entramos en la llamada a la lista de todos los generos");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<GenreTvResults> callGenresTV = myInterface.listOfGenresTv(API_KEY);
        callGenresTV.enqueue(new Callback<GenreTvResults>() {

            @Override
            public void onResponse(Call<GenreTvResults> call, Response<GenreTvResults> response) {
                GenreTvResults results = response.body();
                List<GenreTvResults.Genre> listOfGenreTv = results.getGenres();
                ResultadoGenerosTv = listOfGenreTv;
                Log.e("","Obtenemos la lista con todos los generos con tamaño: "+String.valueOf(ResultadoGenerosTv.size()));
                getGeneros();
            }

            @Override
            public void onFailure(Call<GenreTvResults> call, Throwable t) {

            }
        });

    }

    private void getGeneros(){
        Log.e("","Entramos en el getGeneros");
        int iteradorPeliculas = 0;
        while(iteradorPeliculas< series.size()) {
            List<Integer> ListaGenerosPelicula = series.get(iteradorPeliculas).getGenreIds();
            if(ListaGenerosPelicula.size()==1){
                int generoId = ListaGenerosPelicula.get(0);
                int iteradorListaGeneros = 0;
                while(iteradorListaGeneros< ResultadoGenerosTv.size()){
                    if(ResultadoGenerosTv.get(iteradorListaGeneros).getId()==generoId){
                        GenreName += ResultadoGenerosTv.get(iteradorListaGeneros).getName();
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
                    while(iteradorListaGeneros< ResultadoGenerosTv.size()){
                        if(ResultadoGenerosTv.get(iteradorListaGeneros).getId()==generoId){
                            if(ListaGenerosPelicula.size()-1==iteradorIds){
                                GenreName += ResultadoGenerosTv.get(iteradorListaGeneros).getName();
                            }else{
                                GenreName += ResultadoGenerosTv.get(iteradorListaGeneros).getName() + ", " ;
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
        recyclerView2.setVisibility(View.VISIBLE);
        Log.e("","recyclerview Visible");

    }

    private void initValues(){

        listSeries = getItemsSeries();
        listaGeneros = returnGeneros();
        recyclerAdapter2.updateData(listSeries,listaGeneros);
        recyclerAdapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = listSeries.get(recyclerView2.getChildAdapterPosition(view)).getId();
                Intent detalle = new Intent(getContext(), DetalleSerieActivity.class);
                detalle.putExtra("idSerie",String.valueOf(id));
                detalle.putExtra("imageSerie",listSeries.get(recyclerView2.getChildAdapterPosition(view)).getPosterPath());
                startActivity(detalle);

            }
        });


    }
    private List<String> returnGeneros(){return generos;}
    private List<SeriesResults.Result> getItemsSeries(){
        return series;
    }

    @Override
    public void funcionCargarMas2(int page) {
        Log.e("","interfaz funciona, para la pagina " + String.valueOf(page));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<SeriesResults> call = myInterface.listOfSeries(CATEGORY,API_KEY,LANGUAGE,page);
        call.enqueue(new Callback<SeriesResults>() {
            @Override
            public void onResponse(Call<SeriesResults> call, Response<SeriesResults> response) {
                Log.e("", "entra en el onResponse");
                SeriesResults results = response.body();
                List<SeriesResults.Result> listOfSeries2 = results.getResults();
                series2 = listOfSeries2;
                listSeries.addAll(series2);
                recyclerAdapter2.updateSeries(series2,listaGeneros);
            }
            @Override
            public void onFailure(Call<SeriesResults> call, Throwable t) {
                Log.e("","entra fallo");
            }

        });
    }




}
