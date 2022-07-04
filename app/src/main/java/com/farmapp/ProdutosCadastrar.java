package com.farmapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProdutosCadastrar extends Fragment {

    private AppCompatButton button_Cadastrar;
    private AppCompatButton button_Voltar;

    private EditText editNome, editCusto, editMargem, editValor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String[] mensagens = {
            "Preencha o campo de nome",
            "Preencha o campo de custo",
            "Preencha o campo de margem",
            "Preencha o campo de custo e margem",
            "Cadastro realizado com sucesso",
            "Erro ao salvar o produto"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produtos_cadastrar, container, false);
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

        button_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editNome.getText().toString();
                String custo = editCusto.getText().toString();
                String margem = editMargem.getText().toString();
                String valor = editValor.getText().toString();

                if (nome.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (custo.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (margem.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (valor.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[3], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    CadastrarProduto((nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase()), Double.parseDouble(custo), Double.parseDouble(margem), Double.parseDouble(valor), view);
                }
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

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void CadastrarProduto (String nome, Double custo, Double margem, Double valor, View view){
        Map<String, Object> produto = new HashMap<>();
        produto.put("nome", nome);
        produto.put("custo", custo);
        produto.put("margem", margem);
        produto.put("valor", valor);

        DocumentReference documentReference = db.collection("Produtos").document(nome);
        documentReference.set(produto).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar snackbar = Snackbar.make(view, mensagens[4], Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.GREEN);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();

                hideFragment();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(view, mensagens[5], Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }


    private void IniciarComponentes(@NonNull View view){
        button_Cadastrar = view.findViewById(R.id.btnCadastrarPF);
        button_Voltar = view.findViewById(R.id.btnVoltarPF);
        editNome = view.findViewById(R.id.editPCNome);
        editCusto = view.findViewById(R.id.editPCCusto);
        editMargem = view.findViewById(R.id.editPCMargem);
        editValor = view.findViewById(R.id.editPCValorFinal);
    }
}