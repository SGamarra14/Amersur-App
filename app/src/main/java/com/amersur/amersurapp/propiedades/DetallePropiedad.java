package com.amersur.amersurapp.propiedades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amersur.amersurapp.Models.Solicitud;
import com.amersur.amersurapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetallePropiedad extends AppCompatActivity {

    ImageView det_img_propiedad;

    Button mas_informacion;

    TextView det_nombre_propiedad, det_desc_propiedad, det_ubi_propiedad, det_estado_propiedad, det_area_propiedad, det_precio_propiedad;

    private FirebaseAuth firebaseAuth;
    private String uuid;
    private int idPro;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_propiedad);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            uuid = user.getUid();
            idPro = getIntent().getIntExtra("Id", 0);
        }else {

        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        det_nombre_propiedad = findViewById(R.id.det_nombre_propiedad);
        det_desc_propiedad = findViewById(R.id.det_desc_propiedad);
        det_ubi_propiedad = findViewById(R.id.det_ubi_propiedad);
        det_estado_propiedad = findViewById(R.id.det_estado_propiedad);
        det_area_propiedad = findViewById(R.id.det_area_propiedad);
        det_precio_propiedad = findViewById(R.id.det_precio_propiedad);

        det_img_propiedad = findViewById(R.id.det_img_propiedad);

        mas_informacion = findViewById(R.id.mas_informacion);

        String foto = getIntent().getStringExtra("Foto1");
        String nombre = getIntent().getStringExtra("Nombre");
        String descripcion = getIntent().getStringExtra("Descripcion");
        String ubicacion = getIntent().getStringExtra("Ubicacion");
        int estado = getIntent().getIntExtra("Estado", 0);
        String area = getIntent().getStringExtra("Area");
        double precio = getIntent().getDoubleExtra("Precio", 0);

        try {
            Picasso.get().load(foto).placeholder(R.drawable.imageplaceholder).into(det_img_propiedad);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.imageplaceholder).into(det_img_propiedad);
        }

        det_nombre_propiedad.setText(nombre);
        det_desc_propiedad.setText(descripcion);
        det_ubi_propiedad.setText(ubicacion);
        switch (estado) {
            case 1:
                det_estado_propiedad.setText("En venta");
                break;
            case 2:
                det_estado_propiedad.setText("En preventa");
                break;
            default:
                det_estado_propiedad.setText("No especifica");
        }
        det_area_propiedad.setText(area + " m²");
        det_precio_propiedad.setText("S/. "+ precio + " x m²");

        mas_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaActual = obtenerFechaActual();

                Query userQuery = databaseReference.child("SOLICITUDES")
                        .orderByChild("uiduser")
                        .equalTo(uuid);

                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String uidUserActual = childSnapshot.child("uiduser").getValue(String.class);
                                int uidProActual = childSnapshot.child("uidpro").getValue(Integer.class);
                                String fechaActualHijo = childSnapshot.child("fecha").getValue(String.class);

                                if (uidUserActual.equals(uuid) && uidProActual == idPro && fechaActualHijo.equals(fechaActual)) {
                                    Toast.makeText(DetallePropiedad.this, "Ya has registrado una solicitud.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        registrarSolicitud(uuid, idPro);
                        Toast.makeText(DetallePropiedad.this, "Se ha registrado la solicitud.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    private void registrarSolicitud(String iduser, int idpro) {

        DatabaseReference solicitudRef = databaseReference.child("SOLICITUDES").push();

        String uidSolicitud = solicitudRef.getKey();

        Solicitud solicitud = new Solicitud(uidSolicitud, iduser, idpro, obtenerFechaActual(), 1);

        solicitudRef.setValue(solicitud);
    }

    private String obtenerFechaActual() {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        return sdf.format(date);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}