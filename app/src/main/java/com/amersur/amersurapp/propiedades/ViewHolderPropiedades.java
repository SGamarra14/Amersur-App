package com.amersur.amersurapp.propiedades;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amersur.amersurapp.R;
import com.squareup.picasso.Picasso;

public class ViewHolderPropiedades extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolderPropiedades.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
        //void onItemLongClick(View view, int position);
    }

    public void setOnClickListener (ViewHolderPropiedades.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public ViewHolderPropiedades(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });*/
    }

    public void setPropiedadItem (Context context, String nombre, String area, String foto1) {
        ImageView imagenPropiedad;
        TextView nombre_propiedad, area_propiedad;

        imagenPropiedad = mView.findViewById(R.id.imagenPropiedad);
        nombre_propiedad = mView.findViewById(R.id.nombre_propiedad);
        area_propiedad = mView.findViewById(R.id.area_propiedad);

        nombre_propiedad.setText(nombre);
        area_propiedad.setText("Área: " + area + " m²");
        //mView.setTag(id);

        try {
            Picasso.get().load(foto1).placeholder(R.drawable.imageplaceholder).into(imagenPropiedad);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.imageplaceholder).into(imagenPropiedad);
            //Toast.makeText(context, "Error: " +  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public String getTag() {
        return (String) mView.getTag();
    }*/
}
