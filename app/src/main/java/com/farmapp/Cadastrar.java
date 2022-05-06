package com.farmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Cadastrar extends AppCompatActivity {

    private AppCompatButton button_cadastrar;
    private AppCompatButton button_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        getSupportActionBar().hide();
        IniciarComponentes();

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Cadastrar.this, );
//                startActivity(intent);
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastrar.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes(){
        button_cadastrar = findViewById(R.id.btnCadastrar);
        button_voltar = findViewById(R.id.btnCadastrarVoltar);
    }
}