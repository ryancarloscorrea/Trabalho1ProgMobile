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

public class CrudCursos extends AppCompatActivity {

    ListView listaDeCursos;
    Button btnCadastrarCurso;
    DBHelper dbHelper;
    ArrayList arrayListCursos;
    ArrayAdapter<Curso> arrayAdapterCursos;
    Curso curso;
    private int id1,id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_cursos);
        listaDeCursos = findViewById(R.id.listaDeCursos);
        btnCadastrarCurso = findViewById(R.id.inserirCurso);
        preencherLista();
        registerForContextMenu(listaDeCursos);
        btnCadastrarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CrudCursos.this, CadastroCurso.class);
                startActivity(it);
            }
        });
        listaDeCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Curso curso1 = (Curso)arrayAdapterCursos.getItem(position);
                Intent it = new Intent(CrudCursos.this, CadastroCurso.class);
                it.putExtra("ch_curso", curso1);
                startActivity(it);
            }
        });
        listaDeCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                curso = arrayAdapterCursos.getItem(position);
                return false;
            }
        });

    }
    public void preencherLista() {
        dbHelper = new DBHelper(CrudCursos.this);
        arrayListCursos = dbHelper.selecionaTodosCursos();
        dbHelper.close();
        if (arrayListCursos != null) {
            arrayAdapterCursos = new ArrayAdapter<Curso>(CrudCursos.this, android.R.layout.simple_expandable_list_item_1, arrayListCursos);
            listaDeCursos.setAdapter(arrayAdapterCursos);
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
                dbHelper = new DBHelper(CrudCursos.this);
                retornoDB = dbHelper.deletarCurso(curso, CrudCursos.this);
                dbHelper.close();
                if(retornoDB == -1){
                    Toast.makeText(CrudCursos.this, "Erro ao excluir, ainda possui alunos cadastrados nesse curso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CrudCursos.this, "Registro excluído com sucesso", Toast.LENGTH_SHORT).show();
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