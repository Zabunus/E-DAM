package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SifremiUnuttumActivity extends AppCompatActivity {

    private EditText SifreRstEdt;
    private Button sifremiUnuttumBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        SifreRstEdt = findViewById(R.id.SifreRstEdt);
        sifremiUnuttumBtn = findViewById(R.id.sifremiUnuttumBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("tr");
        sifremiUnuttumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SifreRstEdt.getText().toString().isEmpty()){
                    Toast.makeText(SifremiUnuttumActivity.this, "Eposta boş olamaz!", Toast.LENGTH_LONG).show();
                }else{
                        firebaseAuth.sendPasswordResetEmail(SifreRstEdt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SifremiUnuttumActivity.this, "Şifre sıfırlama bağlantısı eposta adresinize gönderildi.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(SifremiUnuttumActivity.this, "Bir hata oluştu. Eposta adresinizi kontrol edin.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });

    }
}