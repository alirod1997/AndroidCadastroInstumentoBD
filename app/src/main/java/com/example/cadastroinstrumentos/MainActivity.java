package com.example.cadastroinstrumentos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Optional;

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

        builder.setTitle("Remocão de usuário: ");

        builder.setMessage("Deseja mesmo deletar este cadastro ???")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //validar se o cadastro está presente no banco antes de manipular os dados
                        Optional<Instrumento> op = bancoDadosInstrumentos.buscaInstrumentoPeloNome(nomeInstrumento.getText().toString());
                        if (op.isPresent()){
                            //codigo caso o usuario click em ok
                            bancoDadosInstrumentos.deletarInstrumentoPeloNome(nomeInstrumento.getText().toString());
                            //mensagem de confirmacao ao usuario
                            //nao esquecer de passar a classe.this como contexto=> macetezinho maroto
                            Toast.makeText(MainActivity.this,"Cadastro Deletado !",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Cadastro não encontrado",Toast.LENGTH_SHORT).show();
                        }//fim da validação
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

    public void editarInstrumento(View view) {
        EditText categoriaAtualizar = findViewById(R.id.editTextCategoriaAtualizar);
        EditText nomeAtualizar = findViewById(R.id.editTextNomeAtualizar);

        if (categoriaAtualizar.getText().toString().equals("") || nomeAtualizar.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"por favor preencha os dados ",Toast.LENGTH_SHORT).show();
        }else {
            Instrumento instrumentoAtualizar = new Instrumento();
            instrumentoAtualizar.categoria = categoriaAtualizar.getText().toString();
            instrumentoAtualizar.nomeInstrumento = nomeAtualizar.getText().toString();
            bancoDadosInstrumentos.atualizaInstrumentos(instrumentoAtualizar);
        }
    }
}