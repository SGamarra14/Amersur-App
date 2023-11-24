package com.amersur.amersurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class ChangePwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        // Habilita el botón de regresar en la barra de acción
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja los eventos de clic en la barra de acción
        switch (item.getItemId()) {
            case android.R.id.home:
                // Acción a realizar cuando se hace clic en el botón de regresar
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}