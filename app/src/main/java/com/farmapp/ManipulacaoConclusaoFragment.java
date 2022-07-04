package com.farmapp;

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

import java.util.ArrayList;

public class ManipulacaoConclusaoFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_salvar;

    private String nomeCliente, tamanhoCapsulas, volumeCapsulas, quantCapsulas;

    ArrayList<String> listaItensNome = new ArrayList<>();
    ArrayList<String> listaItensGramas = new ArrayList<>();

    private TextView capsUtilizada, quantCaps, txtItens;

//    public ManipulacaoConclusaoFragment() {
//        // Required empty public constructor
//    }
//    public static ManipulacaoConclusaoFragment newInstance(String param1, String param2) {
//        ManipulacaoConclusaoFragment fragment = new ManipulacaoConclusaoFragment();
//        Bundle args = new Bundle();
//        return fragment;
//    }

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


    }

    @Override
    public void onStart() {
        super.onStart();
        nomeCliente = this.getArguments().getString("nomeCliente");
        tamanhoCapsulas = this.getArguments().getString("tamanhoCapsulas");
        volumeCapsulas = this.getArguments().getString("volumeCaps");
        quantCapsulas = this.getArguments().getString("quantCapsulas");
        listaItensNome = this.getArguments().getStringArrayList("itensNome");
        listaItensGramas = this.getArguments().getStringArrayList("itensGramas");
        for (int i = 0; i < listaItensNome.size(); i++) {
            txtItens.setText(txtItens.getText() + "\n" + listaItensNome.get(i) + ": " + listaItensGramas.get(i));

        }
        capsUtilizada.setText("Cápsula utilizada: " + tamanhoCapsulas);
        quantCaps.setText("Qtd. de Cápsulas: " + quantCapsulas);

        Log.d("db_teste", nomeCliente + ", " + tamanhoCapsulas + "," + volumeCapsulas + "," + quantCapsulas);
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoConclusao);
        button_salvar = view.findViewById(R.id.btnSalvarManipulacaoConclusao);
        capsUtilizada = view.findViewById(R.id.txtManipulacaoConclusaoCapsulasUtilizadas);
        quantCaps = view.findViewById(R.id.txtManipulacaoConclusaoQtdCapsulas);
        txtItens = view.findViewById(R.id.txtManipulacaoConclusaoItem1);
    }
}