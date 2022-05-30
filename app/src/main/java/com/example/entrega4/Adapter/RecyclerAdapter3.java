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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.R;
import com.example.entrega4.TrendingResults;

import java.util.List;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener{
    private List<TrendingResults.Result> trendings;
    LayoutInflater inflater;
    public  String GenreName, date;
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;
    public static String MEDIA_TYPE = "movie";
    public static String TIME_WINDOW = "week";
    Fragment fragment;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String BASE_URL = "https://api.themoviedb.org";
    private List<String> listaGeneros;
    public List<TrendingResults.Result> trendings2;
    public int page =2;
    private botonCargarMasTrending botonCargarMasTrending;
    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }

    public interface botonCargarMasTrending{
        void funcionCargarMasTrending(int page);
    }


    public RecyclerAdapter3(Context context, List<TrendingResults.Result> trendings,List<String> listaGeneros,
                            botonCargarMasTrending botoncargarmastrending){
        this.inflater = LayoutInflater.from(context);
        this.listaGeneros = listaGeneros;
        this.trendings = trendings;
        this.botonCargarMasTrending = botoncargarmastrending;
    }
    public void updateDataTrending(List<TrendingResults.Result>  newitems, List<String> newGeneros) {
        trendings.clear();
        trendings.addAll(newitems);
        listaGeneros.addAll(newGeneros);
        notifyDataSetChanged();
    }
    public  void updateTrending(List<TrendingResults.Result> newitems, List<String> newGeneros) {
        trendings.addAll(newitems);
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
            Double pop = trendings.get(position).getPopularity();
            //Glide.with(getContext()).load(poster_paths.get(j)).into();
            View view = inflater.inflate(R.layout.item_list_view, null, false);
            Glide.with(view).load(url_imagenes + trendings.get(position).getPosterPath()).into(viewHolder.image);
            viewHolder.Titulo.setText(trendings.get(position).getTitle());
            viewHolder.Release.setText(trendings.get(position).getFirstAirDate());
            viewHolder.Genrer.setText(listaGeneros.get(position));
            viewHolder.popu.setProgress((int) (pop / 50));
            viewHolder.prog.setText(String.valueOf(Math.round(((pop / 50.0)) * 100.0) / 100.0) + "%");
        }else{
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.cargaMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicado el carga mas",Toast.LENGTH_SHORT).show();
                    botonCargarMasTrending.funcionCargarMasTrending(page);
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
            // poster = itemView.findViewById(R.id.imgItem);
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
        if(position == trendings.size())return TIPO_VER_MAS;
        return TIPO_NORMAL;
    }
    @Override
    public int getItemCount() {
        return trendings.size()+1;
    }

}
