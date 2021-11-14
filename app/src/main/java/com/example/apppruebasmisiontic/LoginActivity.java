package com.example.apppruebasmisiontic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText edt_username;
    private EditText edt_password;
    private TextView txt_forgot;
    private TextView txt_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        txt_forgot = findViewById(R.id.txt_forgot);
        txt_register = findViewById(R.id.txt_register);

        btn_login.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
        txt_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                String correo = edt_username.getText().toString();
                String contrasena = edt_password.getText().toString();
                Log.e("EDT_CORREO", correo);
                Log.e("EDT_CONTRASENA", contrasena);

                if (correo.equals("") || contrasena.equals("")){
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (correo.equals("admin@admin.co") && contrasena.equals("admin")) {
                    Toast.makeText(this, getString(R.string.txt_click_login), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("user",correo);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error iniciando sesión", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_register:
                Log.e("CLICK_REGISTER", "CLICK_REGISTER");
                Toast.makeText(this, getString(R.string.txt_click_register), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_forgot:
                Log.e("CLICK_FORGOT_PASSWORD", "CLICK_FORGOT_PASSWORD");
                Toast.makeText(this, "change password", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}