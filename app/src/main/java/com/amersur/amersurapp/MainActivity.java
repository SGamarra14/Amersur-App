package com.amersur.amersurapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.amersur.amersurapp.fragments.GestionarCuenta;
import com.amersur.amersurapp.fragments.Inicio;
import com.amersur.amersurapp.fragments.ListSolicitudes;
import com.amersur.amersurapp.fragments.RegistrarUsuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Inicio()).commit();
            navigationView.setCheckedItem(R.id.Inicio);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Inicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Inicio()).commit();
                break;

            case R.id.GestionarCuenta:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GestionarCuenta()).commit();
                break;

            case R.id.MisSolicitudes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListSolicitudes()).commit();
                break;
                
            case R.id.CerrarSesion:
                CerrarSesion();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void ComprobandoInicioSesion() {
        if (user!=null) {
            
            //Toast.makeText(this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
            
        } else {
            //si no ha iniciado sesion entonces se abre el main cliente, en este caso deberia abrir activity iniciar sesion con fragmente registrar usuario
            startActivity(new Intent(MainActivity.this, IniciarSesion.class));
            finish();
        }
    }

    private void CerrarSesion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, IniciarSesion.class));
        finish();
        Toast.makeText(this, "Cerraste sesión exitosamente.", Toast.LENGTH_SHORT).show();
    }

    //Es lo primero en ejecutarse. Tmb puede colocarse en el onCreate
    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
}