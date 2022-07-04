package com.example.e_dam;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class arkadasFragment extends Fragment {

    RecyclerView recyclerView;
    List<Arkadaslar> arkadaslarList;
    DatabaseReference arkadaslarReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public arkadasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arkadas, container, false);
        recyclerView  = view.findViewById(R.id.rwArkadaslar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arkadaslarList = new ArrayList<>();
        arkadaslarReference = FirebaseDatabase.getInstance().getReference(Veritabani.arkadaslar).child(user.getUid());
        arkadaslarReference.addValueEventListener(arkadaslarEvent);
        return view;
    }
    ValueEventListener arkadaslarEvent = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            arkadaslarList.clear();
            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                Arkadaslar arkadaslar = snapshot1.getValue(Arkadaslar.class);
                arkadaslarList.add(arkadaslar);
            }
            Collections.sort(arkadaslarList);
            ArkadaslarAdapter arkadaslarAdapter = new ArkadaslarAdapter(getContext(), arkadaslarList);
            recyclerView.setAdapter(arkadaslarAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}