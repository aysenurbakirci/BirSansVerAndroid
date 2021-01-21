package com.example.birsansverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            Intent gitKayitliGiris = new Intent(MainActivity.this, UygulamaMenuActivity.class);
            startActivity(gitKayitliGiris);
            finish();
        }

        Button girisYapEkran = findViewById(R.id.btnGirisYapEkran);
        Button kayitOlEkran = findViewById(R.id.btnKayıtOlEkran);
        TextView sifremiUnuttum = findViewById(R.id.sifremiUnuttum);
        TextView nasilKullanilir = findViewById(R.id.nasilKullanilir);

        girisYapEkran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gitGirisEkran = new Intent(MainActivity.this, GirisActivity.class);
                startActivity(gitGirisEkran);
            }
        });

        kayitOlEkran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gitKayitEkran = new Intent(MainActivity.this, KayitActivity.class);
                startActivity(gitKayitEkran);
            }
        });

        sifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

        nasilKullanilir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Nasıl Kullanılır?");
                builder.setMessage("Uygulama hastanede bulunan kan, ilik, ilaç ve malzeme gibi ihtiyaçları bulunan hastaların bu ihtiyaçlarını kolayca insanlara duyurabilmesini sağlar. Bu uygulama sayesinde hastanede bulunan insanlar istedikleri kategoriyi belirterek bunu sağlayabilecek insanların kendileriyle iletişime geçmesini sağlar.");
                builder.setNegativeButton("Tamam", null);
                builder.show();

            }
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Şifremi Unuttum.");

        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailEt = new EditText(this);
        emailEt.setHint("Email Adresiniz");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10, 10, 10, 10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Link Gönder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        });

        //button cancel
        builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    private void beginRecovery(String email) {

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Mail Gönderildi!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Mailiniz gönderilirken hata oluştu. Lütfen tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Mailiniz gönderilirken hata oluştu. Lütfen tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}