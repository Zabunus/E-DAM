package com.example.e_dam;

public class Kullanicilar {
    public String id;
    public String email;
    public String kullaniciAdi;
    public boolean cevrimici;
    public long sonGorulme;

    public Kullanicilar(String id, String email, String kullaniciAdi, boolean cevrimici, long sonGorulme) {
        this.id = id;
        this.email = email;
        this.kullaniciAdi = kullaniciAdi;
        this.cevrimici = cevrimici;
        this.sonGorulme = sonGorulme;
    }

    public Kullanicilar() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public boolean isCevrimici() {
        return cevrimici;
    }

    public void setCevrimici(boolean cevrimici) {
        this.cevrimici = cevrimici;
    }

    public long getSonGorulme() {
        return sonGorulme;
    }

    public void setSonGorulme(long sonGorulme) {
        this.sonGorulme = sonGorulme;
    }
}
