package com.example.trabalho1_progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CrudAlunos extends AppCompatActivity {

    ListView listaDeAlunos;
    ListView listaDeAlunosAux;
    Button btnCadastrarAluno;
    DBHelper dbHelper;
    ArrayList arrayListAlunos;
    ArrayList arrayListAlunoAux;
    ArrayAdapter<Aluno> arrayAdapterAluno;
    ArrayAdapter<AuxAluno> arrayAdapterAuxAluno;
    Aluno aluno;
    private int id1,id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_alunos);
        listaDeAlunos = findViewById(R.id.listaDeAlunos);
        listaDeAlunosAux = findViewById(R.id.listAlunoNomeCurso);
        btnCadastrarAluno = findViewById(R.id.inserirAluno);
        preencherLista();
        registerForContextMenu(listaDeAlunos);
        btnCadastrarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CrudAlunos.this, CadastroAluno.class);
                startActivity(it);
            }
        });
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno1 = (Aluno)arrayAdapterAluno.getItem(position);
                Intent it = new Intent(CrudAlunos.this, CadastroAluno.class);
                it.putExtra("ch_aluno", aluno1);
                startActivity(it);
            }
        });
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                aluno = arrayAdapterAluno.getItem(position);
                return false;
            }
        });

    }
    public void preencherLista() {
        dbHelper = new DBHelper(CrudAlunos.this);
        arrayListAlunos = dbHelper.selecionaTodosAlunos();
        dbHelper.close();
        if (arrayListAlunos != null) {
            arrayAdapterAluno = new ArrayAdapter<Aluno>(CrudAlunos.this, android.R.layout.simple_expandable_list_item_1, arrayListAlunos);
            listaDeAlunos.setAdapter(arrayAdapterAluno);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, id1, 1, "Deleta registro");
        MenuItem mSair = menu.add(Menu.NONE, id2, 2, "Cancela");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                dbHelper = new DBHelper(CrudAlunos.this);
                retornoDB = dbHelper.deleteAluno(aluno);
                dbHelper.close();
                if(retornoDB == -1){
                    Toast.makeText(CrudAlunos.this, "Erro ao excluir", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CrudAlunos.this, "Registro excluído com sucesso", Toast.LENGTH_SHORT).show();
                }
                preencherLista();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherLista();
    }
}