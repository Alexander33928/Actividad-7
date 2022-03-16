package com.example.actividad7;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PessoaDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    private static final String NOME_BANCO="pessoa.sqlite";
    private static final int VERSAO = 1;

    public PessoaDB(Context context){super(context,NOME_BANCO,null,VERSAO);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    Log.d(TAG,"Criando a Tabela pessoa");
    sqLiteDatabase.execSQL("create table if not exists pessoa(_id integer primary key autoincrement,nome text,email text,curso text);");
    Log.d(TAG,"Tabela Criada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long save(Pessoa pessoa){
        long id = pessoa.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nome",pessoa.getNome());
            values.put("email",pessoa.getEmail());
            values.put("curso",pessoa.getCurso());
            if (id!=0){
                String _id = String.valueOf(pessoa.getId());
                String[] whereArgs = new String[]{_id};
                int count = db.update("pessoa",values,"_id=?",whereArgs);
                return count;
            }
            else{
                id = db.insert("pessoa","",values);
                return id;
            }
        }
        finally {
            db.close();
        }
    }
    public int delete(Pessoa pessoa){
        SQLiteDatabase db = getWritableDatabase();
        try{
            int count = db.delete("pessoa","_id=?",new String[]{String.valueOf(pessoa.getId())});
            Log.d(TAG, "Deleteu ["+count+"] registro.");
            return count;
        }finally {
            db.close();
        }
    }
    public int deletePessoaByCurso(String curso){
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete("pessoa","curso=?",new String[]{curso});
            Log.d(TAG, "Deleteu ["+ count + "] registros.");
            return count;
        }finally {
            db.close();
        }
    }
    public List<Pessoa> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try {
           Cursor c = db.query("pessoa",null,null,null,null,null,null,null);
            return toList(c);
        }finally {
            db.close();
        }
    }
    @SuppressLint("Range")
    private  List<Pessoa> toList (Cursor c){
        List<Pessoa> pessoas = new ArrayList<>();
        if (c.moveToFirst()){
    do {
        Pessoa p = new Pessoa();
        pessoas.add(p);
        p.setId(c.getInt(c.getColumnIndex("_id")));
        p.setNome(c.getString(c.getColumnIndex("nome")));
        p.setCurso(c.getString(c.getColumnIndex("curso")));
    }while(c.moveToNext());
        }
        return pessoas;
    }
}
