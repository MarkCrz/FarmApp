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

import com.google.firebase.auth.FirebaseAuth;

public class TelaPrincipal extends AppCompatActivity {

    private AppCompatButton button_produtos;
    private AppCompatButton button_lotes;
    private AppCompatButton button_manipulacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        IniciarComponentes();

        button_produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Produtos.class);
                startActivity(intent);
            }
        });

        button_lotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Lotes.class);
                startActivity(intent);
            }
        });

        button_manipulacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, ManipulacaoActivity.class);
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
                trocarTela(TelaPrincipal.this, Configuracoes.class);
                break;
            case R.id.logoutItem:

                FirebaseAuth.getInstance().signOut();

                trocarTela(TelaPrincipal.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes(){
        button_produtos = findViewById(R.id.btnPrincipalProdutos);
        button_lotes = findViewById(R.id.btnPrincipalLotes);
        button_manipulacao = findViewById(R.id.btnPrincipalManipulacao);
    }
}