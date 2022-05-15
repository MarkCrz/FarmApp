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

public class ManipulacaoConclusaoFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_salvar;

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

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoConclusao);
        button_salvar = view.findViewById(R.id.btnSalvarManipulacaoConclusao);
    }
}