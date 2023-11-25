package com.amersur.amersurapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amersur.amersurapp.ChangePwd;
import com.amersur.amersurapp.DataUsuario;
import com.amersur.amersurapp.Models.User;
import com.amersur.amersurapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GestionarCuenta extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView nameTv,lastnameTv,emailTv,phoneTv;
    private String uuid,pwd;
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

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        nameTv = view.findViewById(R.id.txtname);
        lastnameTv = view.findViewById(R.id.txtlastname);
        emailTv = view.findViewById(R.id.txtemail);
        phoneTv = view.findViewById(R.id.txtphone);

        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("USUARIOS").child(user.getUid());

            obtenerDatosUsuario();
        }

        Button btnModificar = view.findViewById(R.id.btnModificar);
        Button btnModificarPwd = view.findViewById(R.id.btnChgPwd);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataUsuario.class);

                String name = nameTv.getText().toString();
                String lastname = lastnameTv.getText().toString();
                String email = emailTv.getText().toString();
                String phone = phoneTv.getText().toString();
                User usuario = new User(uuid,email,"",lastname,phone,name);

                intent.putExtra("USUARIO_EXTRA", usuario);

                startActivity(intent);
            }
        });

        btnModificarPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes iniciar la nueva actividad
                Intent intent = new Intent(getActivity(), ChangePwd.class);

                String email = emailTv.getText().toString();
                User usuario = new User(uuid,email,pwd,"","","");

                intent.putExtra("USUARIO_SECRET", usuario);

                startActivity(intent);
            }
        });
    }

    private void obtenerDatosUsuario() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User usuario = dataSnapshot.getValue(User.class);

                    mostrarDatosEnTextView(usuario);
                } else {
                    Log.d("TAG", "No se encontró el usuario con el ID especificado.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error al obtener el usuario", databaseError.toException());
            }
        });
    }

    private void mostrarDatosEnTextView(User usuario) {
        if (usuario != null) {
            nameTv.setText(usuario.getNOMBRES());
            lastnameTv.setText(usuario.getAPELLIDOS());
            emailTv.setText(usuario.getCORREO());
            phoneTv.setText(usuario.getTELEFONO());
            uuid = usuario.getUID();
            pwd = usuario.getPASSWORD();
        }else {
            Log.d("TAG", "El objeto usuario es nulo");
        }
    }


}