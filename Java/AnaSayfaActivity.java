package com.example.yeniprojem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.yeniprojem.Cerceve.AramaFragment;
import com.example.yeniprojem.Cerceve.BildirimFragment;
import com.example.yeniprojem.Cerceve.HomeFragment;
import com.example.yeniprojem.Cerceve.ProfilFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AnaSayfaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Fragment seciliCerceve = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemReselectedListener(navigationItemSelectedListener);


        Bundle intent = getIntent().getExtras();

        if (intent != null){

            String gonderen = intent.getString("gonderenId");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileId",gonderen);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new ProfilFragment()).commit();
        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new HomeFragment()).commit();
        }


    }

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){

                        case R.id.nav_home:

                            //Ana cerceveyi cagirsin
                            seciliCerceve = new HomeFragment();

                            break;

                        case R.id.nav_arama:

                            //Arama cercevesini cagirsin
                            seciliCerceve = new AramaFragment();

                            break;

                        case R.id.nav_ekle:

                            //Cerceve boş olsun GonderiActivity e gitsin
                            seciliCerceve = null;
                            startActivity(new Intent(AnaSayfaActivity.this,GonderiActivity.class));

                            break;

                        case R.id.nav_kalp:

                            //Bildirim cercevesini cagirsin
                            seciliCerceve = new BildirimFragment();

                            break;

                        case R.id.nav_profil:

                            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();

                            //Profil cercevesini cagirsin
                            seciliCerceve = new ProfilFragment();

                            break;
                    }

                    if (seciliCerceve != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,seciliCerceve).commit();
                    }




                }
            };

}
