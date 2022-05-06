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
import com.example.entrega4.MovieDetailsResults;
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
    public List<String> titulos, releases,generos3,sinopsisList,languages, poster_paths,original_titles,generos_final,companys;
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
        generosid = new ArrayList<Integer>();
        generos_final = new ArrayList<String>();
        MovieIds = new ArrayList<Integer>();
        companys = new ArrayList<String>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                Log.e("","entra en el onResponse");
                MovieResults results = response.body();
                List<MovieResults.ResultsBean> listOfMovies = results.getResults();
                //initValues();
                int cantidad = listOfMovies.size();
                cantidad2 = cantidad;

                while (i < cantidad) {
                    MovieResults.ResultsBean Movie = listOfMovies.get(i);
                    titulo = (String) Movie.getOriginal_title();
                    release = (String) Movie.getRelease_date();

                    sinopsis = (String) Movie.getOverview();
                    language= (String)Movie.getOriginal_language();
                    poster_path= (String) Movie.getPoster_path();
                    popularity = (Double) Movie.getPopularity();
                    id = (Integer)Movie.getId();
                    //Log.e("",String.valueOf(Movie.getId()));

                    original_title = (String) Movie.getOriginal_title();
                    //Log.e("",Movie.getOriginal_title());
                    titulos.add(titulo);
                    releases.add(release);

                    sinopsisList.add(sinopsis);
                    languages.add(language);
                    poster_paths.add(poster_path);
                    popolularitys.add(popularity);
                    original_titles.add(original_title);
                    MovieIds.add(id);

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
                                    GenreName += listOfGenre.get(iteradorListaGeneros).getName()+ " ";
                                    iteradorListaGeneros++;
                                }
                            }
                            if(SizeCompanysList==1) {
                                CompnayName = listOfCompanys.get(iteradorListaCompanys).getName();
                            }else{
                                while (iteradorListaCompanys < SizeCompanysList) {
                                    CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + " ";
                                    iteradorListaCompanys++;
                                }
                            }
                            Log.e("",GenreName);
                            Log.e("",CompnayName);
                            companys.add(CompnayName);
                            generos3.add(GenreName);
                        }
                        @Override
                        public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

                        }

                    });
                    //iteradorMovie++;
                    i = i + 1;
                }
                //initValues();
                while(MovieIds.size()!=cantidad2) {
                    if (MovieIds.size() == cantidad2) {
                        initValues();
                        Log.e("" , "El tamaño del MovieIDs es: "+ String.valueOf(MovieIds.size()));
                    }
                }

            }




            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }

        });
        while(MovieIds.size()!=cantidad2) {
            if (MovieIds.size() == cantidad2) {
                initValues();
                Log.e("" , "El tamaño del MovieIDs es: "+ String.valueOf(MovieIds.size()));
            }
        }
        Log.e("" , "El tamaño del MovieIDs es: "+ String.valueOf(MovieIds.size()));
        //initValues();

        return view;
    }




    private void initValues(){
        itemList = getItems();
        //RecyclerAdapter adaptador = new RecyclerAdapter(getContext(),itemList);
        //LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext(), VERTICAL,false);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        //recyclerView.setLayoutManager(recyce);
       // RecyclerAdapter rAdapter = new RecyclerAdapter(getContext(),itemList);
        //recyclerView.setAdapter(rAdapter);

        recyclerAdapter = new RecyclerAdapter(getContext(),itemList);
        //itemList = getItems();
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
                detalle.putExtra("sinopsis",itemList.get(recyclerView.getChildAdapterPosition(view)).getSinopsis());
                detalle.putExtra("language",itemList.get(recyclerView.getChildAdapterPosition(view)).getLanguage());
                detalle.putExtra("image",itemList.get(recyclerView.getChildAdapterPosition(view)).getPosterPath());
               detalle.putExtra("company",itemList.get(recyclerView.getChildAdapterPosition(view)).getCompany());
                startActivity(detalle);

            }
        });


    }
    private List<ItemList> getItems(){
        List<ItemList> itemLists = new ArrayList<>();
        while(j<cantidad2) {

            itemLists.add(new ItemList(titulos.get(j), releases.get(j),
                    generos3.get(j),sinopsisList.get(j),languages.get(j),poster_paths.get(j),
                    popolularitys.get(j),titulos.get(j),companys.get(i)));
            j+=1;
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
