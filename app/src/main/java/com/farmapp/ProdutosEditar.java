package com.farmapp;

import android.app.ActivityManager;
import android.app.DirectAction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProdutosEditar extends Fragment {

    private AppCompatButton button_Voltar, button_salvar;

    private EditText editNome, editCusto, editMargem, editValor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_produtos_editar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IniciarComponentes(view);

        button_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }
        });

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editNome.getText().toString();
                String custo = editCusto.getText().toString();
                String margem = editMargem.getText().toString();
                String valor = editValor.getText().toString();

                AtualizarDados(nome, Double.parseDouble(custo), Double.parseDouble(margem), Double.parseDouble(valor), view);
            }
        });

        editMargem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String margem = editMargem.getText().toString();
                    String custo = editCusto.getText().toString();
                    Double valorConta;

                    if (!margem.isEmpty() && !custo.isEmpty()) {
                        valorConta = Double.parseDouble(custo) + (Double.parseDouble(custo) * (Double.parseDouble(margem) / 100));
                        editValor.setText(valorConta.toString());
                    } else {
                        editValor.setText("");
                    }
                }
            }
        });

        editCusto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String margem = editMargem.getText().toString();
                    String custo = editCusto.getText().toString();
                    Double valorConta;

                    if (!margem.isEmpty() && !custo.isEmpty()) {
                        valorConta = Double.parseDouble(custo) + (Double.parseDouble(custo) * (Double.parseDouble(margem) / 100));
                        editValor.setText(valorConta.toString());
                    } else {
                        editValor.setText("");
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String nome = this.getArguments().getString("nomeProduto");
        editNome.setText(nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase());
        pegarDados();
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void pegarDados() {
        db.collection("Produtos").document(editNome.getText().toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    editCusto.setText(value.getDouble("custo").toString());
                    editMargem.setText(value.getDouble("margem").toString());
                    editValor.setText(value.getDouble("valor").toString());
                }
            }
        });
    }

    private void AtualizarDados(String nome, Double custo, Double margem, Double valor, View view) {
        db.collection("Produtos").document(nome)
                .update("custo", custo, "margem", margem, "valor", valor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar snackbar = Snackbar.make(view, "Produto alterado com sucesso", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.GREEN);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();

                        hideFragment();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(view, "Não foi possivel alterar o produto", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }

    private void IniciarComponentes(@NonNull View view){
        button_Voltar = view.findViewById(R.id.btnEVoltarPF);
        button_salvar = view.findViewById(R.id.btnESalvarPF);
        editNome = view.findViewById(R.id.editPENome);
        editCusto = view.findViewById(R.id.editPECusto);
        editMargem = view.findViewById(R.id.editPEMargem);
        editValor = view.findViewById(R.id.editPEValorFinal);
    }
}