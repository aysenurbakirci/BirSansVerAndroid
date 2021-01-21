package com.example.birsansverapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IlanlarActivity extends AppCompatActivity implements IlanListesiRecyclerAdapter.IlanListesiOnClickListener {

    ArrayList<String> kisiTelefonList;
    ArrayList<String> kisiAdSoyadList;
    ArrayList<String> ilList;
    ArrayList<String> hastaneList;
    ArrayList<String> ilanTipiList;
    ArrayList<String> ilanBilgisiList;

    IlanListesiRecyclerAdapter ilanListesiAdapter;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlar);

        Intent intent = getIntent();
        String secilenKoleksiyonAdi = intent.getStringExtra("KoleksiyonAdi");

        kisiTelefonList = new ArrayList<>();
        kisiAdSoyadList = new ArrayList<>();
        ilList = new ArrayList<>();
        hastaneList = new ArrayList<>();
        ilanTipiList = new ArrayList<>();
        ilanBilgisiList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();

        IlanBilgileriniAl(secilenKoleksiyonAdi);

        RecyclerView ilanListesiIlanlar = findViewById(R.id.ilanlarListesi);
        ilanListesiIlanlar.setLayoutManager(new LinearLayoutManager(this));
        ilanListesiAdapter = new IlanListesiRecyclerAdapter(kisiTelefonList,kisiAdSoyadList,ilList,hastaneList,ilanTipiList,ilanBilgisiList, this);
        ilanListesiIlanlar.setAdapter(ilanListesiAdapter);
    }

    public void IlanBilgileriniAl(String koleksiyonAdi){

        firebaseFirestore.collection(koleksiyonAdi).orderBy("Tarih", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(IlanlarActivity.this, "HATA", Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> alinanBilgiler = snapshot.getData();

                        String adsoyad = (String) alinanBilgiler.get("AdSoyad");
                        String telefon = (String) alinanBilgiler.get("Telefon");
                        String il = (String) alinanBilgiler.get("IlBilgisi");
                        String hastane = (String) alinanBilgiler.get("Hastane");
                        String ilantip = (String) alinanBilgiler.get("IlanTipi");
                        String ilanbilgisi = (String) alinanBilgiler.get("IlanBilgisi");

                        kisiAdSoyadList.add(adsoyad);
                        kisiTelefonList.add(telefon);
                        ilList.add(il);
                        hastaneList.add(hastane);
                        ilanTipiList.add(ilantip);
                        ilanBilgisiList.add(ilanbilgisi);

                        ilanListesiAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void clickListItem(int position) {

        IlanAyrintisiBilgileri.ilanAyrintilari.adsoyad = kisiAdSoyadList.get(position);
        IlanAyrintisiBilgileri.ilanAyrintilari.telefon = kisiTelefonList.get(position);
        IlanAyrintisiBilgileri.ilanAyrintilari.il = ilList.get(position);
        IlanAyrintisiBilgileri.ilanAyrintilari.hastane = hastaneList.get(position);
        IlanAyrintisiBilgileri.ilanAyrintilari.ilantipi = ilanTipiList.get(position);
        IlanAyrintisiBilgileri.ilanAyrintilari.ilanbilgileri = ilanBilgisiList.get(position);

        Intent ilanAyrinti = new Intent(IlanlarActivity.this, IlanBilgileriActivity.class);
        startActivity(ilanAyrinti);
    }
}