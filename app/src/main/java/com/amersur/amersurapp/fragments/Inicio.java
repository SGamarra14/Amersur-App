package com.amersur.amersurapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amersur.amersurapp.Propiedades;
import com.amersur.amersurapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inicio extends Fragment {

    RecyclerView recyclervirePropiedades;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    LinearLayoutManager linearLayoutManager;

   // FirebaseRecyclerAdapter<Propiedades, >   revisar video 107 / 2:57, necesito hacer el view holder primero

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        return view;
    }
}