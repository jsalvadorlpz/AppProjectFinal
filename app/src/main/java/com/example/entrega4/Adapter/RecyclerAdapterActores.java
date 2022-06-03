package com.example.entrega4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.ActoresMovie;
import com.example.entrega4.CreditResults;
import com.example.entrega4.R;

import java.util.List;

public class RecyclerAdapterActores extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final int TIPO_VER_MAS=1;
    public static final int  TIPO_NORMAL=2;
    LayoutInflater inflater;
    private List<CreditResults.Cast>  listaActores;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    Context context;
    Integer id;

   //constructor
   public RecyclerAdapterActores(Context context, List<CreditResults.Cast> listaActores, Integer id){
       this.inflater = LayoutInflater.from(context);
       this.listaActores = listaActores;
       this.context = context;
       this.id = id;
   }
    public void updateDataActores(List<CreditResults.Cast> newitems,Integer id) {
        listaActores.clear();
        listaActores.addAll(newitems);
        this.id = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TIPO_NORMAL) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actors, parent, false);
            View view = inflater.inflate(R.layout.item_actors, parent, false);
            //view.setOnClickListener(this);
            return new ViewHolder(view);
        }else{
            View filaverMas =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pie_de_vista, parent, false);
            View viewVerMas = inflater.inflate(R.layout.item_actores_cargartodos,parent,false);
            return new FooterViewHolder(viewVerMas);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType() ==TIPO_NORMAL) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.personaje.setText(listaActores.get(position).getCharacter());
            viewHolder.nombre.setText(listaActores.get(position).getName());
            View view = inflater.inflate(R.layout.item_actors, null, false);
            Glide.with(view).load(url_imagenes + listaActores.get(position).getProfilePath()).into(viewHolder.image);
        }else{
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.cargaMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicado el carga mas actores",Toast.LENGTH_SHORT).show();

                    Intent verTodoslosActores = new Intent(context, ActoresMovie.class);
                    verTodoslosActores.putExtra("idpelicula",id);
                    context.startActivity(verTodoslosActores);
                }
            });
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView personaje, nombre;
        private ImageView image;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            personaje =itemView.findViewById(R.id.personaje);
            nombre = itemView.findViewById(R.id.nombreactor);
            image = itemView.findViewById(R.id.imagenActor);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        private TextView cargaMas;

        public FooterViewHolder(@NonNull View itemView){
            super(itemView);
            cargaMas = itemView.findViewById(R.id.cargamasactores);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

    }




    @Override
    public int getItemViewType(int position){
        if(position == listaActores.size())return TIPO_VER_MAS;
        return TIPO_NORMAL;
    }


    @Override
    public int getItemCount() {
        return listaActores.size()+1;
    }
}
