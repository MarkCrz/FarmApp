package com.farmapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class LotesEditarFragment extends Fragment {

    private AppCompatButton button_Voltar, button_salvar;

    private TextView txtLoteProduto, txtLote;

    private EditText editQuant, editDensidade, editFator;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String[] mensagens = {
            "Preencha o campo de quantidade",
            "Preencha o campo de densidade",
            "A densidade deve ser entre 0.5 e 1.7",
            "Preencha o campo de fator de correção",
            "Lote salvo com sucesso",
            "Erro ao salvar o lote"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lotes_editar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IniciarComponentes(view);

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantidade = editQuant.getText().toString();
                String densidade = editDensidade.getText().toString();
                String fator = editFator.getText().toString();

                if (quantidade.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (densidade.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (fator.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[3], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    if (Double.parseDouble(densidade) < 0.5 || Double.parseDouble(densidade) > 1.7) {
                        Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    } else {
                        AtualizarDados(Double.parseDouble(quantidade), Double.parseDouble(densidade), Double.parseDouble(fator), view);
                    }
                }
            }
        });

        button_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        String nomeProduto = this.getArguments().getString("nomeProduto");
        String nomeLote = this.getArguments().getString("nomeLote");
        txtLoteProduto.setText("Lote de " + nomeProduto.substring(0, 1).toUpperCase() + nomeProduto.substring(1).toLowerCase());
        txtLote.setText("Lote " + nomeLote);
        BuscarDados();
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void BuscarDados() {
        db.collection("Lotes").document(this.getArguments().getString("nomeLote")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    editQuant.setText(value.getDouble("quantidade").toString());
                    editDensidade.setText(value.getDouble("densidade").toString());
                    editFator.setText(value.getDouble("fatorCorrecao").toString());
                }
            }
        });
    }

    private void AtualizarDados(Double quantidade, Double densidade, Double fator, View view) {
        db.collection("Lotes").document(this.getArguments().getString("nomeLote"))
                .update("quantidade", quantidade, "densidade", densidade, "fatorCorrecao", fator)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
        button_Voltar = view.findViewById(R.id.btnVoltarLFE);
        button_salvar = view.findViewById(R.id.btnSalvarLFE);
        txtLoteProduto = view.findViewById(R.id.txtLotesProdutosE);
        txtLote = view.findViewById(R.id.txtLotesE);
        editQuant = view.findViewById(R.id.editLEQuant);
        editDensidade = view.findViewById(R.id.editLEDensidade);
        editFator = view.findViewById(R.id.editLECorrecao);
    }
}