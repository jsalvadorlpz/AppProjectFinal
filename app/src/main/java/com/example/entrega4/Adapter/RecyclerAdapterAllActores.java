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
import com.example.entrega4.CreditResults;
import com.example.entrega4.R;

import java.util.List;

public class RecyclerAdapterAllActores extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
public static final int TIPO_VER_MAS=1;
public static final int  TIPO_NORMAL=2;

public interface verTodoslosActores{
    void funcionVerTodos(Integer id);
}

LayoutInflater inflater;
private List<CreditResults.Cast> listaActores;
public String url_imagenes = "https://image.tmdb.org/t/p/w500";
        Context context;

//constructor
public RecyclerAdapterAllActores(Context context, List<CreditResults.Cast> listaActores){
        this.inflater = LayoutInflater.from(context);
        this.listaActores = listaActores;
        this.context = context;
        }
public void updateDataActores(List<CreditResults.Cast> newitems) {
        listaActores.clear();
        listaActores.addAll(newitems);
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
        viewHolder.personaje.setText(listaActores.get(position).getCharacter());
        viewHolder.nombre.setText(listaActores.get(position).getName());
        View view = inflater.inflate(R.layout.item_actors, null, false);
        Glide.with(view).load(url_imagenes + listaActores.get(position).getProfilePath()).into(viewHolder.image);

        }




public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView personaje, nombre;
    private ImageView image;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        personaje = itemView.findViewById(R.id.personajeactor_all);
        nombre = itemView.findViewById(R.id.nombreactor_all);
        image = itemView.findViewById(R.id.imagenActorAll);
    }
}

    @Override
    public int getItemCount() {
        return listaActores.size();
    }

}
