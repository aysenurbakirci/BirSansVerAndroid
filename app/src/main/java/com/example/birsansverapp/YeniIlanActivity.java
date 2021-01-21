package com.example.birsansverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class YeniIlanActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_ilan);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        EditText adsoyad = findViewById(R.id.ilanPaylasAdSoyad);
        EditText telefon = findViewById(R.id.ilanPaylasTelefon);
        EditText il = findViewById(R.id.ilanPaylasIl);
        EditText hastane = findViewById(R.id.ilanPaylasHastane);
        EditText ilanBilgisi = findViewById(R.id.ilanPaylasIlanBilgisi);
        Button paylas = findViewById(R.id.btnYeniIlanPaylas);
        RadioGroup radiogroup = findViewById(R.id.ilanTipiSecPaylas);

        paylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser kullaniciBilgileri = firebaseAuth.getCurrentUser();
                String kullaniciMail = kullaniciBilgileri.getEmail();

                int secilenIlanTipi = radiogroup.getCheckedRadioButtonId();
                RadioButton ilanTip = findViewById(secilenIlanTipi);
                String ilanTipBaslik = ilanTip.getText().toString();

                Boolean kontrol = VeriKontrol(adsoyad.getText().toString(),telefon.getText().toString(),il.getText().toString(),hastane.getText().toString(),ilanBilgisi.getText().toString());

                if (kontrol){

                    HashMap<String, Object> ilanBilgileriListesi = new HashMap<>();
                    ilanBilgileriListesi.put("PaylasanKisi", kullaniciMail);
                    ilanBilgileriListesi.put("AdSoyad", adsoyad.getText().toString());
                    ilanBilgileriListesi.put("Telefon", telefon.getText().toString());
                    ilanBilgileriListesi.put("IlBilgisi", il.getText().toString());
                    ilanBilgileriListesi.put("Hastane", hastane.getText().toString());
                    ilanBilgileriListesi.put("IlanTipi", ilanTip.getText().toString());
                    ilanBilgileriListesi.put("IlanBilgisi",ilanBilgisi.getText().toString());
                    ilanBilgileriListesi.put("Tarih", FieldValue.serverTimestamp());

                    if (ilanTipBaslik.equals("Kan İlanı")){
                        IlanPaylasFonksiyonu("KanIlanlari",ilanBilgileriListesi);
                    }else if (ilanTipBaslik.equals("İlaç İlanı")){
                        IlanPaylasFonksiyonu("IlacIlanlari",ilanBilgileriListesi);
                    }else if (ilanTipBaslik.equals("İlik İlanı")){
                        IlanPaylasFonksiyonu("IlikIlanlari",ilanBilgileriListesi);
                    }else if (ilanTipBaslik.equals("Malzeme İlanı")){
                        IlanPaylasFonksiyonu("MalzemeIlanlari",ilanBilgileriListesi);
                    }else {
                        Toast.makeText(YeniIlanActivity.this, "İLAN PAYLAŞILAMADI. Lütfen tekrar deneyiniz. ", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }

    public void IlanPaylasFonksiyonu(String koleksiyonAdi, HashMap ilanBilgiListesi){
        firebaseFirestore.collection(koleksiyonAdi).add(ilanBilgiListesi).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(YeniIlanActivity.this,"İLAN PAYLAŞILDI. En kısa sürede sağlığınıza kavuşmanız dileğiyle. Sağlıklı Günler.", Toast.LENGTH_LONG).show();

                Intent geriDon = new Intent(YeniIlanActivity.this, UygulamaMenuActivity.class);
                startActivity(geriDon);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(YeniIlanActivity.this, "İLAN PAYLAŞILAMADI. Lütfen tekrar deneyiniz.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Boolean VeriKontrol(String adsoyad, String telefon, String il, String hastane, String bilgi){
        if (adsoyad.matches("")){
            return false;
        }else if (telefon.matches("")){
            return false;
        }else if (il.matches("")){
            return false;
        }else if (hastane.matches("")){
            return false;
        }else if (bilgi.matches("")){
            return false;
        }else{
            return true;
        }
    }
}