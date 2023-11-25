package com.amersur.amersurapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amersur.amersurapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ChangePwd extends AppCompatActivity {
    private User user;
    private Button btnGuardar;
    private EditText actualPwd,newPwd,reNewPwd;
    private String pwdUser,correo,uuid;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        actualPwd = findViewById(R.id.editTextLastPwd);
        newPwd = findViewById(R.id.editTextPwd);
        reNewPwd = findViewById(R.id.editTextRepeatPwd);
        btnGuardar = findViewById(R.id.btnSave);

        user = (User) getIntent().getSerializableExtra("USUARIO_SECRET");

        if (user != null) {
            pwdUser = user.getPASSWORD();
            uuid = user.getUID();
            correo = user.getCORREO();
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guardar los cambios
                //guardarCambios();

                String lastPwd = actualPwd.getText().toString();
                String pwd = newPwd.getText().toString();
                String rptPwd = reNewPwd.getText().toString();

                String lastHashed = hashPassword(lastPwd);
                String hashedPwd = hashPassword(pwd);

                if (!rptPwd.equals(pwd)) {
                    Toast.makeText(ChangePwd.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else{
                    if (!pwdUser.equals(lastHashed)) {
                        Toast.makeText(ChangePwd.this, "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                    }else{
                        reautenticarUsuario(correo,lastPwd);
                        actualizarContraseña(hashedPwd);
                        updateDatabasePassword(hashedPwd);
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void reautenticarUsuario(String correo, String passActual) {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(correo, passActual);

            usuario.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.v("TAG", "Usuario reautenticado.");
                    //Toast.makeText(ChangePwd.this, "Usuario reautenticado", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ChangePwd.this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarContraseña(String nuevaPass) {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            usuario.updatePassword(nuevaPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("CambiarContraseña", "Contraseña cambiada exitosamente.");
                       // Toast.makeText(ChangePwd.this, "Contraseña cambiada exitosamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("CambiarContraseña", "Error al cambiar la contraseña", task.getException());
                        Toast.makeText(ChangePwd.this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(ChangePwd.this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }

    }
    private void updateDatabasePassword(String hashedpwd) {
        // Crear un Map para almacenar los campos que deseas actualizar en la base de datos
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("PASSWORD", hashedpwd);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("USUARIOS").child(uuid).updateChildren(updateData);
            Toast.makeText(ChangePwd.this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ChangePwd.this, "Error al cambiar la contraseña en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja los eventos de clic en la barra de acción
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}