package br.com.senaijandira.mybooks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.senaijandira.mybooks.fragments.BibliotecaGeral;
import br.com.senaijandira.mybooks.fragments.Lidos;
import br.com.senaijandira.mybooks.fragments.LivrosParaLer;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        bottom_nav = findViewById(R.id.bottom_nav);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.fragLivrosLidos) {
                    openFragment1();
                    return true;
                }

                if (item.getItemId() == R.id.fragLivrosParaLer) {
                    openFragment2();
                    return true;
                }

                if (item.getItemId() == R.id.fragBibliotecaGeral) {
                    openFragment3();
                    return true;
                }
                return false;
            }
        });

        openFragment3();
    }

    public void openFragment1() {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout, new Lidos());

        ft.commit();
    }

    public void openFragment2() {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout, new LivrosParaLer());

        ft.commit();
    }

    public void openFragment3() {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.frame_layout, new BibliotecaGeral());

        ft.commit();
    }
}
