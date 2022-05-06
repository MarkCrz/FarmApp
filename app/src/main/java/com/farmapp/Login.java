package com.farmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    private AppCompatButton button_cadastro;
    private AppCompatButton button_logar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        IniciarComponentes();

        button_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Cadastrar.class);
                startActivity(intent);
            }
        });

        button_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes(){
        button_cadastro = findViewById(R.id.btnLoginCadastrar);
        button_logar = findViewById(R.id.btnEntrar);
    }
}