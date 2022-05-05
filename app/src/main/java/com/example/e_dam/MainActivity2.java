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

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        getSupportActionBar().setTitle(R.string.app_name);
        vpMain =(ViewPager) findViewById(R.id.vpMain);
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
}