package com.example.trabalho1_progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class CadastroCurso extends AppCompatActivity {
    static boolean active = false;
    private EditText edtNomeCurso, edtQtdHoras;
    private Button btnVariavel;
    Curso curso, alteraCurso;
    DBHelper dbHelper;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);

        edtNomeCurso = findViewById(R.id.edtNomeCurso);
        edtQtdHoras = findViewById(R.id.edtQtdHoras);
        btnVariavel = findViewById(R.id.btnVariavelCurso);

        Intent it = getIntent();
        alteraCurso = (Curso)it.getSerializableExtra("ch_curso");
        curso = new Curso();
        dbHelper = new DBHelper(CadastroCurso.this);

        if(alteraCurso != null) {
            btnVariavel.setText("ALTERAR");
            edtNomeCurso.setText(alteraCurso.getNomeCurso());
            edtQtdHoras.setText(alteraCurso.getQtdeHoras());
            curso.setCursoId(alteraCurso.getCursoId());
        }else{
            btnVariavel.setText("INSERIR");


        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCurso = edtNomeCurso.getText().toString();
                String qdtHoras = edtQtdHoras.getText().toString();
                Random gerador = new Random();
                int cursoId = gerador.nextInt();

                Log.i("RYAN", String.valueOf(cursoId));
                curso.setNomeCurso(nomeCurso);
                curso.setQtdeHoras(Integer.parseInt(qdtHoras));
                curso.setCursoId(cursoId);
                if(btnVariavel.getText().toString().equals("INSERIR")) {
                    retornoDB = dbHelper.insereCurso(curso);
//                    Log.i("idAluno", String.valueOf(aluno.getAlunoId()));
                    if (retornoDB == -1) {
                        Toast.makeText(CadastroCurso.this, "Erro as cadastrar curso", Toast.LENGTH_LONG).show();

                    }else{
                        CadastroAluno cadastroAluno = null;
                        cadastroAluno.adapterSpinner.notifyDataSetChanged();
                        Toast.makeText(CadastroCurso.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                    }
                } else { /// atualizar
                    dbHelper.atualizarCurso(curso);
                    dbHelper.close();}
                finish();
                }
            });
        }
    }
    public void onStart() {
        super.onStart();
        active = true;
    }
    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}