package com.amersur.amersurapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amersur.amersurapp.fragments.RegistrarUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IniciarSesion extends AppCompatActivity {

    Button CrearCuenta, IniciaSesion;
    EditText Correo, Password;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        /*
        ------barra superior en pantalla para mostrar titulo y retroceder-----
        se tiene que copiar el metodo onSupportNavigateUp que esta abajo
        ActionBar actionBar = getSupportActionBar();    //crea action bar
        assert actionBar != null;
        actionBar.setTitle("inicio sesion");
        actionBar.setDisplayHomeAsUpEnabled(true);      //para que se pueda retroceder
        actionBar.setDisplayShowHomeEnabled(true);*/

        Correo = findViewById(R.id.Correo_Inicio_Sesion);
        Password = findViewById(R.id.Password_Inicio_Sesion);
        IniciaSesion = findViewById(R.id.Inicio_Sesion_btn);
        CrearCuenta = findViewById(R.id.Ir_Crear_Cuenta_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(IniciarSesion.this);
        progressDialog.setMessage("Iniciando sesión, espere por favor.");
        progressDialog.setCancelable(false);

        IniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();

                if (correo.equals("") || pass.equals("")) {
                    Toast.makeText(IniciarSesion.this, "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                            Correo.setError("Dirección de correo inválida.");
                            Correo.setFocusable(true);
                        } else if (pass.length() < 6) {
                            Password.setError("La contraseña debe tener 6 o más caracteres.");
                            Correo.setFocusable(true);
                        } else {
                            // Hashea el password ingresado antes de iniciar sesión
                            String passwordIngresadoHash = hashPassword(pass);
                            IniciarSesionUsuario(correo, passwordIngresadoHash);
                        }
                    }
                }
        });

        CrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                RegistrarUsuario fragmentRegistrarUsuario = new RegistrarUsuario();
                fragmentTransaction.replace(R.id.fragment_container_IniciarSesion, fragmentRegistrarUsuario);

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
    }

    private void IniciarSesionUsuario(String correo, String pass) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(IniciarSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                            Toast.makeText(IniciarSesion.this, "Bienvenido.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            UsuarioInvalido();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        UsuarioInvalido();
                    }
                });
    }

    private void UsuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IniciarSesion.this);
        builder.setCancelable(false);
        builder.setTitle("Ha ocurrido un error.");
        builder.setMessage("Verifique si el correo o contraseña son correctos")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /*Agrega la funcionalidad al boton de retroceder del action bar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }*/

    private String hashPassword(String password) {
        try {
            // Utilizar SHA-256 para el hash (puedes elegir otro algoritmo si lo prefieres)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convertir el array de bytes a una representación hexadecimal
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ""; // Manejar la excepción de manera apropiada en tu aplicación
        }
    }
}