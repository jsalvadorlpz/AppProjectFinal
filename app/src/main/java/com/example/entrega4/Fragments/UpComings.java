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

import com.example.entrega4.ActoresMovie;
import com.example.entrega4.Adapter.RecyclerAdapter;
import com.example.entrega4.Adapter.RecyclerAdapterUpcomings;
import com.example.entrega4.DetalleMovieActivity;
import com.example.entrega4.GenreResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.UpComingsDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class UpComings extends Fragment implements RecyclerAdapter.botonCargarMas{

    RecyclerAdapterUpcomings recyclerAdapterUpComings;
    RecyclerView recyclerView;
    List<UpComingsDetails.Result> listaupcomings;

    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="upcoming";
    public int id,cantidadMovies;
    public String titulo,GenreName;
    public List<UpComingsDetails.Result> upcomings;
    public List<String> generos,listaGeneros;
    public List<GenreResults.Genre> ResultadoGeneros;
    public List<UpComingsDetails.Result> upcomings2;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GenreName = "";
        generos = new ArrayList<String>();
        upcomings = new ArrayList<UpComingsDetails.Result>();
        listaGeneros = new ArrayList<String>();
        ResultadoGeneros = new ArrayList<GenreResults.Genre>();
        upcomings2 = new ArrayList<UpComingsDetails.Result>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.fragment_up_comings,container, false);
        //initViews
        recyclerView = view.findViewById(R.id.recyclerview_upcomings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapterUpComings = new RecyclerAdapterUpcomings(getContext(),new ArrayList<>(),new ArrayList<>(),this::funcionCargarMas);
        recyclerView.setAdapter(recyclerAdapterUpComings);
        recyclerView.setVisibility(View.INVISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<UpComingsDetails> callMovies = myInterface.listOfUpcomings(API_KEY,PAGE);
        Log.e("","crea la call");
        callMovies.enqueue(new Callback<UpComingsDetails>() {
            @Override
            public void onResponse(Call<UpComingsDetails> call, Response<UpComingsDetails> response) {
                Log.e("","entra en el OnResponse");
                UpComingsDetails results = response.body();
                List<UpComingsDetails.Result> listOfMovies = results.getResults();
                upcomings = listOfMovies;
                cantidadMovies = listOfMovies.size();
                Log.e("","Recibimos la lista de peliculas con tamaño: " + String.valueOf(upcomings.size()));
                getGenres();


            } @Override
            public void onFailure(Call<UpComingsDetails> call, Throwable t) {
                Log.e("","entra fallo");
            }

        });


        return view;
    }
    private void getGeneros(){
        Log.e("","Entramos en el getGeneros");
        int iteradorPeliculas = 0;
        while(iteradorPeliculas< upcomings.size()) {
            List<Integer> ListaGenerosPelicula = upcomings.get(iteradorPeliculas).getGenreIds();
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
        listaupcomings = getItems();
        listaGeneros = returnGeneros();
        recyclerAdapterUpComings.updateData(listaupcomings,listaGeneros);

        recyclerAdapterUpComings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detalle = new Intent(getContext(), DetalleMovieActivity.class);
                Intent actores = new Intent(getContext(), ActoresMovie.class);
                actores.putExtra("MovieTitle",String.valueOf(listaupcomings.get(recyclerView.getChildAdapterPosition(view)).getTitle()));
                detalle.putExtra("idMovie",String.valueOf(listaupcomings.get(recyclerView.getChildAdapterPosition(view)).getId()));
                detalle.putExtra("imageMovie",listaupcomings.get(recyclerView.getChildAdapterPosition(view)).getPosterPath());
                detalle.putExtra("tituloMovie",listaupcomings.get(recyclerView.getChildAdapterPosition(view)).getTitle());
                //detalle.putExtra("tituloMovie",listapeliculas.get(recyclerView.getChildAdapterPosition(view)).getTitle());
                startActivity(detalle);

            }

        });


    }
    private List<String> returnGeneros(){return generos;}
    private List<UpComingsDetails.Result> getItems(){
        return upcomings;
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
    public void funcionCargarMas(int page) {
        Log.e("","interfaz funciona, para la pagina " + String.valueOf(page));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<UpComingsDetails> callMovies = myInterface.listOfUpcomings(API_KEY,page);
        Log.e("","crea la call");
        callMovies.enqueue(new Callback<UpComingsDetails>() {
            @Override
            public void onResponse(Call<UpComingsDetails> call, Response<UpComingsDetails> response) {
                Log.e("","hacemos la llamada del cargar mas");
                UpComingsDetails results = response.body();
                List<UpComingsDetails.Result> listOfMovies = results.getResults();
                upcomings2 = listOfMovies;
                listaupcomings.addAll(upcomings2);
                recyclerAdapterUpComings.updateUpcomings(upcomings2,listaGeneros);
            }
            @Override
            public void onFailure(Call<UpComingsDetails> call, Throwable t) {
                Log.e("","entra fallo");
            }

        });
    }
}