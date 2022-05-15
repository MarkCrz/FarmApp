package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

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

public class Produtos extends AppCompatActivity {

    private ProdutosCadastrar produtosFragment = new ProdutosCadastrar();
    private ProdutosEditar produtosEditar = new ProdutosEditar();
    private AppCompatButton button_produtosCadastrar;
    private AppCompatButton button_produtosVoltar;
    private ListView lvProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        IniciarComponentes();

        ArrayList<String> listaProdutos = new ArrayList<>();

        listaProdutos.add("Minoxidil");
        listaProdutos.add("Whey");
        listaProdutos.add("Termogênico");
        listaProdutos.add("Melatonina");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listaProdutos
        );

        lvProdutos.setAdapter(adapter);

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.produtosEditarFrameLayout, produtosEditar).commit();
            }
        });

        button_produtosVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Produtos.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });

        button_produtosCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.produtosCFrameLayout, produtosFragment).commit();
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
                trocarTela(Produtos.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(Produtos.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }


    private void IniciarComponentes(){
        button_produtosCadastrar = findViewById(R.id.btnCadastrarProdutos);
        button_produtosVoltar = findViewById(R.id.btnVoltarProdutos);
        lvProdutos = findViewById(R.id.listViewProdutos);
    }
}