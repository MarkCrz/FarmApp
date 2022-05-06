package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TelaPrincipal extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
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
                trocarTela(TelaPrincipal.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }
}