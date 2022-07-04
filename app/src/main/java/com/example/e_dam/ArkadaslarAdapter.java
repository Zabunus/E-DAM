package com.example.e_dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ArkadaslarAdapter extends RecyclerView.Adapter<ArkadaslarAdapter.ViewHolder>  {


    private List<Arkadaslar> arkadaslarList;
    private Context context;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    public ArkadaslarAdapter(Context context, List<Arkadaslar> arkadaslarList){
        this.arkadaslarList = arkadaslarList;
        this.context = context;
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
        Arkadaslar arkadaslar = arkadaslarList.get(position);
        holder.kullaniciAdi.setText(arkadaslar.getKullaniciAdi());
        holder.kullaniciLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //builder.setTitle(arkadaslar.getKullaniciAdi());
                TextView baslik = new TextView(context);
                baslik.setText(arkadaslar.getKullaniciAdi());
                baslik.setBackgroundColor(Color.WHITE);
                baslik.setPadding(10, 10, 10, 10);
                baslik.setGravity(Gravity.CENTER);
                baslik.setTextColor(Color.BLACK);
                baslik.setTextSize(20);
                builder.setCustomTitle(baslik);
                final TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText("Arkadaşlıktan Çıkar");
                textView.setTextSize(18);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        builder2.setTitle("Emin misin?");
                        TextView textView2 = new TextView(context);
                        textView2.setText(arkadaslar.getKullaniciAdi()+ " kişisiniz arkadaşlıktan çıkarmak istedinize emin misiniz?");
                        builder2.setView(textView2);
                        builder2.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference(Veritabani.arkadaslar).child(firebaseUser.getUid()).child(arkadaslar.getId()).removeValue();
                            }
                        });
                        builder2.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder2.show();
                    }
                });
                builder.setView(textView);
                builder.show();
                return true;
            }
        });
        holder.kullaniciLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MesajActivity.class);
                intent.putExtra("kullaniciAdi", arkadaslar.getKullaniciAdi());
                intent.putExtra("id", arkadaslar.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arkadaslarList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout kullaniciLayout;
        private final TextView kullaniciAdi;

        public ViewHolder(View view) {
            super(view);
            kullaniciLayout = view.findViewById(R.id.kullaniciLayout);
            kullaniciAdi = view.findViewById(R.id.kullaniciAdi);
        }
    }
}
