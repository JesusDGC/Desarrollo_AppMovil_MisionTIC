package com.example.apppruebasmisiontic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TermsAndCondActivity extends AppCompatActivity {

    private TextView txt_terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_cond);

        txt_terms = findViewById(R.id.txt_terms);
        txt_terms.setMovementMethod(new ScrollingMovementMethod());
    }
}