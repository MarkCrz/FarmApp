package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.provider.FirebaseInitProvider;

import java.util.HashMap;
import java.util.Map;

public class Cadastrar extends AppCompatActivity {

    private AppCompatButton button_cadastrar;
    private AppCompatButton button_voltar;

    private EditText editUserCadastrar, editUserEmail, editPasswordCadastrar, editPasswordConfCadastrar;
    String[] mensagens = {
            "Preencha o campo de laboratório",
            "Preencha o campo de email",
            "Preencha o campo de senha",
            "Preencha o campo de confirmar senha",
            "A senha não está igual",
            "Cadastro realizado com sucesso"};

    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        getSupportActionBar().hide();
        IniciarComponentes();

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lab = editUserCadastrar.getText().toString();
                String email = editUserEmail.getText().toString();
                String senha = editPasswordCadastrar.getText().toString();
                String confSenha = editPasswordConfCadastrar.getText().toString();

                if (lab.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();

                } else if (email.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (senha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (confSenha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[3], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    if (!senha.equals(confSenha)) {
                        Snackbar snackbar = Snackbar.make(view, mensagens[4], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    } else {
                        CadastrarUsuario(lab, email, senha, view);
                    }
                }
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastrar.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CadastrarUsuario(String lab, String email, String senha, View view) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    SalvarDados(lab, senha);

                    Snackbar snackbar = Snackbar.make(view, mensagens[5], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.GREEN);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "A senha deve ter pelo menos 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email já cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }

    private void SalvarDados(String lab, String senha) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("laboratorio", lab);
        usuarios.put("senha", senha);
        usuarios.put("custo", 0);
        usuarios.put("valor", 0);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar od dados" + e.toString());
            }
        });

        Intent intent = new Intent(Cadastrar.this, Login.class);
        startActivity(intent);
        finish();

    }

    private void IniciarComponentes(){
        button_cadastrar = findViewById(R.id.btnCadastrar);
        button_voltar = findViewById(R.id.btnCadastrarVoltar);

        editUserCadastrar = findViewById(R.id.editUserCadastrar);
        editUserEmail = findViewById(R.id.editUserEmail);
        editPasswordCadastrar = findViewById(R.id.editPasswordCadastrar);
        editPasswordConfCadastrar = findViewById(R.id.editPasswordConfCadastrar);
    }
}