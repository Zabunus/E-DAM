package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {


    private Toolbar action;
    private ViewPager vpMain;
    private TabLayout tabsMain;
    private tabsadapter tabsAdapter;

    private FirebaseUser aktifKullanici;
    private FirebaseAuth auth;
    public void init (){
        auth = FirebaseAuth.getInstance();
        aktifKullanici = auth.getCurrentUser();
        action=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(action);
        vpMain =(ViewPager) findViewById(R.id.vpMain);
        TextView kAdiBaslik = findViewById(R.id.kAdiBaslik);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        if (aktifKullanici != null){

            FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(aktifKullanici.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Kullanicilar kullanicilar = snapshot.getValue(Kullanicilar.class);
                    kAdiBaslik.setText(kullanicilar.kullaniciAdi);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        tabsAdapter =new tabsadapter(getSupportFragmentManager());
        vpMain.setAdapter(tabsAdapter);

        tabsMain=(TabLayout) findViewById(R.id.tabsMain);
        tabsMain.setupWithViewPager(vpMain);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }

    @Override
    protected void onStart() {

        if (aktifKullanici==null) {

            Intent girisintent = new Intent(MainActivity2.this,MainActivity.class);
            startActivity( girisintent);
            finish();

        }

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.manumain, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.maincikis) {

            auth.signOut();
            Intent girisintent = new Intent(MainActivity2.this,GirisActivity.class);
            startActivity(girisintent);
            finish();
        }

        return true;
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