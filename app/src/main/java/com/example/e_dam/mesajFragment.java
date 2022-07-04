package com.example.e_dam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mesajFragment extends Fragment {
    private List<Mesajlar> mesajList;
    private RecyclerView mesajlarRW;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;

    MesajlarAdapter mesajlarAdapter;

    public mesajFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mesaj, container, false);
        mesajlarRW = view.findViewById(R.id.mesajlarRW);
        mesajlarRW.setHasFixedSize(true);
        mesajlarRW.setLayoutManager(new LinearLayoutManager(getActivity()));
        mesajList = new ArrayList<>();
        mesajlarAdapter = new MesajlarAdapter(getActivity(), mesajList);
        databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.mesajlar).child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mesajList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    List<Mesaj> mesaj = new ArrayList<>();
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Mesaj mesaj1 = dataSnapshot1.getValue(Mesaj.class);
                        mesaj.add(mesaj1);
                    }
                    mesajList.add(new Mesajlar("", dataSnapshot.getKey(), mesaj.get(mesaj.size()-1)));
                }
                for(int i = 0; i < mesajList.size(); i++){
                    Mesajlar mesajlar = mesajList.get(i);
                    int finalI = i;
                    FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(mesajlar.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Kullanicilar kullanicilar = snapshot.getValue(Kullanicilar.class);
                            mesajlar.setKullaniciAdi(kullanicilar.kullaniciAdi);
                            mesajList.set(finalI, mesajlar);

                            mesajlarAdapter.notifyItemChanged(finalI);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                mesajlarAdapter.notifyDataSetChanged();
                mesajlarRW.setAdapter(mesajlarAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}