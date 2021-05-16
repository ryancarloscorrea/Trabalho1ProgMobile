package com.example.trabalho1_progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnListarAlunos, btnListarCursos;
    ListView alunosCursosListView;
    private DBHelper dbHelper;
    private ArrayList arrayListAlunoCurso;
    private ArrayAdapter arrayAdapterAlunoCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnListarAlunos = findViewById(R.id.listarAlunos);
        btnListarCursos = findViewById(R.id.listarCursos);
        alunosCursosListView = findViewById(R.id.listAlunoNomeCurso);
        preencherLista();

        btnListarAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, CrudAlunos.class);
                startActivity(it);
            }
        });
        btnListarCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, CrudCursos.class);
                startActivity(it);
            }
        });
    }
    public void preencherLista() {
        dbHelper = new DBHelper(MainActivity.this);
        arrayListAlunoCurso = dbHelper.getAlunoCurso();
        dbHelper.close();
        if (arrayListAlunoCurso != null) {
            arrayAdapterAlunoCurso = new ArrayAdapter<Curso>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, arrayListAlunoCurso);
            alunosCursosListView.setAdapter(arrayAdapterAlunoCurso);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherLista();

    }
}