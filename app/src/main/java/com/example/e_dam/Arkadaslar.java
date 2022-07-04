package com.example.e_dam;

public class Arkadaslar implements Comparable<Arkadaslar> {

    public String id;
    public String kullaniciAdi;

    public Arkadaslar(String id, String kullaniciAdi) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
    }

    public Arkadaslar() {
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

    @Override
    public int compareTo(Arkadaslar arkadaslar) {
        return kullaniciAdi.compareToIgnoreCase(arkadaslar.getKullaniciAdi());
    }
}
