package com.domo.zoom.serviceya;


import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

        import java.util.List;

public class PrestadorAdapter extends RecyclerView.Adapter<PrestadorAdapter.MyViewHolder> {

    private List<ItemPrestador> presList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, calif, comen;
        public ImageView imagen;

        public MyViewHolder(View view) {
            super(view);
            imagen = view.findViewById(R.id.imageView2);
            nombre = view.findViewById(R.id.tvPresNombre);
            calif = view.findViewById(R.id.tvPresCalifica);
            comen = view.findViewById(R.id.tvPresComenta);
        }
    }


    public PrestadorAdapter(List<ItemPrestador> moviesList) {
        this.presList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prestador_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemPrestador prestador = presList.get(position);
        holder.imagen.setImageDrawable(prestador.getImagenItem());
        holder.nombre.setText(prestador.getNombreItem());
        holder.calif.setText(prestador.getCalItem());
        holder.comen.setText(prestador.getComenItem());
    }

    @Override
    public int getItemCount() {
        return presList.size();
    }
}
