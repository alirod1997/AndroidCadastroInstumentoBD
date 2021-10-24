package com.example.cadastroinstrumentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    BancoDadosInstrumentos bancoDadosInstrumentos = new BancoDadosInstrumentos(this);//usar this para parar o erro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrar(View view) {
        EditText categoria = findViewById(R.id.editTextCategoria);
        EditText nomeinstrumento = findViewById(R.id.editTextNomeInstrumento);

        //objeto instrumento
        Instrumento instrumento = new Instrumento();
        instrumento.categoria = categoria.getText().toString();
        instrumento.nomeInstrumento = nomeinstrumento.getText().toString();
        bancoDadosInstrumentos.salvaDados(instrumento);

    }

    public void Listar(View view) {
        Intent intent = new Intent(this, ListagemInstrumentoActivity.class);
        startActivity(intent);

    }
}