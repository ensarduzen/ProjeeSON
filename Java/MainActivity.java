package com.example.yeniprojem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btn_baslangicGiris;
    Button btn_baslangisKayitOl;

    FirebaseUser baslangicKullanici;

    @Override
    protected void onStart() {
        super.onStart();

        baslangicKullanici = FirebaseAuth.getInstance().getCurrentUser();

        //Eğer veritabanında varsa direkt anasayfaya gönder

        if (baslangicKullanici != null){

            startActivity(new Intent(MainActivity.this,AnaSayfaActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_baslangicGiris=findViewById(R.id.btn_Giris);
        btn_baslangisKayitOl=findViewById(R.id.btn_KayitOl);

        btn_baslangicGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GirisActivity.class));
            }
        });

        btn_baslangisKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KayitOlActivity.class));
            }
        });

    }
}
