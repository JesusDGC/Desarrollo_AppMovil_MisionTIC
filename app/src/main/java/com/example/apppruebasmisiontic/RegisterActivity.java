package com.example.apppruebasmisiontic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private EditText edt_name_register;
    private EditText edt_lastname_register;
    private EditText edt_email_register;
    private EditText edt_password_register;
    private EditText edt_cel_register;
    private Button btn_register;
    private TextView txt_login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        chb_term_register = findViewById(R.id.chb_term_register);
        edt_name_register = findViewById(R.id.edt_name_register);
        edt_lastname_register = findViewById(R.id.edt_lastname_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_password_register = findViewById(R.id.edt_password_register);
        edt_cel_register = findViewById(R.id.edt_cel_register);
        btn_register = findViewById(R.id.btn_register);
        txt_login_register = findViewById(R.id.txt_login_register);

        //Desabilito el boton de registrar
        btn_register.setEnabled(false);

        btn_register.setOnClickListener(this);
        txt_login_register.setOnClickListener(this);

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
                String celPhone = edt_cel_register.getText().toString();
                Log.e("EDT_NAME", name);
                Log.e("EDT_NAME", lastname);
                Log.e("EDT_email", email);
                Log.e("EDT_PASSWORD", password);
                Log.e("CELPHONE", celPhone);

                break;
            case R.id.txt_login_register:
                Log.e("CLICK_LOGIN_REGISTER", "CLICK_LOGIN_REGISTER");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}