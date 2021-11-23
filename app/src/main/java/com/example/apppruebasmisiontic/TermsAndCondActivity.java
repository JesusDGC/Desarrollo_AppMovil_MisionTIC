package com.example.apppruebasmisiontic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TermsAndCondActivity extends AppCompatActivity {

    private TextView txt_terms;
    private Button btn_accept;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);

        txt_terms = findViewById(R.id.txt_terms);
        btn_accept = findViewById(R.id.btn_accept);

        //Desabilito el boton de registrar
        btn_accept.setEnabled(false);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                //Una sobrecarga
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        txt_terms.setMovementMethod(new ScrollingMovementMethod());

        //Mas informacion en: https://www.develou.com/scrollview-en-android/
        txt_terms.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
                if (!view.canScrollVertically(1)){
                    btn_accept.setEnabled(true);
                }
                else
                    btn_accept.setEnabled(false);
            }
        });
    }
}