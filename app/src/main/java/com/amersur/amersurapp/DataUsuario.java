package com.amersur.amersurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amersur.amersurapp.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataUsuario extends AppCompatActivity {
    private EditText nameEt,lastNameEt,emailEt,phoneEt;
    private Button btnGuardar;

    private DatabaseReference databaseReference;
    private User user;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_usuario);

        nameEt = findViewById(R.id.editTextNombres);
        lastNameEt = findViewById(R.id.editTextApellidos);
        emailEt = findViewById(R.id.editTextEmail);
        phoneEt = findViewById(R.id.editTextPhone);

        user = (User) getIntent().getSerializableExtra("USUARIO_EXTRA");

        if (user != null) {
            nameEt.setText(user.getNOMBRES());
            lastNameEt.setText(user.getAPELLIDOS());
            emailEt.setText(user.getCORREO());
            phoneEt.setText(user.getTELEFONO());
            uuid = user.getUID();
        }

        btnGuardar = findViewById(R.id.btnSave);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guardar los cambios
                guardarCambios();
            }
        });


        // Habilita el botón de regresar en la barra de acción
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void guardarCambios() {
        // Obtener los nuevos datos de los EditTexts
        String nuevoNombre = nameEt.getText().toString();
        String nuevoApellido = lastNameEt.getText().toString();
        String nuevoCorreo = emailEt.getText().toString();
        String nuevoTelefono = phoneEt.getText().toString();

        // Actualizar los datos en el objeto Usuario
        user.setNOMBRES(nuevoNombre);
        user.setAPELLIDOS(nuevoApellido);
        user.setCORREO(nuevoCorreo);
        user.setTELEFONO(nuevoTelefono);

        // Aquí deberías guardar los cambios en Firebase, por ejemplo, mediante un DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("USUARIOS").child(user.getUID()).setValue(user);
        finish();
        // Mostrar un mensaje de éxito o manejar el resultado según sea necesario
        Toast.makeText(DataUsuario.this, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        // Manejar el evento de clic en el botón de retroceso
        onBackPressed();
        return true;
    }
}