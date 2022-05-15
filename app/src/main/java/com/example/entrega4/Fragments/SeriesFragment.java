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
    public int cantidad2  = 0;

    public List<SeriesResults.Result> series,listSeries;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("","entra al oncreate");

        View view = inflater.inflate(R.layout.series_fragment,container, false);
        //initViews
        recyclerView2 = view.findViewById(R.id.recyclerview_series2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerAdapter2= new RecyclerAdapter2(getContext(),new ArrayList<>());
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
                int cantidad = listOfSeries.size();
                cantidad2 = cantidad;
                initValues();

            }
            @Override
            public void onFailure(Call<SeriesResults> call, Throwable t) {

            }
        });

        return view;
    }




    private void initValues(){

        listSeries = getItemsSeries();
        recyclerAdapter2.updateData(listSeries);
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
    private List<SeriesResults.Result> getItemsSeries(){



        return series;
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
