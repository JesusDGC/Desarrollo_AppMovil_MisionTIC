package com.example.apppruebasmisiontic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppruebasmisiontic.ui.util.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText edt_username;
    private EditText edt_password;
    private TextView txt_forgot;
    private TextView txt_register;

    private SharedPreferences myPreference;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        txt_forgot = findViewById(R.id.txt_forgot);
        txt_register = findViewById(R.id.txt_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
        txt_register.setOnClickListener(this);

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return;
        }

        //Se utilizaba SharedPreferences para guardar el usuario logeado, sera cambiado por firebase
        /*
        //El modo es para asignar si solo mi aplicación puede acceder a mi archivo xml o todas las apps puedan usarlo
        //myPreference = getSharedPreferences(Constantes.PREFERENCE,MODE_PRIVATE);

        //String usuario = myPreference.getString("email","");

        if(!usuario.equals("")){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return;
        }*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                String email = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                Log.e("EDT_CORREO", email);
                Log.e("EDT_CONTRASENA", password);

                if (email.equals("") || password.equals("")){
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    log_in(email,password);
                }
                /*
                else if (email.equals("admin@admin.co") && password.equals("admin")) {
                    //Toast.makeText(this, getString(R.string.txt_click_login), Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = myPreference.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);

                    //Debe confirmar los cambios en el sharedPreferences para que se vean reflejados
                    editor.commit();

                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("user",email);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Error iniciando sesión", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.txt_register:
                Log.e("CLICK_REGISTER", "CLICK_REGISTER");
                Toast.makeText(this, getString(R.string.txt_click_register), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_forgot:
                Log.e("CLICK_FORGOT_PASSWORD", "CLICK_FORGOT_PASSWORD");
                break;
        }
    }

    public void log_in(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("provider", "BASIC");
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            edt_username.setText("");
                            edt_password.setText("");
                        }
                    }
                });
    }
}