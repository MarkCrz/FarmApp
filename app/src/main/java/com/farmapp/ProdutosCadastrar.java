package com.farmapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProdutosCadastrar extends Fragment {

    private AppCompatButton button_Cadastrar;
    private AppCompatButton button_Voltar;

//    public ProdutosCadastrar() {
//        // Required empty public constructor
//    }
//
//    public static ProdutosCadastrar newInstance(String param1, String param2) {
//        ProdutosCadastrar fragment = new ProdutosCadastrar();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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

            }
        });
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }


    private void IniciarComponentes(@NonNull View view){
        button_Cadastrar = view.findViewById(R.id.btnCadastrarPF);
        button_Voltar = view.findViewById(R.id.btnVoltarPF);
    }
}