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
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManipulacaoConclusaoFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_salvar;

    private String nomeCliente, tamanhoCapsulas, volumeCapsulas, quantCapsulas;

    ArrayList<String> listaItensNome = new ArrayList<>();
    ArrayList<String> listaItensGramas = new ArrayList<>();

    private TextView capsUtilizada, quantCaps, txtItens, txtValor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Double valorTotal = 0.0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manipulacao_conclusao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IniciarComponentes(view);


        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }
        });

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastrarManipulacao(view);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        String string = "";
        nomeCliente = this.getArguments().getString("nomeCliente");
        tamanhoCapsulas = this.getArguments().getString("tamanhoCapsulas");
        volumeCapsulas = this.getArguments().getString("volumeCaps");
        quantCapsulas = this.getArguments().getString("quantCapsulas");
        listaItensNome = this.getArguments().getStringArrayList("itensNome");
        listaItensGramas = this.getArguments().getStringArrayList("itensGramas");
        for (int i = 0; i < listaItensNome.size(); i++) {
            Log.d("db_teste", listaItensNome.get(i));
            string = string + listaItensNome.get(i) + ": " + listaItensGramas.get(i) + "\n";
            BuscarProduto(listaItensNome.get(i), Double.parseDouble(listaItensGramas.get(i)));

        }
        txtItens.setText(txtItens.getText() + string + "\n");
        txtValor.setText("Valor: R$: " + valorTotal);
        capsUtilizada.setText("Cápsula utilizada: " + tamanhoCapsulas);
        quantCaps.setText("Qtd. de Cápsulas: " + quantCapsulas);

        Log.d("db_teste", nomeCliente + ", " + tamanhoCapsulas + "," + volumeCapsulas + "," + quantCapsulas);
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void BuscarProduto(String nomeProduto, Double gramas) {
        db.collection("Produtos").document(nomeProduto).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    valorTotal = value.getDouble("valor") * gramas + valorTotal;
                    txtValor.setText("Valor: R$: " + valorTotal);
                    Log.d("db_teste", "" + valorTotal);
                }
            }
        });
    }

    private void CadastrarManipulacao(View view) {
        Map<String, Object> manipulacao = new HashMap<>();
        manipulacao.put("nomeCliente", nomeCliente);
        manipulacao.put("tamanhoCapsula", tamanhoCapsulas);
        manipulacao.put("quantidadeCapsulas", quantCapsulas);
        //manipulacao.put("produtos", densidade);
        manipulacao.put("valor", valorTotal);


        DocumentReference documentReference = db.collection("Manipulacoes").document(nomeCliente);
        documentReference.set(manipulacao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar snackbar = Snackbar.make(view, "Cadastro realizado com sucesso", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.GREEN);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();

                hideFragment();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(view, "Erro ao cadastrar", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoConclusao);
        button_salvar = view.findViewById(R.id.btnSalvarManipulacaoConclusao);
        capsUtilizada = view.findViewById(R.id.txtManipulacaoConclusaoCapsulasUtilizadas);
        quantCaps = view.findViewById(R.id.txtManipulacaoConclusaoQtdCapsulas);
        txtItens = view.findViewById(R.id.txtManipulacaoConclusaoItem1);
        txtValor = view.findViewById(R.id.txtManipulacaoConclusaoValor);
    }
}