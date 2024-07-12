package com.amersur.amersurapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amersur.amersurapp.Models.Solicitud;
import com.amersur.amersurapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSolicitudes extends Fragment {

    private RecyclerView recyclerView;
    private SolicitudesAdapter adapter;
    private List<Solicitud> solicitudes;

    public ListSolicitudes() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_solicitudes, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewSolicitudes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Obtener tus datos (solicitudes) de Firebase o donde sea necesario
        solicitudes = obtenerSolicitudesDesdeFirebase(); // Implementa esta función según tu caso

        // Crear y configurar el adaptador
        adapter = new SolicitudesAdapter(solicitudes);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Función ficticia para obtener solicitudes desde Firebase (ajusta según tu implementación)
    private List<Solicitud> obtenerSolicitudesDesdeFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            // El usuario no está autenticado, manejar según sea necesario
            return new ArrayList<>();
        }

        String uidUsuarioActual = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SOLICITUDES");
        List<Solicitud> listaSolicitudesUsuario = new ArrayList<>();

        databaseReference.orderByChild("uiduser").equalTo(uidUsuarioActual).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtener datos del snapshot y agregar a la lista de solicitudes del usuario
                    String uid = snapshot.getKey();
                    String uiduser = snapshot.child("uiduser").getValue(String.class);
                    int uidpro = snapshot.child("uidpro").getValue(Integer.class);
                    String fecha = snapshot.child("fecha").getValue(String.class);
                    int estado = snapshot.child("estado").getValue(Integer.class);

                    Solicitud solicitud = new Solicitud(uid, uiduser, uidpro, fecha, estado);

                    listaSolicitudesUsuario.add(solicitud);
                }

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores al obtener datos de Firebase
                Log.e("ERROR", "Error getting data", databaseError.toException());
            }
        });

        return listaSolicitudesUsuario;
    }
}