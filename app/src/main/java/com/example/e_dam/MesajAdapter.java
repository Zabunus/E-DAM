package com.example.e_dam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder>  {

    private Context context;
    private List<Mesaj> mesajList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private int MESAJ_TURU_GONDEREN = 0;
    private int MESAJ_TURU_ALAN = 1;

    public MesajAdapter(Context context, List<Mesaj> mesajList){
        this.context = context;
        this.mesajList = mesajList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new ViewHolder(LayoutInflater.from(context).inflate(viewType == MESAJ_TURU_GONDEREN ? R.layout.layout_mesaj_sag : R.layout.layout_mesaj_sol, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mesaj mesaj = mesajList.get(position);
        holder.mesajText.setText(mesaj.getMesaj());
    }

    @Override
    public int getItemCount() {
        return mesajList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mesajText;
        private final TextView saat;

        public ViewHolder(View view) {
            super(view);
            mesajText = view.findViewById(R.id.mesajText);
            saat = view.findViewById(R.id.saat);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(mesajList.get(position).isGonderen()){
            return MESAJ_TURU_GONDEREN;
        }else {
            return MESAJ_TURU_ALAN;
        }
    }
}

