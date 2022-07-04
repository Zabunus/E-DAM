package com.example.e_dam;

public class Mesaj {
    private String key;
    private String mesaj;
    private long tarih;
    private boolean gonderen;

    public Mesaj() {
    }

    public Mesaj(String key, String mesaj, long tarih, boolean gonderen) {
        this.key = key;
        this.mesaj = mesaj;
        this.tarih = tarih;
        this.gonderen = gonderen;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public long getTarih() {
        return tarih;
    }

    public void setTarih(long tarih) {
        this.tarih = tarih;
    }

    public boolean isGonderen() {
        return gonderen;
    }

    public void setGonderen(boolean gonderen) {
        this.gonderen = gonderen;
    }
}
