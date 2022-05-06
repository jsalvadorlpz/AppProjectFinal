package com.example.entrega4.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter;
import com.example.entrega4.DetalleMovieActivity;
import com.example.entrega4.MovieResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.iComunicaFragments;
import com.example.entrega4.model.ItemList;

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
    List<ItemList> itemList;
    Button anterior,siguiente;
    Activity actividad;
    iComunicaFragments interfaceComunicateFragmets;


    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    public int id;
    public String titulo,release,poster_path,sinopsis,language,original_title,nombregeneros,gen,company;
    public List<String> titulos, releases,generos3,sinopsisList,languages, poster_paths,original_titles,generos_final,companys,StringMovieIds;
    public List<Integer> generos,generosid,MovieIds;
    public List<List<Integer>> generos2;
    public double popularity;
    public List<Object> generos4;
    public List<Double> popolularitys;
    public int cantidad2,j,i,z  = 0;
    public boolean encontrado;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.main_fragment,container, false);
        //initViews
        recyclerView = view.findViewById(R.id.recyclerview_movies);
        //anterior = view.findViewById(R.id.botonAnterior);
        //siguiente = view.findViewById(R.id.botonSnterior);
        itemList = new ArrayList<>();

        titulos = new ArrayList<String>();
        releases = new ArrayList<String>();
        generos = new ArrayList<Integer>();
        generos2 = new ArrayList<List<Integer>>();
        generos3 = new ArrayList<String>();
        poster_paths = new ArrayList<String>();
        popolularitys = new ArrayList<Double>();
        MovieIds = new ArrayList<Integer>();
        StringMovieIds = new ArrayList<String>();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();

                int cantidad = listOfMovies.size();
                cantidad2 = cantidad;

                while (i < cantidad) {
                    MovieResults.ResultsBean Movie = listOfMovies.get(i);
                    titulo = (String) Movie.getTitle();
                    release = (String) Movie.getRelease_date();
                    generos = (List<Integer>) Movie.getGenre_ids();
                    poster_path= (String) Movie.getPoster_path();
                    popularity = (Double) Movie.getPopularity();
                    id = (Integer)Movie.getId();

                    titulos.add(titulo);
                    releases.add(release);
                    generos2.add(generos);
                    poster_paths.add(poster_path);
                    popolularitys.add(popularity);
                    MovieIds.add(id);
                    StringMovieIds.add(String.valueOf(id));


                    i++;
                }
                i = 0;
                while (i < cantidad2) {
                    generos3.add(String.valueOf(generos2.get(i)));
                    i++;
                }
                initValues();

            }



            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }

        });

        return view;
    }




    private void initValues(){
        itemList = getItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerAdapter = new RecyclerAdapter(getContext(),itemList);
        recyclerView.setAdapter(recyclerAdapter);


        recyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo2 = itemList.get(recyclerView.getChildAdapterPosition(view)).getTitulo();
                Toast.makeText(getContext(),"selecciono"+titulo2,Toast.LENGTH_SHORT).show();
                Log.e("","selecciono"+titulo2);
                Intent detalle = new Intent(getContext(), DetalleMovieActivity.class);
                detalle.putExtra("titulo",titulo2);
                detalle.putExtra("genre",itemList.get(recyclerView.getChildAdapterPosition(view)).getGenrer());
                detalle.putExtra("date",itemList.get(recyclerView.getChildAdapterPosition(view)).getRelease());
                // detalle.putExtra("sinopsis",itemList.get(recyclerView.getChildAdapterPosition(view)).getSinopsis());
                //detalle.putExtra("language",itemList.get(recyclerView.getChildAdapterPosition(view)).getLanguage());
                detalle.putExtra("image",itemList.get(recyclerView.getChildAdapterPosition(view)).getPosterPath());
                detalle.putExtra("id",itemList.get(recyclerView.getChildAdapterPosition(view)).getId());
                startActivity(detalle);

            }
        });


    }
    private List<ItemList> getItems(){
        List<ItemList> itemLists = new ArrayList<>();
        int iterador = 0;
        while(iterador<cantidad2) {

            itemLists.add(new ItemList(titulos.get(iterador), releases.get(iterador),
                    poster_paths.get(iterador), generos3.get(iterador),
                    popolularitys.get(iterador),StringMovieIds.get(iterador)));
            iterador++;
        }


        return itemLists;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
            interfaceComunicateFragmets = (iComunicaFragments) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }





}
