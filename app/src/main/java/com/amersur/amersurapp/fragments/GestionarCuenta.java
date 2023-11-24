package com.amersur.amersurapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amersur.amersurapp.ChangePwd;
import com.amersur.amersurapp.DataUsuario;
import com.amersur.amersurapp.R;

public class GestionarCuenta extends Fragment {

    public GestionarCuenta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestionar_cuenta, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnModificar = view.findViewById(R.id.btnModificar);
        Button btnModificarPwd = view.findViewById(R.id.btnChgPwd);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes iniciar la nueva actividad
                Intent intent = new Intent(getActivity(), DataUsuario.class);
                startActivity(intent);
            }
        });

        btnModificarPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes iniciar la nueva actividad
                Intent intent = new Intent(getActivity(), ChangePwd.class);
                startActivity(intent);
            }
        });
    }
}