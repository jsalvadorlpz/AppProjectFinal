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

import com.example.entrega4.Adapter.RecyclerAdapter2;
import com.example.entrega4.DetalleSerieActivity;
import com.example.entrega4.R;
import com.example.entrega4.SeriesResults;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.iComunicaFragments;
import com.example.entrega4.model.ItemListSeries;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeriesFragment extends Fragment {
    RecyclerAdapter2 recyclerAdapter2;
    RecyclerView recyclerView2;
    List<ItemListSeries> itemListSeries;
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
    public String titulo_serie,release_serie,poster_path_serie,sinopsis_serie,language_serie,original_title_serie,nombregeneros,gen;
    public List<String> titulos_serie, releases_serie,generos3_serie,sinopsisList_serie,languages_serie, poster_paths_serie,original_titles_serie,generos_final_serie;
    public List<Integer> generos_serie,generosid_serie;
    public List<List<Integer>> generos2_serie;
    public double popularity_serie;
    public List<Object> generos4_serie;
    public List<Double> popolularitys_serie;
    public int cantidad2,j,i,z  = 0;
    public boolean encontrado;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.main_fragment,container, false);
        //initViews
        recyclerView2 = view.findViewById(R.id.recyclerview_series);
        //anterior = view.findViewById(R.id.botonAnterior);
        //siguiente = view.findViewById(R.id.botonSnterior);
        itemListSeries = new ArrayList<>();

        titulos_serie = new ArrayList<String>();
        original_titles_serie = new ArrayList<String>();
        releases_serie = new ArrayList<String>();
        generos_serie = new ArrayList<Integer>();
        generos2_serie = new ArrayList<List<Integer>>();
        generos3_serie = new ArrayList<String>();
        sinopsisList_serie = new ArrayList<>();
        languages_serie = new ArrayList<>();
        poster_paths_serie = new ArrayList<String>();
        popolularitys_serie = new ArrayList<Double>();
        generos4_serie = new ArrayList<Object>();
        generosid_serie = new ArrayList<Integer>();
        generos_final_serie = new ArrayList<String>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<SeriesResults> call = myInterface.listOfSeries(CATEGORY,API_KEY,LANGUAGE,PAGE);
        call.enqueue(new Callback<SeriesResults>() {
            @Override
            public void onResponse(Call<SeriesResults> call, Response<SeriesResults> response) {
                Log.e("","entra en el onResponse");
                SeriesResults results = response.body();
                List<SeriesResults.Result> listOfSeries = results.getResults();
                initValues();
                int cantidad = listOfSeries.size();
                cantidad2 = cantidad;

                while (i < cantidad) {
                    SeriesResults.Result Serie = listOfSeries.get(i);
                    titulo_serie = (String) Serie.getName();
                    //release_serie = (String) Serie.getPosterPath();
                    //generos_serie = (List<Integer>) Serie.getGenreIds();
                    //sinopsis_serie = (String) Serie.getOverview();
                    //language_serie= (String)Serie.getOriginalLanguage();
                    poster_path_serie= (String) Serie.getBackdropPath();
                    //popularity_serie = (Double) Serie.getPopularity();
                    //Log.e("",poster_path);

                    //original_title_serie = (String) Serie.getOriginalName();
                    //Log.e("",Movie.getOriginal_title());
                    titulos_serie.add(titulo_serie);
                    //releases_serie.add(release_serie);
                    //generos2_serie.add(generos_serie);
                    //sinopsisList_serie.add(sinopsis_serie);
                    //languages_serie.add(language_serie);
                    poster_paths_serie.add(poster_path_serie);
                    //popolularitys_serie.add(popularity_serie);
                    //original_titles_serie.add(original_title_serie);

                    i = i + 1;
                }
                i = 0;
                //while (i < cantidad) {
                  //  generos3_serie.add(String.valueOf(generos2_serie.get(i)));
                  //  i++;
               // }
                i=0;
                Log.e("hay: ", Integer.toString(cantidad));
                initValues();
            }
            @Override
            public void onFailure(Call<SeriesResults> call, Throwable t) {

            }
        });
        //    Call<GenreResults> call2 = myInterface.listOfGenre(API_KEY);
        //    call2.enqueue(new Callback<GenreResults>() {
        //        @Override
        //        public void onResponse(Call<GenreResults> call, Response<GenreResults> response) {
        //            GenreResults results = response.body();
        //            List<GenreResults.Genre> listOfGenre = results.getGenres();
        //            int cantidad = listOfGenre.size();
        //            cantidad2 = cantidad;

        //            while(i<cantidad){
        //                generos4.add(listOfGenre.get(i));
        //            }
        //            i=0;
        //          j=0;

        //            while(i<generos2.size()){
        //                    generosid = generos2.get(i);
        //                while(j<generosid.size()){
        //                    int id = generosid.get(j);
        //                    encontrado = false;
        //                  z=0;
        //                  while(encontrado != true){
        //                      int id2 = listOfGenre.get(z).getId();
        //                      if(id == id2){
        //                           nombregeneros = listOfGenre.get(z).getName();
        //                           encontrado = true;
        //                        }
        //                        z++;
        //                   }
        //                    gen += nombregeneros + " ";
        //                    j++;

        //                }
        //                generos_final.add(gen);
        //                i++;
        //            }
        //      initValues();
        //    }

        //    @Override
        //        public void onFailure(Call<GenreResults> call, Throwable t) {

        //      }
        //    });

        return view;
    }




    private void initValues(){
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        itemListSeries = getItemsSeries();
        recyclerAdapter2 = new RecyclerAdapter2(getContext(),itemListSeries);
        recyclerView2.setAdapter(recyclerAdapter2);

        recyclerAdapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo2 = itemListSeries.get(recyclerView2.getChildAdapterPosition(view)).getTitulo();
                Toast.makeText(getContext(),"selecciono"+titulo2,Toast.LENGTH_SHORT).show();
                Log.e("","selecciono"+titulo2);
                Intent detalle = new Intent(getContext(), DetalleSerieActivity.class);
                detalle.putExtra("titulo",titulo2);
              //  detalle.putExtra("genre2",itemList.get(recyclerView2.getChildAdapterPosition(view)).getGenrer());
              //  detalle.putExtra("date2",itemList.get(recyclerView2.getChildAdapterPosition(view)).getRelease());
              //  detalle.putExtra("sinopsis2",itemList.get(recyclerView2.getChildAdapterPosition(view)).getSinopsis());
              //  detalle.putExtra("language2",itemList.get(recyclerView2.getChildAdapterPosition(view)).getLanguage());
                detalle.putExtra("image2",itemListSeries.get(recyclerView2.getChildAdapterPosition(view)).getPosterPathSerie());
                startActivity(detalle);

            }
        });


    }
    private List<ItemListSeries> getItemsSeries(){
        List<ItemListSeries> itemListsSerie = new ArrayList<>();
        while(j<cantidad2) {

            itemListsSerie.add(new ItemListSeries(titulos_serie.get(j),poster_paths_serie.get(j)));
            j+=1;
        }


        return itemListsSerie;
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
