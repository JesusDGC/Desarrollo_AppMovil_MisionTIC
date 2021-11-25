package com.example.apppruebasmisiontic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppruebasmisiontic.ui.util.Constantes;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppruebasmisiontic.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private SharedPreferences myPreference;
    private Activity miActividad;

    //private TextView name_user;
    //private TextView email_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        miActividad = this;
        myPreference = getSharedPreferences(Constantes.PREFERENCE, MODE_PRIVATE);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        /*binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(miActividad, "boton flotante pulsado", Toast.LENGTH_SHORT).show();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent intent = new Intent(HomeActivity.this, RegisterProductActivity.class);

                startActivity(intent);
                //startActivityForResult(intent,ACTIVIDAD_REGISTRAR_PRODUCTO);
                //Toast.makeText(miActividad, "boton flotante pulsado", Toast.LENGTH_SHORT).show();

            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_myshopping, R.id.nav_favorites)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //name_user = findViewById(R.id.name_user);
        //email_user = findViewById(R.id.email_user);

        //Para obtener los parametros enviados desde otra actividad
        Bundle bundle = getIntent().getExtras();
        //PARAMETROS ENVIADOS DESDE LOGIN
        if (bundle != null && bundle.getString("email") != null && bundle.getString("provider") != null){
            Log.e("USUARIO_MENU", bundle.getString("user"));
            SharedPreferences.Editor editor = myPreference.edit();
            editor.putString("email", bundle.getString("email"));
            editor.putString("provider", bundle.getString("provider"));
            Log.e("LOGIN_HOME", bundle.getString("email"));
            Log.e("LOGIN_HOME", bundle.getString("provider"));

            editor.commit();
            //email_user.setText(bundle.getString("email"));
        }
        //PARAMETROS ENVIADOS DESDE REGISTER
        if (bundle != null && bundle.getString("name") != null && bundle.getString("lastname") != null && bundle.getString("email") != null && bundle.getString("provider") != null){

            SharedPreferences.Editor editor = myPreference.edit();
            editor.putString("email", bundle.getString("email"));
            editor.putString("name_user", bundle.getString("name"));
            editor.putString("lastname_user", bundle.getString("lastname"));
            editor.putString("provider", bundle.getString("provider"));
            Log.e("REGISTER_HOME", bundle.getString("email"));
            Log.e("REGISTER_HOME", bundle.getString("name"));
            Log.e("REGISTER_HOME", bundle.getString("lastname"));
            Log.e("REGISTER_HOME", bundle.getString("provider"));

            //Debe confirmar los cambios en el sharedPreferences para que se vean reflejados
            editor.commit();
            //email_user.setText(bundle.getString("email"));
            //name_user.setText(bundle.getString("name") + bundle.getString("lastname"));
        }


        //email_user.setText("jedaga123@gmail.com");
        //name_user.setText("Jesus Garcia");

        //String usuario = myPreference.getString("email","No hay usuario");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}