package com.amersur.amersurapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amersur.amersurapp.MainActivity;
import com.amersur.amersurapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrarUsuario extends Fragment {

    EditText Nombres, Apellidos, Celular, Correo, Password;
    Button Registrar;

    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_usuario, container, false);

        auth = FirebaseAuth.getInstance();

        Nombres = view.findViewById(R.id.Nombre_registro);
        Apellidos = view.findViewById(R.id.Apellido_registro);
        Celular = view.findViewById(R.id.NumTelf_registro);
        Correo = view.findViewById(R.id.Correo_registro);
        Password = view.findViewById(R.id.Password_registro);
        Registrar = view.findViewById(R.id.Registrar_cuenta_btn);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();
                String nombre = Nombres.getText().toString();
                String apellido = Apellidos.getText().toString();
                String telefono = Celular.getText().toString();

                if (correo.equals("") || pass.equals("") || nombre.equals("") ||
                        apellido.equals("") || telefono.equals("")) {
                    Toast.makeText(getActivity(), "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    if(!apellido.matches("^[A-Za-zÁ-Úá-úüÜñÑ\\s'-]+$")){
                        Apellidos.setError("Formato incorrecto");
                        Apellidos.setFocusable(true);
                    } else if(!nombre.matches("^[A-Za-zÁ-Úá-úüÜñÑ\\s'-]+$")){
                        Nombres.setError("Formato incorrecto");
                        Nombres.setFocusable(true);
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        Correo.setError("Dirección de correo inválida.");
                        Correo.setFocusable(true);
                    } else if (pass.length() < 6) {
                        Password.setError("La contraseña tener 6 o más carácteres.");
                        Correo.setFocusable(true);
                    } else {
                        RegistrarNuevoUsuario(correo, pass);
                    }
                }
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere, por favor.");
        progressDialog.setCancelable(false);

        return view;
    }

    private void RegistrarNuevoUsuario(String correo, String pass) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Si se creó la cuenta exitosamente
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();

                            String UID = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Password.getText().toString();
                            String nombre = Nombres.getText().toString();
                            String apellido = Apellidos.getText().toString();
                            String telefono = Celular.getText().toString();

                            HashMap<Object, Object> Usuario = new HashMap<>();

                            Usuario.put("UID", UID);
                            Usuario.put("CORREO", correo);
                            Usuario.put("PASSWORD", pass);
                            Usuario.put("NOMBRES", nombre);
                            Usuario.put("APELLIDOS", apellido);
                            Usuario.put("TELEFONO", telefono);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("USUARIOS");
                            reference.child(UID).setValue(Usuario);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            Toast.makeText(getActivity(), "Registro exitoso.", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            //Agregar funcion para iniciar sesion con los mismos credenciales...
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}