package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.MyBooksDatabase;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapters.LivroLidoAdapter;
import br.com.senaijandira.mybooks.model.Livro;


public class Lidos extends Fragment{

    /*ListView que carregará os livros*/
    ListView lstViewLivros;

    public static Livro livros;

    private MyBooksDatabase myBooksDB; /*VARIAVEL DE ACESSO AO BANCO*/

    /*ADAPTER PARA CRIAR A LISTA DE LIVROS*/
    LivroLidoAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lidos,container, false);

        lstViewLivros = v.findViewById(R.id.lstViewLivros);


        /*INSTANCIA DO BANCO DE DADOS*/
        myBooksDB = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        /*CRIAR ADAPTER*/
        adapter = new LivroLidoAdapter(getContext() ,myBooksDB );
        lstViewLivros.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        Livro[] resultadoLidos = myBooksDB.daoLivro().selecionarLivrosLidos();
        adapter.addAll(resultadoLidos);

    }


}
