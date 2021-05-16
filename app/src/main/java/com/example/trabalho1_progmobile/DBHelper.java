package com.example.trabalho1_progmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cursosOnline.db";


    private static final String TABLE_NAME_ALUNO = "aluno";
    private static final String COLUM_ALUNOID = "alunoId";
    private static final String COLUM_NOME = "nomeAluno";
    private static final String COLUM_CPF = "cpf";
    private static final String COLUM_EMAIL = "email";
    private static final String COLUM_TELEFONE = "telefone";

    private static final String COLUM_CURSO_ID = "cursoId"; /// FK

    private static final String TABLE_NAME_CURSO = "curso";
    private static final String COLUM_NOME_CURSO = "nomeCurso";
    private static final String COLUM_QTD_HORAS = "qtdeHoras";

    SQLiteDatabase db;

    private static final String TABLE_ALUNO_CREATE = "create table aluno" +
            "(alunoId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cursoId integer not null, nomeAluno text not null," +
            "cpf text not null," +
            "email text not null, telefone text not null," +
            "foreign key(cursoId) references curso(cursoId))";

    private static final String TABLE_CURSO_CREATE = "create table curso" +
            "(cursoId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nomeCurso text not null," +
            "qtdeHoras text not null)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CURSO_CREATE);
        db.execSQL(TABLE_ALUNO_CREATE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryCurso = "DROP TABLE IF EXISTS " + TABLE_NAME_CURSO;
        String queryAluno = "DROP TABLE IF EXISTS " + TABLE_NAME_ALUNO;
        db.execSQL(queryCurso);
        db.execSQL(queryAluno);
    }

    //////////////// CURSO  - CRUD /////////////////////////////


    public long insereCurso (Curso curso) {
        long retornoDB;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_CURSO_ID, curso.getCursoId());
        values.put(COLUM_NOME_CURSO, curso.getNomeCurso());
        values.put(COLUM_QTD_HORAS, curso.getQtdeHoras());

        retornoDB = db.insert(TABLE_NAME_CURSO, null, values);
        String res = Long.toString(retornoDB);
        Log.i("DBContatoHelperCurso", res);
        String sqlQuery = "SELECT * from curso";
        Cursor cursor = getReadableDatabase().rawQuery(sqlQuery, null);
//        while(cursor.moveToNext()) {
//            Log.i("InsereCurso", String.valueOf(cursor.getInt(0)));
//            Log.i("InsereCurso", String.valueOf(cursor.getString(1)));
//            Log.i("InsereCurso", String.valueOf(cursor.getString(2)));
//        }
        Log.i("CursoInserido", String.valueOf(cursor.getCount()));
        db.close();


        return retornoDB;
    }
    public ArrayList<Curso> selecionaTodosCursos() {
        String [] colums = {COLUM_CURSO_ID, COLUM_NOME_CURSO,COLUM_QTD_HORAS};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME_CURSO, colums, null, null,null,null, "upper(nomeCurso)");
        ArrayList<Curso> listCursos = new ArrayList<>();

        while(cursor.moveToNext()) {
            Curso curso = new Curso();
            curso.setCursoId(cursor.getInt(0));
            curso.setNomeCurso(cursor.getString(1));
            curso.setQtdeHoras(cursor.getInt(2));
            listCursos.add(curso);
        }
        return listCursos;
    }

    public long atualizarCurso (Curso curso) {
        long retorno;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_CURSO_ID, curso.getCursoId());
        values.put(COLUM_NOME_CURSO, curso.getNomeCurso());
        values.put(COLUM_QTD_HORAS, curso.getQtdeHoras());
        String [] args = new String[]{String.valueOf(curso.getCursoId())};
