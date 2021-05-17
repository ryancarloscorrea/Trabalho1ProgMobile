package com.example.trabalho1_progmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class CadastroAluno extends AppCompatActivity {
    private EditText edtNome, edtEmail, edtTelefone, edtCpf;
    private CoordinatorLayout coordinatorLayout;
    private Button btnVariavel;
    private Spinner spinnerCurso;
    int idCurso;
    Aluno aluno, alteraAluno;
    DBHelper dbHelper;
    long retornoDB;
    private List<SpinnerCurso> cursos;
    public ArrayAdapter<SpinnerCurso> adapterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        spinnerCurso = findViewById(R.id.spinnerCursos);
        btnVariavel = findViewById(R.id.btnVariavel);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        cursos = loadDataSpinner();
        adapterSpinner = new ArrayAdapter<SpinnerCurso>(CadastroAluno.this, android.R.layout.simple_spinner_item, cursos);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurso.setAdapter(adapterSpinner);

        Snackbar snackbar = Snackbar.make(coordinatorLayout , "Caso seu curso não esteja aqui, faça um cadastro", Snackbar.LENGTH_INDEFINITE).
                            setDuration(30000).
                            setAction("Cadastrar curso", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent it = new Intent(CadastroAluno.this, CadastroCurso.class);
                                    startActivity(it);
                                }
                            });
                    snackbar.show();
        spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCurso spi = (SpinnerCurso) parent.getSelectedItem();
                idCurso = getIdCurso(spi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Intent it = getIntent();
        alteraAluno = (Aluno)it.getSerializableExtra("ch_aluno");
        aluno = new Aluno();
        dbHelper = new DBHelper(CadastroAluno.this);

        if(alteraAluno != null) {
            btnVariavel.setText("ALTERAR");
            edtNome.setText(alteraAluno.getNomeAluno());
            edtCpf.setText(alteraAluno.getCpf());
            edtEmail.setText(alteraAluno.getEmail());
            edtTelefone.setText(alteraAluno.getTelefone());
            aluno.setAlunoId(alteraAluno.getAlunoId());
        }else{
            btnVariavel.setText("INSERIR");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String cpf = edtCpf.getText().toString();
                String email = edtEmail.getText().toString();
                String telefone = edtTelefone.getText().toString();


                boolean validaNome = Auxiliares.isNullText(nome);
                boolean validaCpf = Auxiliares.isNullText(cpf);
                boolean validaEmail = Auxiliares.isNullText(email);
                boolean validaTelefone = Auxiliares.isNullText(telefone);
                boolean validaIdCurso = Auxiliares.isInvalidNumber(idCurso);

                if ((validaEmail || validaCpf || validaIdCurso || validaTelefone || validaNome) == true) {
                    Toast.makeText(CadastroAluno.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                }else {
                    aluno.setNomeAluno(nome);
                    aluno.setCpf(cpf);
                    aluno.setEmail(email);
                    aluno.setTelefone(telefone);
                    aluno.setCursoId(idCurso);
                    if(btnVariavel.getText().toString().equals("INSERIR")) {
                        retornoDB = dbHelper.insereAluno(aluno);
                        if (retornoDB == -1) {
                            Toast.makeText(CadastroAluno.this, "Erro as cadastrar aluno", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(CadastroAluno.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                            Intent it = new Intent(CadastroAluno.this, MainActivity.class);
                            startActivity(it);
                        }
                    } else {
                        dbHelper.atualizarContato(aluno);
                        dbHelper.close();
                    }
                    finish();
                }

            }
        });
    }
    private List<SpinnerCurso> loadDataSpinner() {
        dbHelper = new DBHelper(this);
        List <SpinnerCurso> cursos;
        cursos = dbHelper.getAllCursos();
        dbHelper.close();
        return cursos;
    }

    public int getIdCurso(SpinnerCurso spinnerCurso){
        int id = spinnerCurso.getIdCurso();
        return id;
    }
}