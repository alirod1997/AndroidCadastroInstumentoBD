package com.example.cadastroinstrumentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BancoDadosInstrumentos extends SQLiteOpenHelper {
    public static final String BANCO_DADOS = "muitosons";
    public static int VERSAO = 1;

    public BancoDadosInstrumentos(@Nullable Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table instrumento(categoria TEXT, nomeinstrumento TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void salvaDados(Instrumento instrumento){
        ContentValues valores = new ContentValues();
        valores.put("categoria",instrumento.categoria);
        valores.put("nomeinstrumento",instrumento.nomeInstrumento);
        final long ret = getWritableDatabase().insert("instrumento", null, valores);
        //importante inserir log para saber oque esta ocorrendo na execucao da aplicacao
        Log.i("muitosons","salvo novo instrumento no banco de dados - " + ret);
    }

    public List<Instrumento> listarInstumentos(){
        List<Instrumento> lista = new ArrayList<>();
        String sql = "select * from instrumento ";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Instrumento instrumento = new Instrumento();
            instrumento.categoria = cursor.getString(0); //posicao 1
            instrumento.nomeInstrumento = cursor.getString(1); //posicao 2
            lista.add(instrumento);
            cursor.moveToNext();
        }
        cursor.close(); //obrigatorio
        return lista;
    }

    //sempre passar uma condicao => where e um parametro => ex: nome, id ...
    public void deletarInstrumentoPeloNome(String nome){
        String sql ="delete from instrumento where nomeInstrumento = ?";
        getWritableDatabase().execSQL(sql, new String[] {nome});
        //adicionar um log para identificar que a operacao foi feita
        Log.i("muitosons","Instrumento foi deletado do banco de dados " + nome );

    }






}