//        Log.i("ID ALUNO", String.valueOf(args));

        retorno = db.update(TABLE_NAME_CURSO, values, "cursoId = ?", args);
        Log.i("update", String.valueOf(retorno));
        db.close();
        return retorno;
    }

    public long deletarCurso(Curso curso, Context context) {
        long retornoDB;

        db = this.getWritableDatabase();
        String query = "SELECT c.cursoId, a.cursoId from curso as c, aluno as a where c.cursoId = a.cursoId";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()) {
            return -1;

        }else{
            String [] args = {String.valueOf(curso.getCursoId())};
            retornoDB = db.delete(TABLE_NAME_CURSO, COLUM_CURSO_ID + "=?", args);
            Log.i("Delete", String.valueOf(retornoDB));
            return retornoDB;
        }
    }



    //////////////// ALUNO-CRUD /////////////////////////////

    public long insereAluno (Aluno aluno) {
        long retornoDB;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_CURSO_ID, aluno.getCursoId());
        values.put(COLUM_NOME, aluno.getNomeAluno());
        values.put(COLUM_CPF, aluno.getCpf());
        values.put(COLUM_EMAIL, aluno.getEmail());
        values.put(COLUM_TELEFONE, aluno.getTelefone());

        retornoDB = db.insert(TABLE_NAME_ALUNO, null, values);
        String res = Long.toString(retornoDB);
        Log.i("DBContatoHelper", res);
        db.close();
        return retornoDB;
    }

    public ArrayList<Aluno> selecionaTodosAlunos() {
        String [] colums = {COLUM_ALUNOID, COLUM_CURSO_ID,COLUM_NOME,COLUM_CPF, COLUM_EMAIL, COLUM_TELEFONE};

        Cursor cursor = getReadableDatabase().query(TABLE_NAME_ALUNO, colums, null, null,null,null, "upper(nomeAluno)");
        ArrayList<Aluno> listAluno = new ArrayList<>();

        while(cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setAlunoId(cursor.getInt(0));
            aluno.setCursoId(cursor.getInt(1));
            aluno.setNomeAluno(cursor.getString(2));
            aluno.setCpf(cursor.getString(3));
            aluno.setEmail(cursor.getString(4));
            aluno.setTelefone(cursor.getString(5));
            listAluno.add(aluno);
        }
        return listAluno;
    }
    public ArrayList<Curso> selecionaCursoPorAluno() {
        String sqlQuery = "SELECT * from curso";
        // , curso as c where a.cursoId = c.cursoId";
        Cursor cursor = getReadableDatabase().rawQuery(sqlQuery, null);
//        String [] colums = {COLUM_CURSO_ID,COLUM_NOME_CURSO, COLUM_QTD_HORAS};
//        Cursor cursor = getReadableDatabase().query(TABLE_NAME_CURSO, colums, null, null,null,null, "upper(nomeCurso)");

        ArrayList<Curso> listCurso = new ArrayList<>();

        while(cursor.moveToNext()) {
            Curso curso = new Curso();
            curso.setCursoId(cursor.getInt((0)));
            curso.setNomeCurso(cursor.getString(1));
            curso.setQtdeHoras(cursor.getInt(2));
            listCurso.add(curso);
        }
        return listCurso;
    }
    public long atualizarContato (Aluno aluno) {
        long retorno;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.i("Insert3442", String.valueOf(aluno.getNomeAluno()));
        values.put(COLUM_CURSO_ID, aluno.getCursoId());
        values.put(COLUM_NOME, aluno.getNomeAluno());
        values.put(COLUM_CPF, aluno.getCpf());
        values.put(COLUM_EMAIL, aluno.getEmail());
        values.put(COLUM_TELEFONE, aluno.getTelefone());
        String [] args = new String[]{String.valueOf(aluno.getAlunoId())};
        Log.i("ID ALUNO", String.valueOf(args));
        String where;
        where = COLUM_NOME + "=" + aluno.getNomeAluno();
        retorno = db.update(TABLE_NAME_ALUNO, values, "alunoId = ?", args);
        Log.i("update", String.valueOf(retorno));
        db.close();
        return retorno;
    }
    public long deleteAluno(Aluno aluno) {
        long retornoDB;
        db = this.getWritableDatabase();
        String [] args = {String.valueOf(aluno.getAlunoId())};
        retornoDB = db.delete(TABLE_NAME_ALUNO, COLUM_ALUNOID + "=?", args);
        Log.i("Delete", String.valueOf(retornoDB));
        return retornoDB;

    }

    public List<SpinnerCurso> getAllCursos() {
        db = this.getWritableDatabase();
        String query = "SELECT cursoId, nomeCurso from curso";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        Log.i("testando", String.valueOf(cursor));
        List <SpinnerCurso> spinnerCursos = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idCurso = cursor.getInt(0);
            String nomeCurso = cursor.getString(1);
            SpinnerCurso spinnerCurso = new SpinnerCurso(idCurso, nomeCurso);
            spinnerCursos.add(spinnerCurso);
        }
        return spinnerCursos;


    }
}

//    Tabela Aluno
//    int alunoId;
//    int cursoId; (chave estrangeira)
//    String nomeAluno;
//    String cpf;
//    String email;
//    String telefone;
