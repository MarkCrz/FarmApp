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


public class ManipulacaoTerceiraPagFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_proximo;
    private ManipulacaoConclusaoFragment manipulacaoConclusaoFragment = new ManipulacaoConclusaoFragment();

//    public ManipulacaoTerceiraPagFragment() {
//        // Required empty public constructor
//    }
//    public static ManipulacaoTerceiraPagFragment newInstance(String param1, String param2) {
//        ManipulacaoTerceiraPagFragment fragment = new ManipulacaoTerceiraPagFragment();
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
        return inflater.inflate(R.layout.fragment_manipulacao_terceira_pag, container, false);
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

        button_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoConclusaoFragment).commit();
            }
        });

    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoTerPag);
        button_proximo = view.findViewById(R.id.btnProximoManipulacaoTerPag);
    }
}