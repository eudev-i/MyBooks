package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.senaijandira.mybooks.CadastroActivity;
import br.com.senaijandira.mybooks.MyBooksDatabase;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapters.LivroAdapter;
import br.com.senaijandira.mybooks.model.Livro;

public class BibliotecaGeral extends Fragment{

    /*ListView que carregará os livros*/
    ListView lstViewLivros;

    public static List<Livro> livros;

    private MyBooksDatabase myBooksDB; /*VARIAVEL DE ACESSO AO BANCO*/

    /*ADAPTER PARA CRIAR A LISTA DE LIVROS*/
    LivroAdapter adapter;


    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_bibliotecageral,container, false);

        fab = v.findViewById(R.id.fab);

        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        /*INSTANCIA DO BANCO DE DADOS*/
        myBooksDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        /*CRIAR ADAPTER*/
        adapter = new LivroAdapter(getContext(),myBooksDB );
        lstViewLivros.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             abrirCadastro();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        carregarLivros();

    }

    public void carregarLivros(){
        livros = myBooksDB.daoLivro().selecionarTodos(); /*FAZER O SELECT NO BANCO*/

        adapter.clear();
        adapter.addAll(livros);
    }


    public void deletarLivro(final Livro livro, final View v){

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Deletar");
        alert.setMessage("Tem certeza que deseja deletar?");
        alert.setNegativeButton("Não", null);

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*DELETAR O  LIVRO*/
                myBooksDB.daoLivro().deletar(livro);

                carregarLivros();

            }
        });

        alert.show();

    }

    public void criarLivro(final Livro livro, ViewGroup root){
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, root, false);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);

        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);

        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);

        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarLivro(livro, v);
            }
        });

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa())); /*SETANDO A IMAGEM DO LIVRO*/
        txtLivroTitulo.setText(livro.getTitulo()); /*SETANDO O TITULO DO LIVRO*/
        txtLivroDescricao.setText(livro.getDescricao()); /*SETANDO A DESCRICAO DO LIVRO*/
        imgDeleteLivro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deletarLivro(livro, v);
            }
        });

        root.addView(v);

    }

    public void abrirCadastro(){
        startActivity(new Intent(getContext(), CadastroActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        myBooksDB.close();
    }
}
