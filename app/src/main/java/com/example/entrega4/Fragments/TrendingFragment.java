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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrega4.Adapter.RecyclerAdapter3;
import com.example.entrega4.DetalleTrendingActivity;
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
    public static String MEDIA_TYPE = "movie";
    public static String TIME_WINDOW = "week";
    public String titulo,release,poster_path,sinopsis,language,original_title;
    public List<String> titulos, releases,generos3,sinopsisList,languages, poster_paths,original_titles,TrendingIds;
    public List<Integer> generos;
    public List<List<Integer>> generos2;
    public double popularity;
    public List<Object> generos4;
    public List<Double> popolularitys;
    public int cantidad2,j,iterador  = 0;
    public List<TrendingResults.Result> trendings,listatrendings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment,container, false);

        recyclerView3 = view.findViewById(R.id.recyclerview_trending);
        listatrendings = new ArrayList<TrendingResults.Result>();
        trendings = new ArrayList<TrendingResults.Result>();
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter3 = new RecyclerAdapter3(getContext(),new ArrayList<>());
        recyclerView3.setAdapter(recyclerAdapter3);

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
                trendings = listOfTrending;

                initValues();

            }
            @Override
            public void onFailure(Call<TrendingResults> call, Throwable t) {
                Log.e("","entra en el fallo");
            }
        });


        return view;
    }
    private void initValues(){
        listatrendings = getItems();
        recyclerAdapter3.updateData(listatrendings);

        recyclerAdapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detalle = new Intent(getContext(), DetalleTrendingActivity.class);
                detalle.putExtra("imageTrending",listatrendings.get(recyclerView3.getChildAdapterPosition(view)).getPosterPath());
                detalle.putExtra("idTrending",String.valueOf(listatrendings.get(recyclerView3.getChildAdapterPosition(view)).getId()));
                startActivity(detalle);

            }
        });

    }
    private List<TrendingResults.Result> getItems(){
        return trendings;
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
