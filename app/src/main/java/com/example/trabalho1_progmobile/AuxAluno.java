package com.example.trabalho1_progmobile;

public class AuxAluno {
    private String nomeAluno, nomeCurso;

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    @Override
    public String toString() {
        return "Aluno: " + nomeAluno.toString() + "\nNome curso: " + nomeCurso.toString();

    }
}
