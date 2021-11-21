package com.example.apppruebasmisiontic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox chb_term_register;
    private TextView txt_term_register;
    private EditText edt_name_register;
    private EditText edt_lastname_register;
    private EditText edt_email_register;
    private EditText edt_password_register;
    private Button btn_register;
    private TextView txt_login_register;

    //final es para definir una constante
    private final int ACTIVIDAD_TERMINOS = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        chb_term_register = findViewById(R.id.chb_term_register);
        txt_term_register = findViewById(R.id.txt_term_register);
        edt_name_register = findViewById(R.id.edt_name_register);
        edt_lastname_register = findViewById(R.id.edt_lastname_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_password_register = findViewById(R.id.edt_password_register);
        btn_register = findViewById(R.id.btn_register);
        txt_login_register = findViewById(R.id.txt_login_register);

        //Desabilito el boton de registrar
        btn_register.setEnabled(false);

        btn_register.setOnClickListener(this);
        txt_login_register.setOnClickListener(this);
        txt_term_register.setOnClickListener(this);

        chb_term_register.setEnabled(false);


        chb_term_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                btn_register.setEnabled(isChecked);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String name = edt_name_register.getText().toString();
                String lastname = edt_lastname_register.getText().toString();
                String email = edt_email_register.getText().toString();
                String password = edt_password_register.getText().toString();
                Log.e("EDT_NAME", name);
                Log.e("EDT_NAME", lastname);
                Log.e("EDT_email", email);
                Log.e("EDT_PASSWORD", password);

                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.txt_login_register:
                Intent intent_login = new Intent(this, LoginActivity.class);
                startActivity(intent_login);
                break;

            case R.id.txt_term_register:
                Intent intent_ter = new Intent(this, TermsAndCondActivity.class);
                //Se lanza esta actividad esperando que nos devuelva un resultado
                startActivityForResult(intent_ter,ACTIVIDAD_TERMINOS);
                break;

        }
    }

    //Se utiliza para gestionar el resultado devuelto de una actividad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVIDAD_TERMINOS){
            if(resultCode == Activity.RESULT_OK){
                chb_term_register.setChecked(true);
                //Toast.makeText(this, "Acepte terminos y condiciones", Toast.LENGTH_SHORT).show();
            }
            else{
                chb_term_register.setChecked(false);
            }

        }

    }
}