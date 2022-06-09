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
import com.example.entrega4.SeriesResults;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private final List<SeriesResults.Result> series;

    LayoutInflater inflater;
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;


    public int page = 2;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public String genero;
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String BASE_URL = "https://api.themoviedb.org";
    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos
    private RecyclerAdapter.botonCargarMas botonCargarMas;
    private List<String> listaGeneros;
    //listener
    private View.OnClickListener  listener;
    public  void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    public interface botonCargarMas2{
        void funcionCargarMas2(int page);
    }

    private botonCargarMas2 botonCargarMas2;
    public RecyclerAdapter2(Context context, List<SeriesResults.Result> series, List<String> listaGeneros, botonCargarMas2 botoncargarmas2){
        this.inflater = LayoutInflater.from(context);
        this.series = series;
        this.listaGeneros = listaGeneros;
        this.botonCargarMas2 = botoncargarmas2;
    }

    public void updateData(List<SeriesResults.Result> newitems,List<String> newGeneros) {
        series.clear();
        series.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }

    public void updateSeries(List<SeriesResults.Result> newitems, List<String> newGeneros) {
        series.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TIPO_NORMAL) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
            View view = inflater.inflate(R.layout.item_list_view,parent,false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }else{
            View filaverMas =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pie_de_vista, parent, false);
            View viewVerMas = inflater.inflate(R.layout.item_pie_de_vista,parent,false);
            return new FooterViewHolder2(viewVerMas);
        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() ==TIPO_NORMAL) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String Titulo = series.get(position).getName();
            String release = series.get(position).getFirstAirDate();
            Integer id = series.get(position).getId();
            Double pop = series.get(position).getPopularity();
            Double average = series.get(position).getVoteAverage();


            View view = inflater.inflate(R.layout.item_list_view, null, false);
            Glide.with(view).load(url_imagenes + series.get(position).getPosterPath()).into(viewHolder.image);
            viewHolder.Titulo.setText(Titulo);
            viewHolder.Release.setText(release);
            viewHolder.Genrer.setText(listaGeneros.get(position));
            viewHolder.popu.setProgress((int) Math.round((pop / 60)));
            viewHolder.prog.setText(String.valueOf(Math.round((pop / 60)) + "%"));
        }else{
            FooterViewHolder2 footerViewHolder2 = (FooterViewHolder2) holder;
            footerViewHolder2.cargaMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicado el carga mas",Toast.LENGTH_SHORT).show();
                    botonCargarMas2.funcionCargarMas2(page);
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

    public class FooterViewHolder2 extends RecyclerView.ViewHolder{
        private TextView cargaMas;

        public FooterViewHolder2(@NonNull View itemView){
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
        if(position == series.size())return TIPO_VER_MAS;
        return TIPO_NORMAL;
    }
    @Override
    public int getItemCount() {
        return series.size()+1;
    }

}
