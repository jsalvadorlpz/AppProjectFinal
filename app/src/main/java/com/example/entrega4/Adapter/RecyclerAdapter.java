package com.example.entrega4.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.MovieResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener  {
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    private List<MovieResults.ResultsBean> peliculas;
    private List<String> listaGeneros;
    LayoutInflater inflater;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public static String BASE_URL = "https://api.themoviedb.org";
    public int page =2;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public List<MovieResults.ResultsBean> peliculas2;
    private botonCargarMas botonCargarMas;

    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }
   //interface
    public interface botonCargarMas{
        void funcionCargarMas();
   }





    public RecyclerAdapter(Context context, List<MovieResults.ResultsBean> peliculas,List<String> listaGeneros,botonCargarMas botoncargarmas){
        this.inflater = LayoutInflater.from(context);
        this.peliculas = peliculas;
        this.listaGeneros = listaGeneros;
        this.botonCargarMas = botoncargarmas;
    }
    public void updateData(List<MovieResults.ResultsBean> newitems,List<String> newGeneros) {
        peliculas.clear();
        peliculas.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }
    public void updatePelis(List<MovieResults.ResultsBean> newitems,List<String> newGeneros) {
        peliculas.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TIPO_NORMAL) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
            View view = inflater.inflate(R.layout.item_list_view, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }else{
                View filaverMas =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pie_de_vista, parent, false);
                View viewVerMas = inflater.inflate(R.layout.item_pie_de_vista,parent,false);
                return new FooterViewHolder(viewVerMas);
        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() ==TIPO_NORMAL) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String Titulo = peliculas.get(position).getTitle();
            String Release = peliculas.get(position).getRelease_date();
            Integer id = peliculas.get(position).getId();

            Double pop = peliculas.get(position).getVote_average();
            View view = inflater.inflate(R.layout.item_list_view, null, false);
            Glide.with(view).load(url_imagenes + peliculas.get(position).getPoster_path()).into(viewHolder.image);
            viewHolder.Titulo.setText(Titulo);
            viewHolder.Release.setText(Release);
            viewHolder.Genrer.setText(listaGeneros.get(position));
            viewHolder.popu.setProgress((int) Math.round((pop * 10)));
            viewHolder.prog.setText(String.valueOf(Math.round((pop * 10) * 100.0) / 100.0) + "%");
        } else {

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.cargaMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicado el carga mas",Toast.LENGTH_SHORT).show();
                    botonCargarMas.funcionCargarMas();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
                    Call<MovieResults> callMovies = myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,page);
                    Log.e("","crea la call");
                    callMovies.enqueue(new Callback<MovieResults>() {
                        @Override
                        public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                            Log.e("","hacemos la llamada del cargar mas");
                            MovieResults results = response.body();
                            List<MovieResults.ResultsBean> listOfMovies = results.getResults();
                            peliculas2 = listOfMovies;

                            updatePelis(peliculas2,listaGeneros);
                        }
                        @Override
                        public void onFailure(Call<MovieResults> call, Throwable t) {
                            Log.e("","entra fallo");
                        }

                    });
                    page++;
               }
           });
        }
    }


    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Titulo,Genrer,Release,prog;
        private ImageView image;
        private ProgressBar popu;
        // private ImageView poster;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Titulo = itemView.findViewById(R.id.Titulo);
            Release = itemView.findViewById(R.id.Release);
            Genrer = itemView.findViewById(R.id.Genrer);
            popu = itemView.findViewById(R.id.progress_bar);
            image = itemView.findViewById(R.id.imgItem);
            prog = itemView.findViewById(R.id.text_view_progress);

        }

    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView cargaMas;

        public FooterViewHolder(@NonNull View itemView){
            super(itemView);
            cargaMas = itemView.findViewById(R.id.cargamas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

    }

    @Override
    public int getItemViewType(int position){
        if(position == peliculas.size())return TIPO_VER_MAS;
        return TIPO_NORMAL;
    }


    @Override
    public int getItemCount() {
        return peliculas.size()+1;
    }

}