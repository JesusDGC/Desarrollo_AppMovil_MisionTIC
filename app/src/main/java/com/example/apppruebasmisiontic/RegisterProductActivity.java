package com.example.apppruebasmisiontic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterProductActivity extends AppCompatActivity {

    //private Button btn_take_photo;
    private Button btn_cancel;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICK_SAVE", "CLICK_SAVE");
                Intent resultIntent = new Intent();
                //Una sobrecarga
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICK_CANCEL", "CLICK_CANCEL");
                Intent resultIntent = new Intent();
                //Una sobrecarga
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}