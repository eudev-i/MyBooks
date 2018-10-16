package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class LivrosParaLerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_livrosparaler); /*DEFINE O CONTEUDO DA TELA*/
    }
}
