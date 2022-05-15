package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Lotes extends AppCompatActivity {

    private ListView lvProdutosLotes;
    private AppCompatButton button_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        IniciarComponentes();

        ArrayList<String> listaProdutosLotes = new ArrayList<>();

        listaProdutosLotes.add("Minoxidil");
        listaProdutosLotes.add("Whey");
        listaProdutosLotes.add("Termogênico");
        listaProdutosLotes.add("Melatonina");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listaProdutosLotes
        );

        lvProdutosLotes.setAdapter(adapter);

        lvProdutosLotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Lotes.this, LotesProdutos.class);
                startActivity(intent);
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lotes.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settingsItem:
                trocarTela(Lotes.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(Lotes.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }

    private void IniciarComponentes(){
        lvProdutosLotes = findViewById(R.id.listViewProdutosLotes);
        button_voltar = findViewById(R.id.btnVoltarLotes);
    }
}