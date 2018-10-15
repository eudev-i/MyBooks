package br.com.senaijandira.mybooks.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.EditarActivity;
import br.com.senaijandira.mybooks.MyBooksDatabase;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.model.Livro;


public class LivroAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase myBooksDB;

    Context ctx;

    public LivroAdapter(Context ctx,  MyBooksDatabase myBooksDB){ /*COLOCANDO MAIS UM ATRIBUTO "BANCO DE DADOS"*/
        super(ctx, 0, new ArrayList<Livro>());
        this.myBooksDB = myBooksDB;
        this.ctx = ctx;
    }

    private void enviarParaLivrosLido(Livro livro){ /*VERIFICANDO STATUS PARA LIVRO LIDO, E ATUALIZANDO NO BANCO*/

        livro.setStatus("L");
        myBooksDB.daoLivro().atualizar(livro);
    }

    private void enviarParaLivrosQueroLer(Livro livro){ /*VERIFICANDO STATUS PARA LIVRO LIDO, E ATUALIZANDO NO BANCO*/

        livro.setStatus("Q");
        myBooksDB.daoLivro().atualizar(livro);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext())
                    .inflate(R.layout.livro_layout, parent,false);
        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        ImageView imgEditar = v.findViewById(R.id.imgEditarLivro);
        ImageView imgDeletar = v.findViewById(R.id.imgDeleteLivro);

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(livro);
            }
        });

        imgDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar(livro);
            }
        });

        Button btnLivrosLido = v.findViewById(R.id.btnLivrosLido);
        Button btnLivrosQueroLer = v.findViewById(R.id.btnLivrosQueroLer);

        btnLivrosLido.setOnClickListener(new View.OnClickListener() { /*onclick DO BOTAO LIVROS LIDO*/
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(), livro.getTitulo(), Toast.LENGTH_SHORT).show();
                enviarParaLivrosLido(livro);
                v.setVisibility(View.INVISIBLE);

                Toast.makeText(getContext(), "Enviado com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        btnLivrosQueroLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarParaLivrosQueroLer(livro);
                v.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"QUERO LER", Toast.LENGTH_SHORT).show();
            }
        });

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()) );

        //Setando o titulo do livro
        txtLivroTitulo.setText(livro.getTitulo());

        //Setando a descrição do livro
        txtLivroDescricao.setText(livro.getDescricao());


        if(livro.getStatus().equals("L")){
            btnLivrosLido.setVisibility(View.INVISIBLE);
        }
        else if(livro.getStatus().equals("Q")){
            btnLivrosQueroLer.setVisibility(View.INVISIBLE);
        }

        return v;
    }

    public void editar (Livro l){

        Bundle b = new Bundle();

        b.putInt("ID", l.getId());

        Intent edit = new Intent(getContext(), EditarActivity.class);

        edit.putExtras(b);

        ctx.startActivity(edit);
    }

    public void deletar (Livro l){

        Livro livro;

        livro = myBooksDB.daoLivro().selecionarLivroId(l.getId());

        if(livro.getStatus().equals("")){
            myBooksDB.daoLivro().deletar(livro);

        }
        else{
            Toast.makeText(getContext(), "O LIVRO ESTÁ EM OUTRA ESTANTE", Toast.LENGTH_LONG).show();
        }
    }
}