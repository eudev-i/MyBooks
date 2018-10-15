package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    private final int COD_REQ_GALERIA = 101;
    EditText txtTitulo, txtDescricao;

    private MyBooksDatabase myBooksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        /*INSTANCIA DO BANCO DE DADOS*/
        myBooksDB = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void abrirGaleria(View view) {

        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Selecione uama imagem"), COD_REQ_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){
            try{
                InputStream input = getContentResolver().openInputStream(data.getData());

                livroCapa = BitmapFactory.decodeStream(input);/*CONVERTEU PARA BITMAP*/

                imgLivroCapa.setImageBitmap(livroCapa);/*EXIBINDO NA TELA*/
            }
            catch (Exception erro){
                erro.printStackTrace();
            }
        }
    }

    public void salvarLivro(View view) {

        byte[] capa = Utils.toByteArray(livroCapa);

        if(livroCapa != null){
            capa = Utils.toByteArray(livroCapa);
        }

        String titulo = txtTitulo.getText().toString();

        String descricao = txtDescricao.getText().toString();

        Livro livro = new Livro(capa, titulo, descricao);
        livro.setStatus("");

        /*VALIDAÇÃO*/
        if(!titulo.equals("") && !descricao.equals("") && livroCapa != null){

            myBooksDB.daoLivro().inserir(livro);/*INSERIR NO BANCO DE DADOS*/
                alert("SUCESSO", "Livro Salvo com sucesso");

        } else {
            alert("ERRO", "Preencha todos os campos!");
        }
    }

    public void alert(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((titulo));
        builder.setMessage(mensagem);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.setCancelable(false);
        builder.show();

    }
}
