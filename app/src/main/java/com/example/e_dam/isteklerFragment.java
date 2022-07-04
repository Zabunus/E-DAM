package com.example.e_dam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class isteklerFragment extends Fragment {


    List<Istekler> isteklerList;

    RecyclerView rwIstekler;
    ImageButton ekleBtn;

    DatabaseReference isteklerReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userid = "";

    public isteklerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_istekler, container, false);
        rwIstekler = view.findViewById(R.id.rwIstekler);
        ekleBtn = view.findViewById(R.id.ekleBtn);
        ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Arkadaşi Ekle");
                final EditText input = new EditText(getContext());
                input.setHint("kullanıcı adı girin");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Bir kullanıcı adı girin!", Toast.LENGTH_LONG).show();
                        }else{
                            Query query = FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).orderByChild("kullaniciAdi").equalTo(input.getText().toString().toLowerCase()).limitToFirst(1);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for (DataSnapshot snapshot1: snapshot.getChildren()){

                                            Kullanicilar kullanicilar = snapshot1.getValue(Kullanicilar.class);
                                            if (kullanicilar.getId().equals(user.getUid())){

                                                Toast.makeText(getContext(), "Kendinize istek gönderemezsiniz", Toast.LENGTH_LONG).show();
                                            }else{
                                                FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Kullanicilar kullanicilar2 = snapshot.getValue(Kullanicilar.class);

                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.istekler).child(kullanicilar.getId()).push();

                                                        Map<String, Object> arkadasEkleMap = new HashMap<>();
                                                        arkadasEkleMap.put("id", user.getUid());
                                                        arkadasEkleMap.put("key", databaseReference.getKey());
                                                        arkadasEkleMap.put("kullaniciAdi", kullanicilar2.getKullaniciAdi());
                                                        databaseReference.updateChildren(arkadasEkleMap);
                                                        Toast.makeText(getContext(), "İstek Gönderildi", Toast.LENGTH_LONG).show();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }
                                        }

                                    }else{
                                        Toast.makeText(getContext(), "Kullanıcı Bulunamadı", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getContext(), "Bir hata oluştu.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                });
                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        if(user == null){
            userid = "test";
        }else {
            userid = user.getUid();
        }
        rwIstekler.setHasFixedSize(true);
        rwIstekler.setLayoutManager(new LinearLayoutManager(getActivity()));
        isteklerList = new ArrayList<>();
        isteklerReference = FirebaseDatabase.getInstance().getReference(Veritabani.istekler).child(userid);
        isteklerReference.addValueEventListener(isteklerEvent);
        return view;
    }

    ValueEventListener isteklerEvent = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            isteklerList.clear();
            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                Istekler istekler = snapshot1.getValue(Istekler.class);
                isteklerList.add(istekler);
            }
            IsteklerAdapter isteklerAdapter = new IsteklerAdapter(isteklerList);
            rwIstekler.setAdapter(isteklerAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}