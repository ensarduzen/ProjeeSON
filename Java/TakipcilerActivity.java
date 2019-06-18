package com.example.yeniprojem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.yeniprojem.Adapter.KullaniciAdapter;
import com.example.yeniprojem.Model.Kullanici;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TakipcilerActivity extends AppCompatActivity {

    String id;
    String baslik;

    List<String> idListesi;

    RecyclerView recyclerView;
    KullaniciAdapter kullaniciAdapter;
    List<Kullanici> kullaniciListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takipciler);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        baslik = intent.getStringExtra("baslik");

        //Toolbar ayarları
        Toolbar toolbar = findViewById(R.id.toolbar_takipcilerActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(baslik);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Recycler ayarları
        recyclerView = findViewById(R.id.recycler_view_takipcilerActivity);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        kullaniciListesi = new ArrayList<>();
        kullaniciAdapter = new KullaniciAdapter(this,kullaniciListesi,false);
        recyclerView.setAdapter(kullaniciAdapter);

        idListesi = new ArrayList<>();

        switch (baslik){

            case "begeniler":
                begenileriAl();
                break;
            case "takip edilenler":
                takipEdilenleriAl();
                break;
            case "takipciler":
                takipcileriAl();
                break;

        }

    }

    private void begenileriAl() {

        DatabaseReference begeniYolu = FirebaseDatabase.getInstance().getReference("Begeniler").child(id);

        begeniYolu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idListesi.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    idListesi.add(snapshot.getKey());

                }
                kullanicilariGoster();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void takipEdilenleriAl() {

        DatabaseReference takipEdilenlerYolu = FirebaseDatabase.getInstance().getReference("Takip").child(id).child("takipEdilenler");

        takipEdilenlerYolu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idListesi.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    idListesi.add(snapshot.getKey());

                }
                kullanicilariGoster();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void takipcileriAl() {

        DatabaseReference takipciYolu = FirebaseDatabase.getInstance().getReference("Takip").child(id).child("takipciler");

        takipciYolu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idListesi.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    idListesi.add(snapshot.getKey());

                }
                kullanicilariGoster();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void kullanicilariGoster() {

        DatabaseReference kullaniciYolu = FirebaseDatabase.getInstance().getReference("Kullanicilar");

        kullaniciYolu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                kullaniciListesi.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Kullanici kullanici = snapshot.getValue(Kullanici.class);

                    for (String id : idListesi){

                        if (kullanici.getId().equals(id)){

                            kullaniciListesi.add(kullanici);

                        }

                    }

                }

                kullaniciAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
