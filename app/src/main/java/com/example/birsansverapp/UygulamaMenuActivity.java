package com.example.birsansverapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UygulamaMenuActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_menu);

        firebaseAuth = FirebaseAuth.getInstance();

        Button kan = findViewById(R.id.btnKanIlan);
        Button ilac = findViewById(R.id.btnIlacIlan);
        Button ilik = findViewById(R.id.btnIlikIlan);
        Button malzeme = findViewById(R.id.btnMalzemeIlan);
        Button yeniIlan = findViewById(R.id.btnYeniIlan);
        Button ilanlarim = findViewById(R.id.btnIlanlarim);
        Button cikis = findViewById(R.id.btnCikisYap);

        kan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilanlar = new Intent(UygulamaMenuActivity.this, IlanlarActivity.class);
                ilanlar.putExtra("KoleksiyonAdi", "KanIlanlari");
                startActivity(ilanlar);
            }
        });

        ilac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilanlar = new Intent(UygulamaMenuActivity.this, IlanlarActivity.class);
                ilanlar.putExtra("KoleksiyonAdi", "IlacIlanlari");
                startActivity(ilanlar);
            }
        });

        ilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilanlar = new Intent(UygulamaMenuActivity.this, IlanlarActivity.class);
                ilanlar.putExtra("KoleksiyonAdi", "IlikIlanlari");
                startActivity(ilanlar);
            }
        });

        malzeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilanlar = new Intent(UygulamaMenuActivity.this, IlanlarActivity.class);
                ilanlar.putExtra("KoleksiyonAdi", "MalzemeIlanlari");
                startActivity(ilanlar);
            }
        });

        yeniIlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeniIlan = new Intent(UygulamaMenuActivity.this, YeniIlanActivity.class);
                startActivity(yeniIlan);
            }
        });

        ilanlarim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilanlarim = new Intent(UygulamaMenuActivity.this, IlanlarimActivity.class);
                startActivity(ilanlarim);
            }
        });

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder cikisAlert = new AlertDialog.Builder(UygulamaMenuActivity.this);
                cikisAlert.setTitle("ÇIKIŞ");
                cikisAlert.setMessage("Çıkış yapmak istediğinize emin misiniz?");
                cikisAlert.setNeutralButton("Vazgeç", null);
                cikisAlert.setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent cikis = new Intent(UygulamaMenuActivity.this, MainActivity.class);
                        startActivity(cikis);
                        finish();
                    }
                });

                cikisAlert.create().show();
            }
        });
    }
}