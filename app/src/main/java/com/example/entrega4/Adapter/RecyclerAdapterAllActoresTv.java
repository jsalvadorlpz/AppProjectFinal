package com.example.entrega4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.CreditResultTv;
import com.example.entrega4.R;

import java.util.List;

public class RecyclerAdapterAllActoresTv extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public interface verTodoslosActores{
        void funcionVerTodos(Integer id);
    }

    LayoutInflater inflater;
    private List<CreditResultTv.Cast> listaActores2;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    Context context;

    //constructor
    public RecyclerAdapterAllActoresTv(Context context, List<CreditResultTv.Cast> listaActores){
        this.inflater = LayoutInflater.from(context);
        this.listaActores2 = listaActores;
        this.context = context;
    }
    public void updateDataAllActoresTv(List<CreditResultTv.Cast> newitems) {
        listaActores2.clear();
        listaActores2.addAll(newitems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actors_all, parent, false);
        View view = inflater.inflate(R.layout.item_actors_all, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.personaje3.setText(listaActores2.get(position).getCharacter());
        viewHolder.nombre3.setText(listaActores2.get(position).getName());
        View view = inflater.inflate(R.layout.item_actors, null, false);
        Glide.with(view).load(url_imagenes + listaActores2.get(position).getProfilePath()).into(viewHolder.image3);

    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView personaje3, nombre3;
        private ImageView image3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personaje3 = itemView.findViewById(R.id.personajeactor_all);
            nombre3 = itemView.findViewById(R.id.nombreactor_all);
            image3 = itemView.findViewById(R.id.imagenActorAll);
        }
    }

    @Override
    public int getItemCount() {
        return listaActores2.size();
    }
}
