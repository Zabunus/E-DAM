package com.example.e_dam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsteklerAdapter extends RecyclerView.Adapter<IsteklerAdapter.ViewHolder>  {


    private List<Istekler> isteklerList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public IsteklerAdapter(List<Istekler> isteklerList){
        this.isteklerList = isteklerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kullanici_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Istekler istekler = isteklerList.get(position);
        holder.kullaniciAdi.setText(istekler.getKullaniciAdi());
        holder.onaylaBtn.setVisibility(View.VISIBLE);
        holder.reddetBtn.setVisibility(View.VISIBLE);
        holder.onaylaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.istekler).child(user.getUid()).child(istekler.getKey());
                databaseReference.removeValue();

                Map<String, Object> istekMap = new HashMap<>();

                // Add some vehicles.
                istekMap.put("id", istekler.getId());
                istekMap.put("kullaniciAdi", istekler.getKullaniciAdi());

                DatabaseReference arkadasEkle = FirebaseDatabase.getInstance().getReference(Veritabani.arkadaslar).child(user.getUid()).child(istekler.getId());

                arkadasEkle.updateChildren(istekMap);

                FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Kullanicilar kullanicilar = snapshot.getValue(Kullanicilar.class);

                        Map<String, Object> istekMap2 = new HashMap<>();

                        // Add some vehicles.
                        istekMap2.put("id", kullanicilar.getId());
                        istekMap2.put("kullaniciAdi", kullanicilar.getKullaniciAdi());
                        DatabaseReference arkadasEkle2 = FirebaseDatabase.getInstance().getReference(Veritabani.arkadaslar).child(istekler.getId()).child(user.getUid());

                        arkadasEkle2.updateChildren(istekMap2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.reddetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Veritabani.istekler).child(user.getUid()).child(istekler.getKey());
                databaseReference.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return isteklerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView kullaniciAdi;
        private final Button onaylaBtn;
        private final Button reddetBtn;

        public ViewHolder(View view) {
            super(view);
            kullaniciAdi = view.findViewById(R.id.kullaniciAdi);
            onaylaBtn = view.findViewById(R.id.onaylaBtn);
            reddetBtn = view.findViewById(R.id.reddetBtn);
        }
    }
}
