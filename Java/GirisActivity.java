package com.example.yeniprojem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {

    EditText edt_Email_Giris,edt_sifre_Giris;

    Button btn_giris_Yap;

    TextView txt_kayitSayfasina_git;

    FirebaseAuth girisYetkisi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);


        edt_Email_Giris = findViewById(R.id.edt_Email_Giris);
        edt_sifre_Giris = findViewById(R.id.edt_Sifre_Giris);


        btn_giris_Yap = findViewById(R.id.btn_Giris_Activity);

        girisYetkisi = FirebaseAuth.getInstance();

        txt_kayitSayfasina_git = findViewById(R.id.txt_kayitSayfasina_git);

        txt_kayitSayfasina_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GirisActivity.this,KayitOlActivity.class));

            }
        });

        btn_giris_Yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pgGiris = new ProgressDialog(GirisActivity.this);
                pgGiris.setMessage("Giriş Yapılıyor...");
                pgGiris.show();

                String str_emailGiris = edt_Email_Giris.getText().toString();
                String str_sifreGiris = edt_sifre_Giris.getText().toString();

                if (TextUtils.isEmpty(str_emailGiris) || TextUtils.isEmpty(str_sifreGiris)){
                    Toast.makeText(GirisActivity.this, "Bütün alanları doldurun..", Toast.LENGTH_LONG).show();
                }

                else {
                    //Giriş yapma kodları

                    girisYetkisi.signInWithEmailAndPassword(str_emailGiris,str_sifreGiris)
                            .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        DatabaseReference yolGiris = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(girisYetkisi.getCurrentUser().getUid());
                                        yolGiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                pgGiris.dismiss();

                                                Intent intent = new Intent(GirisActivity.this,AnaSayfaActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                pgGiris.dismiss();

                                            }
                                        });
                                    }

                                    else {

                                        pgGiris.dismiss();
                                        Toast.makeText(GirisActivity.this, "Giriş başarısız oldu...", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                }



            }
        });

    }
}
