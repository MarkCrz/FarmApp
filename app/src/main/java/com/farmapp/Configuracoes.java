package com.farmapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Configuracoes extends AppCompatActivity {

    private AppCompatButton button_salvar;
    private AppCompatButton button_voltar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    private EditText editLab, editSenha, editConfSenha, editCusto, editValor;

    String[] mensagens = {
            "Preencha o campo de senha",
            "Preencha o campo de confirmar senha",
            "A senha não está igual",
            "Alteração realizada com sucesso",
            "Ocorreu um erro no salvamento de dados"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        IniciarComponentes();

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senha = editSenha.getText().toString();
                String confSenha = editConfSenha.getText().toString();
                String custo = editCusto.getText().toString();
                String valor = editValor.getText().toString();

                if (senha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (confSenha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (!senha.equals(confSenha)) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    AtualizarDados(custo, valor, view);
                }
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuracoes.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
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
            case R.id.logoutItem:

                FirebaseAuth.getInstance().signOut();

                trocarTela(Configuracoes.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    editLab.setText(value.getString("laboratorio"));
                    editCusto.setText(value.getDouble("custo").toString());
                    editValor.setText(value.getDouble("valor").toString());
                }
            }
        });
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
        finish();
    }

    private void AtualizarDados(String custo, String valor, View view) {
        double custoDouble = Double.parseDouble(custo);
        double valorDouble = Double.parseDouble(valor);

        db.collection("Usuarios").document(usuarioID)
                .update("custo", custoDouble, "valor", valorDouble)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar snackbar = Snackbar.make(view, mensagens[3], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.GREEN);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();

                        Intent intent = new Intent(Configuracoes.this, TelaPrincipal.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar snackbar = Snackbar.make(view, mensagens[4], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    }
                });
    }

    private void IniciarComponentes(){
        button_salvar = findViewById(R.id.btnConfSalvar);
        button_voltar = findViewById(R.id.btnConfVoltar);
        editLab = findViewById(R.id.editUserConfiguracoes);
        editSenha = findViewById(R.id.editPasswordConfiguracoes);
        editConfSenha = findViewById(R.id.editPasswordConfConfiguracoes);
        editCusto = findViewById(R.id.editConfCusto);
        editValor = findViewById(R.id.editConfValor);
    }
}