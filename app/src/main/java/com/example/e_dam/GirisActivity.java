package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.auth.FirebaseUser;

public class GirisActivity extends AppCompatActivity {

        private Toolbar actionbargiris;


        private EditText txtmail,txtsifre;
        private Button btnGiris;

        private FirebaseAuth auth;
        private FirebaseUser aktifkullanici;

        public void init(){

            auth= FirebaseAuth.getInstance();
            aktifkullanici=auth.getCurrentUser();

            actionbargiris=(Toolbar) findViewById(R.id.toolbargiris);
            setSupportActionBar(actionbargiris);
            getSupportActionBar().setTitle("GİRİŞ YAP");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            txtmail= (EditText) findViewById(R.id.mailgirisG);
            txtsifre =(EditText) findViewById(R.id.sifregirisG);

            btnGiris=(Button) findViewById(R.id.btnGirisG);




        }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        init();
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullaniciGiris();
            }
        });

    }

    private void kullaniciGiris() {

            String email=txtmail.getText().toString();
            String sifre=txtsifre.getText().toString();

            if (TextUtils.isEmpty(email)){

                Toast.makeText(this,"Mail boş olamaz",Toast.LENGTH_LONG).show();


            }else if (TextUtils.isEmpty(sifre)){

                Toast.makeText(this,"şifre boş olamaz",Toast.LENGTH_LONG).show();

            }else {
                auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(GirisActivity.this,"giriş başarılı",Toast.LENGTH_LONG).show();
                            Intent mainintent=new Intent(GirisActivity.this, MainActivity2.class);
                            startActivity(mainintent);
                            finish();
                        }else {
                            Toast.makeText(GirisActivity.this,"giriş başarısız",Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
    }
}