package com.example.e_dam;

import android.content.Context;
import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Ozellikler {
    public static String TarihFormati = "dd/MM/yyyy";
    public static String SaatFormati = "HH:mm";
    public static String MesajTarihiBul(Context context, long tarih, boolean saatiGoster){
        String tarihStr = DateFormat.format(TarihFormati, tarih).toString();
        String bugunTarih = DateFormat.format(TarihFormati, System.currentTimeMillis()).toString();
        String dunTarih = DateFormat.format(TarihFormati, System.currentTimeMillis() - (24 * 60 * 60 * 1000)).toString();
        String saat = DateFormat.format(SaatFormati, tarih).toString();
        if (tarihStr.equals(bugunTarih)){
            if (saatiGoster){
                return "Bugün, " + saat;
            }else{
                return "Bugün";
            }
        }else if (tarihStr.equals(dunTarih)){
            if (saatiGoster){
                return "Dün, "+saat;
            }else{
                return "Dün";
            }
        }else{
            return tarihStr;
        }
    }
    public static void cevrimiciDurumunuDegistir(boolean durum){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){

            FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(firebaseUser.getUid()).child("cevrimici").setValue(durum);
            FirebaseDatabase.getInstance().getReference(Veritabani.kullanicilar).child(firebaseUser.getUid()).child("sonGorulme").setValue(System.currentTimeMillis());

        }
    }
}
