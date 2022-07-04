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
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LotesCadastrarFragment extends Fragment {

    private AppCompatButton button_Voltar, button_cadastrar;

    private EditText loteNome, loteQuantidade, loteDensidade, loteFator;

    private TextView txtLotesProdutosC;

    private String nomeProduto;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String[] mensagens = {
            "Preencha o campo de nome",
            "Preencha o campo de quantidade",
            "Preencha o campo de densidade",
            "A densidade deve ser entre 0.5 e 1.7",
            "Preencha o campo de fator de correção",
            "Cadastro realizado com sucesso",
            "Erro ao salvar o lote"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lotes_cadastrar, container, false);
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

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = loteNome.getText().toString();
                String quantidade = loteQuantidade.getText().toString();
                String densidade = loteDensidade.getText().toString();
                String fator = loteFator.getText().toString();

                if (nome.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (quantidade.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (densidade.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (fator.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[4], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    if (Double.parseDouble(densidade) < 0.5 || Double.parseDouble(densidade) > 1.7) {
                        Snackbar snackbar = Snackbar.make(view, mensagens[3], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    } else {
                        CadastrarLote(nomeProduto, (nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase()), Double.parseDouble(quantidade), Double.parseDouble(densidade), Double.parseDouble(fator), view);
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        String nome = this.getArguments().getString("nomeProduto");
        nomeProduto = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        txtLotesProdutosC.setText("Lote de " + nomeProduto);
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void CadastrarLote(String nomeProduto, String nome, Double quantidade, Double densidade, Double fator, View view) {
        Map<String, Object> lote = new HashMap<>();
        lote.put("nomeProduto", nomeProduto);
        lote.put("nome", nome);
        lote.put("quantidade", quantidade);
        lote.put("densidade", densidade);
        lote.put("fatorCorrecao", fator);


        DocumentReference documentReference = db.collection("Lotes").document(nome);
        documentReference.set(lote).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        button_Voltar = view.findViewById(R.id.btnVoltarLF);
        button_cadastrar = view.findViewById(R.id.btnCadastrarLF);
        loteNome = view.findViewById(R.id.editLCNome);
        loteQuantidade = view.findViewById(R.id.editLCQuant);
        loteDensidade = view.findViewById(R.id.editLCDensidade);
        loteFator = view.findViewById(R.id.editLCCorrecao);
        txtLotesProdutosC = view.findViewById(R.id.txtLotesProdutosC);
    }
}