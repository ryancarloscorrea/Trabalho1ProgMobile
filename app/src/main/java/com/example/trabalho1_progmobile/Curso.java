package com.example.trabalho1_progmobile;

import java.io.Serializable;

public class Curso implements Serializable {


    private int cursoId; // pk
    private String nomeCurso;
    private int qtdeHoras;


    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getQtdeHoras() {
        return qtdeHoras;
    }

    public void setQtdeHoras(int qtdeHoras) {
        this.qtdeHoras = qtdeHoras;
    }
    @Override
    public String toString() {

        return " id: " + String.valueOf(cursoId) + " Nome: " + nomeCurso.toString() + " Qdt Horas: " + String.valueOf(qtdeHoras);
    }


}
