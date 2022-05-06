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

import com.example.entrega4.Adapter.RecyclerAdapter3;
import com.example.entrega4.DetalleMovieActivity;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.TrendingResults;
import com.example.entrega4.iComunicaFragments;
import com.example.entrega4.model.ItemListTrending;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrendingFragment extends Fragment {

    RecyclerAdapter3 recyclerAdapter3;
    RecyclerView recyclerView3;
    List<ItemListTrending> itemList;
    Button anterior,siguiente;
    Activity actividad;
    iComunicaFragments interfaceComunicateFragmets;
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static int PAGE = 1;
    public static String MEDIA_TYPE = "all";
    public static String TIME_WINDOW = "week";
    public String titulo,release,poster_path,sinopsis,language,original_title;
    public List<String> titulos, releases,generos3,sinopsisList,languages, poster_paths,original_titles;
    public List<Integer> generos;
    public List<List<Integer>> generos2;
    public double popularity;
    public List<Object> generos4;
    public List<Double> popolularitys;
    public int cantidad2,j,i  = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment,container, false);

        recyclerView3 = view.findViewById(R.id.recyclerview_trending);
        //anterior = view.findViewById(R.id.botonAnterior);
        //siguiente = view.findViewById(R.id.botonSnterior);
        itemList = new ArrayList<>();

        titulos = new ArrayList<String>();
        original_titles = new ArrayList<String>();
        releases = new ArrayList<String>();
        generos = new ArrayList<Integer>();
        generos2 = new ArrayList<List<Integer>>();
        generos3 = new ArrayList<String>();
        sinopsisList = new ArrayList<>();
        languages = new ArrayList<>();
        poster_paths = new ArrayList<String>();
        popolularitys = new ArrayList<Double>();
        generos4 = new ArrayList<Object>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<TrendingResults> call = myInterface.listOfTrending(MEDIA_TYPE,TIME_WINDOW,API_KEY,PAGE);
        call.enqueue(new Callback<TrendingResults>() {
            @Override
            public void onResponse(Call<TrendingResults> call, Response<TrendingResults> response) {
                TrendingResults results = response.body();
                List<TrendingResults.Result> listOfTrending = results.getResults();

                int cantidad = listOfTrending.size();
                cantidad2 = cantidad;

                while (i < cantidad) {
                    TrendingResults.Result trending = listOfTrending.get(i);
                    titulo = (String) trending.getTitle();
                    release = (String) trending.getFirstAirDate();
                    generos = (List<Integer>) trending.getGenreIds();
                    sinopsis = (String) trending.getOverview();
                    language= (String)trending.getOriginalLanguage();
                    poster_path= (String) trending.getPosterPath();
                    popularity = (Double) trending.getPopularity();
                    //Log.e("",titulo);

                    original_title = (String) trending.getOriginalTitle();
                    //Log.e("",Movie.getOriginal_title());
                    titulos.add(titulo);
                    releases.add(release);
                    generos2.add(generos);
                    sinopsisList.add(sinopsis);
                    languages.add(language);
                    poster_paths.add(poster_path);
                    popolularitys.add(popularity);
                    original_titles.add(original_title);

                    i = i + 1;
                }
                i = 0;
                while (i < cantidad) {
                    generos3.add(String.valueOf(generos2.get(i)));
                    i++;
                }
                initValues();

            }
            @Override
            public void onFailure(Call<TrendingResults> call, Throwable t) {

            }
        });


        return view;
    }
    private void initValues(){
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = getItems();
        recyclerAdapter3 = new RecyclerAdapter3(getContext(),itemList);
        recyclerView3.setAdapter(recyclerAdapter3);

        recyclerAdapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo2 = itemList.get(recyclerView3.getChildAdapterPosition(view)).getTitulo();
                Toast.makeText(getContext(),"selecciono"+titulo2,Toast.LENGTH_SHORT).show();
                Log.e("","selecciono"+titulo2);
                Intent detalle = new Intent(getContext(), DetalleMovieActivity.class);
                detalle.putExtra("titulo",titulo2);
                detalle.putExtra("genre",itemList.get(recyclerView3.getChildAdapterPosition(view)).getGenrer());
                detalle.putExtra("date",itemList.get(recyclerView3.getChildAdapterPosition(view)).getRelease());
                detalle.putExtra("sinopsis",itemList.get(recyclerView3.getChildAdapterPosition(view)).getSinopsis());
                detalle.putExtra("language",itemList.get(recyclerView3.getChildAdapterPosition(view)).getLanguage());
                detalle.putExtra("image",itemList.get(recyclerView3.getChildAdapterPosition(view)).getPosterPath());
                detalle.putExtra("original_title",itemList.get(recyclerView3.getChildAdapterPosition(view)).getOriginal_title());

                startActivity(detalle);

            }
        });

    }
    private List<ItemListTrending> getItems(){
        List<ItemListTrending> itemLists = new ArrayList<>();
        while(j<cantidad2) {

            itemLists.add(new ItemListTrending(titulos.get(j), releases.get(j),generos3.get(j),sinopsisList.get(j),languages.get(j),poster_paths.get(j),popolularitys.get(j),original_titles.get(j)));
            j+=1;
        }


        return itemLists;
    }
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
