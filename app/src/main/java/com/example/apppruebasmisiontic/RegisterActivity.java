package com.example.apppruebasmisiontic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.example.apppruebasmisiontic.util.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

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

    //Firebase
    private FirebaseAuth mAuth;



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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Desabilito el boton de registrar y el checkbox
        btn_register.setEnabled(false);
        chb_term_register.setEnabled(false);

        //Escucha de Click
        btn_register.setOnClickListener(this);
        txt_login_register.setOnClickListener(this);
        txt_term_register.setOnClickListener(this);

        //Escucha cada cambio del checkbox
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


                if (email.equals("") || password.equals("") || name.equals("") || lastname.equals("")){
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 8){
                    Toast.makeText(this, "Constraseña debe tener minimo 8 caracteres", Toast.LENGTH_SHORT).show();
                }
                else{
                    //registerUserFirebaseEmailAndPassword(email,password,name,lastname);
                    registroUsuarioFirestore(name, lastname, email, Utilidades.md5(password));
                }
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

    public void registroUsuarioFirestore(String nombre, String apellido, String correo, String contrasena) {
        // Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("apellido", apellido);
        usuario.put("correo", correo);
        usuario.put("contrasena", contrasena);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(correo)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    public void registerUserFirebaseEmailAndPassword(String email, String password,String name, String lastname){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("AUTH", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user) // Actualizar interfaz

                            //finalizo la actividad
                            finish();
                            //-------------------------------------------------------------------------------

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("SEND_EMAIL", "Email sent.");
                                            }
                                        }
                                    });


                            //Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            //intent.putExtra("email", email);
                            //intent.putExtra("name",name);
                            //intent.putExtra("lastname",lastname);
                            //intent.putExtra("provider", "BASIC");
                            //startActivity(intent);
                        }
                        else{
                            Log.e("AUTH", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            edt_email_register.setText("");
                            edt_password_register.setText("");
                        }
                    }
                });
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