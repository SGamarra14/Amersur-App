package com.amersur.amersurapp.propiedades;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amersur.amersurapp.R;
import com.squareup.picasso.Picasso;

public class DetallePropiedad extends AppCompatActivity {

    ImageView det_img_propiedad;

    Button mas_informacion;

    TextView det_nombre_propiedad, det_desc_propiedad, det_ubi_propiedad, det_estado_propiedad, det_area_propiedad, det_precio_propiedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_propiedad);

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
                // implementar solicitar mas informacion
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}