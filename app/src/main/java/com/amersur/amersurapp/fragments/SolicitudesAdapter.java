package com.amersur.amersurapp.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amersur.amersurapp.Models.Solicitud;
import com.amersur.amersurapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.SolicitudViewHolder> {

    private List<Solicitud> solicitudes;

    public SolicitudesAdapter(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    @NonNull
    @Override
    public SolicitudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new SolicitudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudViewHolder holder, int position) {
        Solicitud solicitud = solicitudes.get(position);
        holder.bind(solicitud);
    }

    @Override
    public int getItemCount() {
        return solicitudes.size();
    }

    static class SolicitudViewHolder extends RecyclerView.ViewHolder {
        private TextView nombrePropiedadTextView;
        private TextView fechaTextView;
        private TextView estadoTextView;

        public SolicitudViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrePropiedadTextView = itemView.findViewById(R.id.nombre_propiedad);
            fechaTextView = itemView.findViewById(R.id.fecha_propiedad);
            estadoTextView = itemView.findViewById(R.id.estado_propiedad);
        }

        public void bind(Solicitud solicitud) {
            // Dependiendo de cómo obtienes la información de la propiedad, ajusta esta lógica
            // Por ejemplo, asumiendo que tienes una lista de propiedades con sus IDs y nombres
            obtenerNombrePropiedad(solicitud.getUidpro());

            fechaTextView.setText(solicitud.getFecha());

            // Ajusta la lógica para el estado según tus necesidades
            String estado = (solicitud.getEstado() == 1) ? "Activo" : "Inactivo";
            estadoTextView.setText(estado);
        }

        // Ejemplo ficticio de cómo obtener el nombre de la propiedad según el ID
        private void obtenerNombrePropiedad(int uidPro) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PROPIEDADES").child(String.valueOf(uidPro));

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Obtener el nombre de la propiedad desde el snapshot
                        String nombrePropiedad = dataSnapshot.child("NOMBRE").getValue(String.class);
                        // Actualizar el TextView con el nombre de la propiedad
                        nombrePropiedadTextView.setText(nombrePropiedad);
                    } else {
                        // Si no se encuentra la propiedad, puedes devolver un valor predeterminado o un mensaje
                        nombrePropiedadTextView.setText("Propiedad Desconocida");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar errores al obtener datos de Firebase
                    // Log.e(TAG, "Error getting data", databaseError.toException());
                }
            });
        }
    }
}

