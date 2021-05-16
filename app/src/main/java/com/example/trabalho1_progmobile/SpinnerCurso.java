package com.example.trabalho1_progmobile;

public class SpinnerCurso {

    private String nomeCurso;
    private int idCurso;

    public SpinnerCurso(int idCurso, String nomeCurso) {
        this.nomeCurso = nomeCurso;
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
    @Override
    public String toString(){
        return nomeCurso;
    }
}
