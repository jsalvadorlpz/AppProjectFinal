package com.example.entrega4.Adapter;

import android.content.Context;
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
import com.example.entrega4.R;
import com.example.entrega4.UpComingsDetails;

import java.util.List;

public class RecyclerAdapterUpcomings extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener  {
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;
    public static String  LANGUAGE = "en-US";
    public static String CATEGORY="popular";
    private List<UpComingsDetails.Result> peliculas;
    private List<String> listaGeneros;
    LayoutInflater inflater;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    public static String BASE_URL = "https://api.themoviedb.org";
    public int page =2;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";

    private RecyclerAdapterUpcomings.botonCargarMas botonCargarMas;

    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }
    //interface
    public interface botonCargarMas{
        void funcionCargarMas(int page);
    }





    public RecyclerAdapterUpcomings(Context context, List<UpComingsDetails.Result> peliculas, List<String> listaGeneros, RecyclerAdapterUpcomings.botonCargarMas botoncargarmas){
        this.inflater = LayoutInflater.from(context);
        this.peliculas = peliculas;
        this.listaGeneros = listaGeneros;
        this.botonCargarMas = botoncargarmas;
    }
    public void updateData(List<UpComingsDetails.Result> newitems,List<String> newGeneros) {
        peliculas.clear();
        peliculas.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }
    public void updateUpcomings(List<UpComingsDetails.Result> newitems,List<String> newGeneros) {
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
        if (holder.getItemViewType() == TIPO_NORMAL) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String Titulo = peliculas.get(position).getTitle();
            String Release = peliculas.get(position).getReleaseDate();
            Integer id = peliculas.get(position).getId();

            Double pop = peliculas.get(position).getPopularity();
            View view = inflater.inflate(R.layout.item_list_view, null, false);
            Glide.with(view).load(url_imagenes + peliculas.get(position).getPosterPath()).into(viewHolder.image);
            viewHolder.Titulo.setText(Titulo);
            viewHolder.Release.setText(Release);
            viewHolder.Genrer.setText(listaGeneros.get(position));
            viewHolder.popu.setProgress((int) Math.round((pop * 10)));
            viewHolder.prog.setText(String.valueOf(Math.round((pop * 10)) + "%"));
        } else {

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.cargaMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicado el carga mas", Toast.LENGTH_SHORT).show();
                    botonCargarMas.funcionCargarMas(page);
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
