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

public class GirisActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText eposta = findViewById(R.id.edtTxtEpostaGiris);
        EditText sifre = findViewById(R.id.edtTxtSifreGiris);
        Button btnGiris = findViewById(R.id.btnGirisYap);

        sifre.setTransformationMethod(new PasswordTransformationMethod());

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String epostatxt = eposta.getText().toString();
                String sifretxt = sifre.getText().toString();

                Boolean kontrol = VeriKontrol(epostatxt,sifretxt);

                if (kontrol == true){

                    firebaseAuth.signInWithEmailAndPassword(epostatxt,sifretxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent girisyap = new Intent(GirisActivity.this, UygulamaMenuActivity.class);
                            startActivity(girisyap);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GirisActivity.this,"Giriş yapılırken hata oluştu. Lütfen bilgilerinizi kontrol ederek tekrar deneyiniz.", Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    Toast.makeText(GirisActivity.this,"Boş alan bıraktınız. Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Boolean VeriKontrol(String eposta, String sifre){
        if (eposta.matches("")){
            return false;
        }else if (sifre.matches("")){
            return false;
        }else{
            return true;
        }
    }
}