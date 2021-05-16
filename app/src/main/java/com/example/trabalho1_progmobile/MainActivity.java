package com.example.trabalho1_progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnListarAlunos, btnListarCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnListarAlunos = findViewById(R.id.listarAlunos);
        btnListarCursos = findViewById(R.id.listarCursos);

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
}