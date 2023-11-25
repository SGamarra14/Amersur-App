package com.amersur.amersurapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.amersur.amersurapp.MainActivity;
import com.amersur.amersurapp.R;
import com.amersur.amersurapp.propiedades.DetallePropiedad;
import com.amersur.amersurapp.propiedades.Propiedades;
import com.amersur.amersurapp.propiedades.ViewHolderPropiedades;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Inicio extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerUbicacion;

    RecyclerView recyclerviewPropiedades;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Propiedades, ViewHolderPropiedades> firebaseRecyclerAdapter;

    FirebaseRecyclerOptions<Propiedades> options;

    ValueEventListener valueEventListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        //para llenar el recyclerview de propiedades
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("PROPIEDADES");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerviewPropiedades = view.findViewById(R.id.recyclerViewPropiedades);
        recyclerviewPropiedades.setHasFixedSize(true);
        recyclerviewPropiedades.setLayoutManager(linearLayoutManager);

        VerPropiedades();

        //para llenar el spinner
        spinnerUbicacion = view.findViewById(R.id.spinner_ubicacion);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(requireContext(), R.array.ubicaciones, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUbicacion.setAdapter(adapter2);
        spinnerUbicacion.setOnItemSelectedListener(this);

        return view;
    }

    private void VerPropiedades() {
        options = new FirebaseRecyclerOptions.Builder<Propiedades>().setQuery(reference, Propiedades.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Propiedades, ViewHolderPropiedades>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderPropiedades viewHolderPropiedades, int position, @NonNull Propiedades propiedades) {
                viewHolderPropiedades.setPropiedadItem(
                        getActivity(),
                        propiedades.getNOMBRE(),
                        propiedades.getAREA(),
                        propiedades.getFOTO1()
                );
            }

            @NonNull
            @Override
            public ViewHolderPropiedades onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propiedad, parent, false);
                ViewHolderPropiedades viewHolderPropiedades = new ViewHolderPropiedades(itemView);

                viewHolderPropiedades.setOnClickListener(new ViewHolderPropiedades.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //int id = (int) view.getTag();

                        Intent intent = new Intent(getActivity(), DetallePropiedad.class);

                        int id = getItem(position).getID();
                        String foto1 = getItem(position).getFOTO1();
                        String nombre = getItem(position).getNOMBRE();
                        String desc = getItem(position).getDESCRIPCION();
                        String ubicacion = getItem(position).getUBICACION();
                        int estado = getItem(position).getESTADO();
                        String area = getItem(position).getAREA();
                        double precio = getItem(position).getPRECIO();
                        final int visualizaciones = getItem(position).getVISUALIZACIONES();

                        intent.putExtra("Id", id);
                        intent.putExtra("Foto1", foto1);
                        intent.putExtra("Nombre", nombre);
                        intent.putExtra("Descripcion", desc);
                        intent.putExtra("Ubicacion", ubicacion);
                        intent.putExtra("Estado", estado);
                        intent.putExtra("Area", area);
                        intent.putExtra("Precio", precio);
                        intent.putExtra("Visualizaciones", visualizaciones);

                        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Propiedades propiedads = ds.getValue(Propiedades.class);

                                    if (propiedads.getID() == id) {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("VISUALIZACIONES", visualizaciones + 1);
                                        ds.getRef().updateChildren(hashMap);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        startActivity(intent);
                    }

                });
                return viewHolderPropiedades;
            }
        };
        recyclerviewPropiedades.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter != null) {
            recyclerviewPropiedades.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (reference != null && valueEventListener != null) {
            reference.removeEventListener(valueEventListener);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        // Verifica si la opción seleccionada es "Seleccionar"
        if (text.equals("Seleccionar")) {
            // Restablece la consulta sin ningún filtro
            options = new FirebaseRecyclerOptions.Builder<Propiedades>().setQuery(reference, Propiedades.class).build();
            firebaseRecyclerAdapter.updateOptions(options);
            firebaseRecyclerAdapter.notifyDataSetChanged();
            return;
        }

        // Si no es "Seleccionar", aplica el filtro según el Spinner
        Query query;
        if (parent.getId() == R.id.spinner_ubicacion) {
            query = reference.orderByChild("UBICACION").equalTo(text);
        } else {
            return; // Spinner no reconocido, salir
        }

        options = new FirebaseRecyclerOptions.Builder<Propiedades>().setQuery(query, Propiedades.class).build();
        firebaseRecyclerAdapter.updateOptions(options);
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}