package com.example.e_dam;

public class Istekler {

    public String id;
    public String key;
    public String kullaniciAdi;

    public Istekler(String id, String key, String kullaniciAdi) {
        this.id = id;
        this.key = key;
        this.kullaniciAdi = kullaniciAdi;
    }

    public Istekler() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
