package com.example.cadastroinstrumentos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void deletarInstrumento(View view) {
        EditText nomeInstrumento = findViewById(R.id.editTextNomeDoInstParaDeletar);

        //gerar uma duvida quanto a exclusao do cadastro no banco
        //codigo a seguir extraido do stackOverflow :
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alerta !");

        builder.setMessage("Deseja mesmo deletar este cadastro ???")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //codigo caso o usuario click em ok
                        bancoDadosInstrumentos.deletarInstrumentoPeloNome(nomeInstrumento.getText().toString());

                        //mensagem de confirmacao ao usuario
                        //nao esquecer de passar a classe.this como contexto=> macetezinho maroto
                        Toast.makeText(MainActivity.this,"Cadastro Deletado !",Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();//metodo para criar a caixa de dialogo
        alertDialog.show(); //metodo para mostrar a caixa de dialogo
    }
}