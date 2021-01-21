package com.example.birsansverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText eposta = findViewById(R.id.edtTxtEpostaKayit);
        EditText sifre = findViewById(R.id.edtTxtSifreKayit);
        EditText sifreTekrar = findViewById(R.id.edtTxtSifreTekrarKayit);
        Button kayitol = findViewById(R.id.btnKayitOl);

        sifre.setTransformationMethod(new PasswordTransformationMethod());
        sifreTekrar.setTransformationMethod(new PasswordTransformationMethod());

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String epostatxt = eposta.getText().toString();
                String sifretxt = sifre.getText().toString();
                String sifretekrartxt = sifreTekrar.getText().toString();

                Boolean kontrol = VeriKontrol(epostatxt, sifretxt,sifretekrartxt);

                if (kontrol){

                    firebaseAuth.createUserWithEmailAndPassword(epostatxt,sifretxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(KayitActivity.this, UygulamaMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(KayitActivity.this, "Kaydınız yapılırken hata oluştu. Tekrar deneyiniz. (Bağlantı hatası veya daha önce kullanılan mail.)", Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Toast.makeText(KayitActivity.this, "Kaydınız yapılırken hata oluştu. Tekrar deneyiniz. (Bilgilerinizi eksik veya hatalı.)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Boolean VeriKontrol(String eposta, String sifre, String sifretekrar){
        if (eposta.matches("")){
            return false;
        }else if (sifre.matches("")){
            return false;
        }else if (sifretekrar.matches("")){
            return false;
        }else{
            return true;
        }
    }
}