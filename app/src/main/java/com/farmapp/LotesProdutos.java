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

import com.farmapp.Dados.ClassListLotesProdutos;

import java.util.ArrayList;

public class LotesProdutos extends AppCompatActivity {

    private AppCompatButton button_voltar;
    private AppCompatButton button_cadastrar;
    private ListView lvLotesProdutos;
    ArrayList<ClassListLotesProdutos> lista;
    private LotesCadastrarFragment lotesCadastrarFragment = new LotesCadastrarFragment();
    private LotesEditarFragment lotesEditarFragment = new LotesEditarFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes_produtos);
        IniciarComponentes();

        lista = new ArrayList<>();

        ClassListLotesProdutos p1 = new ClassListLotesProdutos("F503", "150");
        ClassListLotesProdutos p2 = new ClassListLotesProdutos("L0131", "200");
        ClassListLotesProdutos p3 = new ClassListLotesProdutos("PPZ1311", "27");
        ClassListLotesProdutos p4 = new ClassListLotesProdutos("R0100101", "0");

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
        lista.add(p4);

        ArrayAdapter<ClassListLotesProdutos> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, lista
        );

        lvLotesProdutos.setAdapter(adapter);

        lvLotesProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.LotesEditarFrameLayout, lotesEditarFragment).commit();
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LotesProdutos.this, Lotes.class);
                startActivity(intent);
            }
        });

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.LotesCadastrarFrameLayout, lotesCadastrarFragment).commit();
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
                trocarTela(LotesProdutos.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(LotesProdutos.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }

    private void IniciarComponentes(){
        button_voltar = findViewById(R.id.btnVoltarLotesProdutos);
        button_cadastrar = findViewById(R.id.btnCadastrarLotesProdutos);
        lvLotesProdutos = findViewById(R.id.listViewProdutosLotes);
    }
}