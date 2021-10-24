package com.example.cadastroinstrumentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListagemInstrumentoActivity extends AppCompatActivity {
    BancoDadosInstrumentos bancoDadosInstrumentos = new BancoDadosInstrumentos(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_instrumento);

        ListView listView = findViewById(R.id.ListViewInstrumentos);

        List<Instrumento> listainstrumentos = bancoDadosInstrumentos.listarInstumentos();
        List<String> listString = new ArrayList<>();

        for (Instrumento instrumento: listainstrumentos) {
            listString.add(instrumento.categoria+" >>>"+instrumento.nomeInstrumento);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listView.setAdapter(adapter);
    }


}