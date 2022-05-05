package com.example.e_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnGiris, btnKayit;

    public void init(){
        btnGiris= findViewById(R.id.giris);
        btnKayit=findViewById(R.id.kayit);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giris =new Intent(MainActivity.this,GirisActivity.class);
                startActivity(giris);
                finish();
            }
        });

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kayit =new Intent(MainActivity.this, kayitActivity.class);
                startActivity(kayit);
                finish();
            }
        });
    }
}