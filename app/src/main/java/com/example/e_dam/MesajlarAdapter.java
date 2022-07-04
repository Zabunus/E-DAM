package com.example.e_dam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MesajlarAdapter extends RecyclerView.Adapter<MesajlarAdapter.ViewHolder>  {

    private Context context;
    private List<Mesajlar> mesajList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private int MESAJ_TURU_GONDEREN = 0;
    private int MESAJ_TURU_ALAN = 1;

    public MesajlarAdapter(Context context, List<Mesajlar> mesajList){
        this.context = context;
        this.mesajList = mesajList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kullanici_layout, parent, false);
        return new MesajlarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mesajlar mesaj = mesajList.get(position);
        holder.kullaniciAdi.setText(mesaj.getKullaniciAdi());
        holder.sonMesaj.setText(mesaj.getMesaj().getMesaj());
        holder.sonMesaj.setVisibility(View.VISIBLE);
        holder.kullaniciLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MesajActivity.class);
                intent.putExtra("kullaniciAdi", mesaj.getKullaniciAdi());
                intent.putExtra("id", mesaj.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesajList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout kullaniciLayout;
        private final TextView kullaniciAdi;
        private final TextView sonMesaj;

        public ViewHolder(View view) {
            super(view);
            kullaniciLayout = view.findViewById(R.id.kullaniciLayout);
            kullaniciAdi = view.findViewById(R.id.kullaniciAdi);
            sonMesaj = view.findViewById(R.id.sonMesaj);
        }
    }
}


