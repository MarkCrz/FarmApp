package com.farmapp;

import static android.R.layout.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.farmapp.Dados.ClassListLotesProdutos;
import com.farmapp.Dados.ManipulacaoClasse;

import java.util.ArrayList;


public class ManipulacaoSegundaPagFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_proximo;
    private ManipulacaoTerceiraPagFragment manipulacaoTerceiraPagFragment = new ManipulacaoTerceiraPagFragment();
    
//    public ManipulacaoSegundaPagFragment() {
//        // Required empty public constructor
//    }
//    public static ManipulacaoSegundaPagFragment newInstance(String param1, String param2) {
//        ManipulacaoSegundaPagFragment fragment = new ManipulacaoSegundaPagFragment();
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
        return inflater.inflate(R.layout.fragment_manipulacao_segunda_pag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IniciarComponentes(view);

//        lista = new ArrayList<>();
//
//        ClassListLotesProdutos m1 = new ClassListLotesProdutos("Melatonina", "150");
//        ClassListLotesProdutos m2 = new ClassListLotesProdutos("Minoxidil", "200");
//
//
//        lista.add(m1);
//        lista.add(m2);

//        ArrayAdapter<ClassListLotesProdutos> adapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, lista
//        );
//
//        lvManipulacao.setAdapter(adapter);

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
                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoTerceiraPagFragment).commit();
            }
        });

    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoSegPag);
        button_proximo = view.findViewById(R.id.btnProximoManipulacaoSegPag);
    }


}