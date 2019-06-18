package com.example.yeniprojem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SeceneklerActivity extends AppCompatActivity {

    TextView txt_cikisYap, txt_ayarlar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secenekler);

        txt_cikisYap = findViewById(R.id.txt_cikisyap_seceneklerActivity);
        txt_ayarlar = findViewById(R.id.txt_ayarlar_seceneklerActivity);

        //Toolbar ayarlamaları
        Toolbar toolbar = findViewById(R.id.toolbar_seceneklerActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Secenekler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SeceneklerActivity.this,MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

    }
}
