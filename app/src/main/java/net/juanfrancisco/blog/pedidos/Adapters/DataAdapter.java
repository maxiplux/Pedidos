package net.juanfrancisco.blog.pedidos.Adapters;

/**
 * Created by franc on 28/08/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.juanfrancisco.blog.pedidos.Activities.ListaProductosActivity;
import net.juanfrancisco.blog.pedidos.Models.Producto;
import net.juanfrancisco.blog.pedidos.R;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitHelper;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Producto> datasource;
    private Context context;

    public DataAdapter(Context context,ArrayList<Producto> datasource)
    {
        this.context = context;
        this.datasource = datasource;

    }



    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {

        viewHolder.nombre.setText(datasource.get(i).getNombre());
        viewHolder.descripcion.setText(datasource.get(i).getDescripcion());
        Picasso.with(context).load(datasource.get(i).getImagen()).resize(250, 125).into(viewHolder.imagen);
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre;
        TextView descripcion;
        ImageView imagen;
        public ViewHolder(View view)
        {
            super(view);

            nombre = (TextView)view.findViewById(R.id.Row_Producto_Nombre);
            imagen = (ImageView)view.findViewById(R.id.Row_Producto_Imagen);



            descripcion = (TextView)view.findViewById(R.id.Row_Producto_Descripcion);

            descripcion.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}

