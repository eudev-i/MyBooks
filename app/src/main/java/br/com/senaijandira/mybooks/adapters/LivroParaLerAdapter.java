package br.com.senaijandira.mybooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.MyBooksDatabase;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.model.Livro;

public class LivroParaLerAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase myBooksDB;

    public LivroParaLerAdapter(Context ctx, MyBooksDatabase myBooksDB){ /*COLOCANDO MAIS UM ATRIBUTO "BANCO DE DADOS"*/
        super(ctx, 0, new ArrayList<Livro>());
        this.myBooksDB = myBooksDB;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext())
                    .inflate(R.layout.livro_layout,
                            parent, false);
        }

        final Livro livro = getItem(position);

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        Button btnLivrosLido = v.findViewById(R.id.btnLivrosLido);
        final Button btnLivrosQueroLer = v.findViewById(R.id.btnLivrosQueroLer);

        if(livro.getStatus().equals("L")){
            btnLivrosLido.setVisibility(View.INVISIBLE);
        }

        //Setando a imagem
        imgLivroCapa.setImageBitmap(
                Utils.toBitmap(livro.getCapa()) );

        //Setando o titulo do livro
        txtLivroTitulo.setText(livro.getTitulo());

        //Setando a descrição do livro
        txtLivroDescricao.setText(livro.getDescricao());

        return v;
    }
}
