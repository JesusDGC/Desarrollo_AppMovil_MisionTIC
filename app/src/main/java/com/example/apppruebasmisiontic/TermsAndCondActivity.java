package com.example.apppruebasmisiontic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TermsAndCondActivity extends AppCompatActivity {

    private TextView txt_terms;
    private Button btn_accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);

        txt_terms = findViewById(R.id.txt_terms);
        btn_accept = findViewById(R.id.btn_accept);

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
    }
}