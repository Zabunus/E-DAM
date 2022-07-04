package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MesajActivity extends AppCompatActivity {

    private Intent intent;
    private RecyclerView mesajlarRW;
    private DatabaseReference databaseReference;
    private DatabaseReference kullaniciRef;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private List<Mesaj> mesajList;
    private EditText MesajEdt;
    private ImageButton gonderBtn;
    private Toolbar toolbar;

    private String id;
    private TextView sonGorulme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        String kullaniciAdi = intent.getStringExtra("kullaniciAdi");
        id = intent.getStringExtra("id");
        TextView kAdiBaslik = findViewById(R.id.kAdiBaslik);
        kAdiBaslik.setText(kullaniciAdi);
        MesajEdt = findViewById(R.id.MesajEdt);
        gonderBtn = findViewById(R.id.gonderBtn);
        mesajlarRW = findViewById(R.id.mesajlarRW);
        mesajlarRW.setHasFixedSize(true);
        sonGorulme = findViewById(R.id.sonGorulme);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        //Mesajın başlama konumu tru is alttan false ise yukarıdan başlar
        linearLayoutManager.setStackFromEnd(true);

        mesajlarRW.setLayoutManager(linearLayoutManager);
        mesajList = new ArrayList<>();

        //Kullanıcı Bilgileri
        kullaniciRef = FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(id);
        kullaniciRef.keepSynced(true);
        kullaniciRef.addValueEventListener(kullaniciValueEventListener);


        databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.mesajlar).child(firebaseUser.getUid()).child(id);
        databaseReference.addValueEventListener(valueEventListener);

        gonderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesajGonder();
            }
        });
    }

    private void mesajGonder() {
        String mesaj = MesajEdt.getText().toString();
        if(mesaj.isEmpty()){
            Toast.makeText(this, "Mesajınız boş olamaz!", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseReference mesajGonderRef1 = FirebaseDatabase.getInstance().getReference(Veritabani.mesajlar).child(firebaseUser.getUid()).child(id);
            DatabaseReference mesajEkleRef = mesajGonderRef1.push();
            Map<String, Object> mesajMap = new HashMap<>();
            mesajMap.put("key", mesajEkleRef.getKey());
            mesajMap.put("mesaj", mesaj);
            mesajMap.put("tarih", System.currentTimeMillis());
            mesajMap.put("gonderen", true);
            mesajEkleRef.updateChildren(mesajMap);
            DatabaseReference mesajGonderRef2 = FirebaseDatabase.getInstance().getReference(Veritabani.mesajlar).child(id).child(firebaseUser.getUid()).child(mesajEkleRef.getKey());

            mesajMap.put("gonderen", false);
            mesajGonderRef2.updateChildren(mesajMap);
            MesajEdt.setText("");
        }
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            mesajList.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                Mesaj mesaj = dataSnapshot.getValue(Mesaj.class);
                mesajList.add(mesaj);
            }
            MesajAdapter mesajAdapter = new MesajAdapter(MesajActivity.this,mesajList);
            mesajlarRW.setAdapter(mesajAdapter);
            mesajlarRW.scrollToPosition(mesajList.size() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private ValueEventListener kullaniciValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Kullanicilar kullanicilar = snapshot.getValue(Kullanicilar.class);
            if(kullanicilar.cevrimici){
                sonGorulme.setText("Çevrimiçi");
            }else{
                sonGorulme.setText(Ozellikler.MesajTarihiBul(MesajActivity.this, kullanicilar.getSonGorulme(), true));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
        kullaniciRef.removeEventListener(kullaniciValueEventListener);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Ozellikler.cevrimiciDurumunuDegistir(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Ozellikler.cevrimiciDurumunuDegistir(true);
    }
}