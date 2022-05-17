package com.example.entrega4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.MovieDetailsResults;
import com.example.entrega4.MovieResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;
    private List<MovieResults.ResultsBean> peliculas;
    LayoutInflater inflater;
    Fragment fragment;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 2;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public String GenreName;

    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }


    public RecyclerAdapter(Context context, List<MovieResults.ResultsBean> peliculas){
        this.inflater = LayoutInflater.from(context);
        this.peliculas = peliculas;
    }
    public void updateData(List<MovieResults.ResultsBean> newitems) {
        peliculas.clear();
        peliculas.addAll(newitems);
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
                View viewVerMas = inflater.inflate(R.layout.item_pie_de_vista,parent,false);return new ViewHolder(viewVerMas);
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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

            Call<MovieDetailsResults> call3 = myInterface.listOfMovieDetails(id, API_KEY);
            call3.enqueue(new Callback<MovieDetailsResults>() {
                @Override
                public void onResponse(Call<MovieDetailsResults> call, Response<MovieDetailsResults> response) {
                    int iteradorListaGeneros = 0;

                    MovieDetailsResults results = response.body();
                    List<MovieDetailsResults.Genre> listOfGenre = results.getGenres();
                    int SizeGenreList = listOfGenre.size();

                    if (SizeGenreList == 1) {
                        GenreName = listOfGenre.get(iteradorListaGeneros).getName();
                    } else {
                        while (iteradorListaGeneros < SizeGenreList) {
                            if (iteradorListaGeneros + 1 == SizeGenreList) {
                                GenreName += listOfGenre.get(iteradorListaGeneros).getName();
                            } else {
                                GenreName += listOfGenre.get(iteradorListaGeneros).getName() + ", ";
                                if (SizeGenreList > 6 && SizeGenreList == 6) {
                                    iteradorListaGeneros = SizeGenreList;
                                }
                            }
                            iteradorListaGeneros++;
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailsResults> call, Throwable t) {
                }
            });
            Double pop = peliculas.get(position).getVote_average();
            View view = inflater.inflate(R.layout.item_list_view, null, false);
            Glide.with(view).load(url_imagenes + peliculas.get(position).getPoster_path()).into(viewHolder.image);
            viewHolder.Titulo.setText(Titulo);
            viewHolder.Release.setText(Release);
            viewHolder.Genrer.setText(GenreName);
            viewHolder.popu.setProgress((int) Math.round((pop * 10)));
            viewHolder.prog.setText(String.valueOf(Math.round((pop * 10) * 100.0) / 100.0) + "%");
        } else {

           // FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
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