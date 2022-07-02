package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {

    private AppCompatButton button_cadastro;
    private AppCompatButton button_logar;

    private EditText editUser, editPassword;

    private String[] mensagens = {
            "Preencha o campo de laboratório",
            "Preencha o campo de senha",
    };


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

                String user = editUser.getText().toString();
                String pass = editPassword.getText().toString();

                if (user.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (pass.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    AutenticarUsuario(user, pass, view);
                }

            }
        });
    }

    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null) {
            TelaLogada();
        }
    }

    private void TelaLogada() {
        Intent intent = new Intent(Login.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void AutenticarUsuario(String email, String senha, View view) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    TelaLogada();
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "A senha está incorreta";
                    } catch (FirebaseAuthInvalidUserException e) {
                        erro = "O usuário está incorreto ou não existe";
                    } catch (Exception e) {
                        erro = "Erro ao logar o usuário";
                    }
                    Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }

    private void IniciarComponentes(){
        button_cadastro = findViewById(R.id.btnLoginCadastrar);
        button_logar = findViewById(R.id.btnEntrar);
        editUser = findViewById(R.id.editUser);
        editPassword = findViewById(R.id.editPassword);
    }
}