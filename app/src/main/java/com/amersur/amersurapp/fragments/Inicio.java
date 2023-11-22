package com.amersur.amersurapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.amersur.amersurapp.R;
import com.amersur.amersurapp.propiedades.Propiedades;
import com.amersur.amersurapp.propiedades.ViewHolderPropiedades;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inicio extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerUbicacion, spinnerTipo;

    RecyclerView recyclerviewPropiedades;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Propiedades, ViewHolderPropiedades> firebaseRecyclerAdapter;

    FirebaseRecyclerOptions<Propiedades> options;


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
        spinnerTipo = view.findViewById(R.id.spinner_tipo);
        spinnerUbicacion = view.findViewById(R.id.spinner_ubicacion);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.tipo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);
        spinnerTipo.setOnItemSelectedListener(this);

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
            firebaseRecyclerAdapter.startListening();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}