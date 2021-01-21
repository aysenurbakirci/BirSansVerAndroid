package com.example.birsansverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IlanBilgileriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_bilgileri);

        TextView adsoyad = findViewById(R.id.ilanAyrintiAdSoyad);
        TextView telefon = findViewById(R.id.ilanAyrintiTelefon);
        TextView il = findViewById(R.id.ilanAyrintiIl);
        TextView hastane = findViewById(R.id.ilanAyrintiHastane);
        TextView ilanTipi = findViewById(R.id.ilanAyrintiIlanTipi);
        TextView ilanBilgileri = findViewById(R.id.ilanAyrintiIlanBilgisi);
        Button ara = findViewById(R.id.btnKisiyiAra);
        Button konumuGor = findViewById(R.id.btnKonumuGor);

        adsoyad.setText(IlanAyrintisiBilgileri.ilanAyrintilari.adsoyad);
        telefon.setText(IlanAyrintisiBilgileri.ilanAyrintilari.telefon);
        il.setText(IlanAyrintisiBilgileri.ilanAyrintilari.il);
        hastane.setText(IlanAyrintisiBilgileri.ilanAyrintilari.hastane);
        ilanTipi.setText(IlanAyrintisiBilgileri.ilanAyrintilari.ilantipi);
        ilanBilgileri.setText(IlanAyrintisiBilgileri.ilanAyrintilari.ilanbilgileri);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + IlanAyrintisiBilgileri.ilanAyrintilari.telefon));
                startActivity(intent);

            }
        });

        konumuGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + IlanAyrintisiBilgileri.ilanAyrintilari.hastane);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}