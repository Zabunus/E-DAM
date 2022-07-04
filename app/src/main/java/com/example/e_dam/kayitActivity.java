package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class kayitActivity extends AppCompatActivity {

   private Toolbar actionbarkayit;

   private EditText txtkulalniciadi,txtemail,txtsifre;
   private Button btnKayit;

   private FirebaseAuth auth;


    public void init(){


        auth= FirebaseAuth.getInstance();
        actionbarkayit= (Toolbar) findViewById(R.id.toolbarkayit);
        setSupportActionBar(actionbarkayit);
        getSupportActionBar().setTitle("YENİ HESAP OLUŞTUR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtkulalniciadi=(EditText) findViewById(R.id.txtkullaniciK);
        txtemail= (EditText) findViewById(R.id.txtmailK);
        txtsifre= (EditText) findViewById(R.id.txtsifreK);
        btnKayit =(Button) findViewById(R.id.btnkayitK);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        init();
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yenihespolustur();
            }
        });
    }
    private void yenihespolustur() {
        String kullaniciadi = txtkulalniciadi.getText().toString();
        String email =txtemail.getText().toString();
        String sifre=txtsifre.getText().toString();


        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this,"EMAİL ALANI BOŞ OLAMAZ",Toast.LENGTH_LONG).show();



        } else if (TextUtils.isEmpty(sifre)) {

            Toast.makeText(this,"ŞİFRE ALANI BOŞ OLAMAZ",Toast.LENGTH_LONG).show();

        } else  if(kullaniciadi.length() < 3){
            Toast.makeText(this,"KULLANICI ADI EN AZ 3 KARAKTER OLMALI.",Toast.LENGTH_LONG).show();
        }

        else {

            auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(task.getResult().getUser().getUid());
                        Map<String, Object> kullaniciMap = new HashMap<>();

                        // Add some vehicles.
                        kullaniciMap.put("id", task.getResult().getUser().getUid());
                        kullaniciMap.put("email", email);
                        kullaniciMap.put("kullaniciAdi", kullaniciadi.toLowerCase());
                        kullaniciMap.put("cevrimici", false);
                        kullaniciMap.put("sonGorulme", System.currentTimeMillis());
                        databaseReference.updateChildren(kullaniciMap);
                        Toast.makeText(kayitActivity.this,"BAŞARILI",Toast.LENGTH_LONG).show();
                        Intent girisintent = new Intent(kayitActivity.this, GirisActivity.class);
                        startActivity(girisintent);
                        finish();

                    }
                    else {

                        Toast.makeText(kayitActivity.this,"BİR HATA OLUŞTU",Toast.LENGTH_LONG).show();

                    }



                }
            });

        }

    }
}