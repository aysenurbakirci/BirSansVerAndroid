package com.example.birsansverapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class IlanlarimActivity extends AppCompatActivity implements IlanlarimRecyclerAdapter.IlanlarimOnClickListener {

    ArrayList<String> hastaneList;
    ArrayList<String> ilanBilgisiList;
    ArrayList<String> ilanIDList;
    IlanlarimRecyclerAdapter ilanlarimRecyclerAdapter;
    RecyclerView ilanListesiIlanlarim;
    String koleksiyonAdi;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarim);

        hastaneList = new ArrayList<>();
        ilanBilgisiList = new ArrayList<>();
        ilanIDList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        IlanBilgileriniAl( "KanIlanlari");
        IlanBilgileriniAl( "IlacIlanlari");
        IlanBilgileriniAl( "IlikIlanlari");
        IlanBilgileriniAl( "MalzemeIlanlari");

        ilanListesiIlanlarim = findViewById(R.id.ilanlarimListesi);
        ilanListesiIlanlarim.setLayoutManager(new LinearLayoutManager(this));
        ilanlarimRecyclerAdapter = new IlanlarimRecyclerAdapter(ilanBilgisiList,hastaneList, ilanIDList, this);
        ilanListesiIlanlarim.setAdapter(ilanlarimRecyclerAdapter);

    }

    public void IlanBilgileriniAl(String koleksiyon){

        koleksiyonAdi = koleksiyon;

        FirebaseUser kullaniciBilgileri = firebaseAuth.getCurrentUser();
        String kullaniciMail = kullaniciBilgileri.getEmail();

        firebaseFirestore.collection(koleksiyon).whereEqualTo("PaylasanKisi",kullaniciMail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(IlanlarimActivity.this, "HATA", Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> alinanBilgiler = snapshot.getData();

                        String hastane = (String) alinanBilgiler.get("Hastane");
                        String ilanbilgisi = (String) alinanBilgiler.get("IlanBilgisi");
                        String dokumanID = (String) snapshot.getId();

                        hastaneList.add(hastane);
                        ilanBilgisiList.add(ilanbilgisi);
                        ilanIDList.add(dokumanID);

                        ilanlarimRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void clickListItem(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(IlanlarimActivity.this);
        builder.setTitle("İlanınızı kaldırmak isiyor musunuz?");
        builder.setMessage("İlanınızı kaldırmak için SİL butonunu kullanabilirsiniz.");
        builder.setNeutralButton("VAZGEÇ",null);
        builder.setNegativeButton("SİL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VerileriSil(koleksiyonAdi,ilanIDList.get(position));
            }
        });
        builder.show();

    }

    public void VerileriSil(String koleksiyon, String dokumanID){

        firebaseFirestore.collection(koleksiyon).document(dokumanID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(IlanlarimActivity.this, "Silme işlemi başarıyla gerçekleştirilmiştir.", Toast.LENGTH_LONG).show();
                IlanBilgileriniAl(koleksiyonAdi);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(IlanlarimActivity.this, "Silme işlemi gerçekleştirilememiştir. Lütfen tekrar deneyiniz.", Toast.LENGTH_LONG).show();
            }
        });

    }
}