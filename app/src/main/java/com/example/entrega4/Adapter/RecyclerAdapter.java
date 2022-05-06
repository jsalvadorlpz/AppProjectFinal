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
import com.example.entrega4.R;
import com.example.entrega4.model.ItemList;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener{
    private List<ItemList> items;
    LayoutInflater inflater;
    Fragment fragment;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos

    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }



    public RecyclerAdapter(Context context, List<ItemList> items){
        this.inflater = LayoutInflater.from(context);

        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Llama a este método siempre que necesita crear una ViewHolder nueva.
        // El método crea el ViewHolder y su View asociada, y los inicializa,
        // pero no completa el contenido de la vista
        // (aún no se vinculó el ViewHolder con datos específicos).
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        View view = inflater.inflate(R.layout.item_list_view,parent,false);
        view.setOnClickListener(this);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view,parent,false);
        //inflate para modificar la jerarquia de views, para poder utiliar diseños en otras views

        return new ViewHolder(view);
    }
    //el ViewHolder se utiliza para cada contenedor dentro de la vista de Recycler, para
    //vincular los datos
    // tenemos unos datos y queremos vincularlos con esas vistas, asi que se crea el adapter
    //public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Llama a este método para asociar una ViewHolder con los datos.
        // El método recupera los datos correspondientes y los usa
        // para completar el diseño del contenedor de vistas.

        String Titulo = items.get(position).getTitulo();
        //String original_title = items.get(position).getOriginal_title();
        String Release = items.get(position).getRelease();
        String Genrer = items.get(position).getGenrer();
        Double pop = items.get(position).getPopularity();
        //Glide.with(getContext()).load(poster_paths.get(j)).into();
        View view = inflater.inflate(R.layout.item_list_view,null,false);
        Glide.with(view).load(url_imagenes+items.get(position).getPosterPath()).into(holder.image);
        holder.Titulo.setText(Titulo);
        holder.Release.setText(Release);
        holder.Genrer.setText(Genrer);
        holder.popu.setProgress((int) Math.round((pop/100)));
        holder.prog.setText(String.valueOf(Math.round((pop/1000)*100.0)/100.0));

        //ItemList item = items.get(position);
        //holder.imgItem.setImageResource(item.getImgResource());
        // holder.Titulo.setText(item.getTitulo());
        //holder.Release.setText(item.getRelease());
        //holder.Genrer.setText(item.getGenrer());
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

    @Override
    public int getItemCount() {
        return items.size();
    }

}