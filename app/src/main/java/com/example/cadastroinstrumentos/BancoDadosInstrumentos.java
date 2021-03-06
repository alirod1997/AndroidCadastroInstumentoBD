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
import java.util.Optional;

public class BancoDadosInstrumentos extends SQLiteOpenHelper {
    public static final String BANCO_DADOS = "muitosons";
    public static int VERSAO = 2;

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

    //busca cadastro pelo nome do instrumento
    public Optional<Instrumento> buscaInstrumentoPeloNome(String nomeInstrumento){

        Optional<Instrumento> retorno = Optional.empty();
        String sql = "select * from instrumento where nomeInstrumento = ? ";
        Cursor cursor =  getReadableDatabase().rawQuery(sql,new String[]{nomeInstrumento});
        cursor.moveToFirst();
        Instrumento instrumento = new Instrumento();
        for(int i=0; i < cursor.getCount(); i++){
            instrumento.categoria = cursor.getString(0);
            instrumento.nomeInstrumento = cursor.getString(1);
            cursor.moveToNext();
            retorno = Optional.of(instrumento);
        }
        cursor.close();
        return retorno;
    }


    //sempre passar uma condicao => where e um parametro => ex: nome, id ...
    public void deletarInstrumentoPeloNome(String nome){
        String sql ="delete from instrumento where nomeInstrumento = ?";
        getWritableDatabase().execSQL(sql, new String[] {nome});
        //adicionar um log para identificar que a operacao foi feita
        Log.i("muitosons","Instrumento foi deletado do banco de dados " + nome );

    }

    //metodo para atualizar dados especificos no banco
    //caso queira mudar este parametros, podemos trabalhar com sobrecarga de metodos
    public void atualizaInstrumentos(Instrumento instrumento){
        String sql ="update instrumento set nomeInstrumento = ? where categoria = ?";
        getWritableDatabase().execSQL(sql, new String[] {instrumento.nomeInstrumento, instrumento.categoria});
        Log.i("muitosons","Instrumento foi atualizado do banco de dados pelo nome " + instrumento.nomeInstrumento );

    }




}
