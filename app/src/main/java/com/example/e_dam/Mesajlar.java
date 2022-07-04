package com.example.e_dam;

public class Mesajlar implements Comparable<Mesajlar>{
    private String kullaniciAdi;
    private String id;
    private Mesaj mesaj;

    public Mesajlar() {
    }

    public Mesajlar(String kullaniciAdi, String id, Mesaj mesaj) {
        this.kullaniciAdi = kullaniciAdi;
        this.id = id;
        this.mesaj = mesaj;
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

    public Mesaj getMesaj() {
        return mesaj;
    }

    public void setMesaj(Mesaj mesaj) {
        this.mesaj = mesaj;
    }

    @Override
    public int compareTo(Mesajlar mesajlar) {
        if (mesaj.getTarih() > mesajlar.getMesaj().getTarih()){
            return 1;
        }
        else if (mesaj.getTarih() < mesajlar.getMesaj().getTarih()) {
            return -1;
        }
        else {
            return 0;
        }

    }
}
