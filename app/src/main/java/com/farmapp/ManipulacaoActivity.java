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

import com.farmapp.Dados.ManipulacaoClasse;

import java.util.ArrayList;

public class ManipulacaoActivity extends AppCompatActivity {

    private AppCompatButton button_voltar;
    private AppCompatButton button_cadastrar;
    private ManipulacaoInicioFragment manipulacaoInicioFragment = new ManipulacaoInicioFragment();
    private ListView lvManipulacao;
    ArrayList<ManipulacaoClasse> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulacao);
        IniciarComponentes();

        lista = new ArrayList<>();

        ManipulacaoClasse m1 = new ManipulacaoClasse("Neuza");
        ManipulacaoClasse m2 = new ManipulacaoClasse("Zureide");
        ManipulacaoClasse m3 = new ManipulacaoClasse("Adileide");
        ManipulacaoClasse m4 = new ManipulacaoClasse("Madalena");
        ManipulacaoClasse m5 = new ManipulacaoClasse("Veronica");
        ManipulacaoClasse m6 = new ManipulacaoClasse("Cíntia");
        ManipulacaoClasse m7 = new ManipulacaoClasse("Cléia");

        lista.add(m1);
        lista.add(m2);
        lista.add(m3);
        lista.add(m4);
        lista.add(m5);
        lista.add(m6);
        lista.add(m7);

        ArrayAdapter<ManipulacaoClasse> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, lista
        );

        lvManipulacao.setAdapter(adapter);

        lvManipulacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoInicioFragment).commit();
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManipulacaoActivity.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoInicioFragment).commit();
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
                trocarTela(ManipulacaoActivity.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(ManipulacaoActivity.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }

    private void IniciarComponentes(){
        button_voltar = findViewById(R.id.btnVoltarManipulacao);
        button_cadastrar = findViewById(R.id.btnCadastrarManipulacao);
        lvManipulacao = findViewById(R.id.listViewManipulacao);
    }
}