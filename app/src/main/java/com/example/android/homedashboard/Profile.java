package com.example.android.homedashboard;

public class Profile {
    private String Gejala;
    private String ID_Gejala;
    private String Ket_Solusi;


    public Profile() {
    }

    public Profile(String gejala, String ID_Gejala, String ket_Solusi) {
        Gejala = gejala;
        this.ID_Gejala = ID_Gejala;
        Ket_Solusi = ket_Solusi;
    }

    public String getGejala() {
        return Gejala;
    }

    public void setGejala(String gejala) {
        Gejala = gejala;
    }

    public String getID_Gejala() {
        return ID_Gejala;
    }

    public void setID_Gejala(String ID_Gejala) {
        this.ID_Gejala = ID_Gejala;
    }

    public String getKet_Solusi() {
        return Ket_Solusi;
    }

    public void setKet_Solusi(String ket_Solusi) {
        Ket_Solusi = ket_Solusi;
    }
}
