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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ManipulacaoInicioFragment extends Fragment {
    private ManipulacaoSegundaPagFragment manipulacaoSegundaPagFragment = new ManipulacaoSegundaPagFragment();

    private AppCompatButton button_Voltar;
    private AppCompatButton button_proximo;

    private Spinner tamCapsulas;

    ArrayList<String> listaCapsulas = new ArrayList<>();

    private EditText nomeCliente;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manipulacao_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IniciarComponentes(view);

        if(listaCapsulas.isEmpty()) {
            listaCapsulas.add("000");
            listaCapsulas.add("00");
            listaCapsulas.add("0");
            listaCapsulas.add("1");
            listaCapsulas.add("2");
            listaCapsulas.add("3");
            listaCapsulas.add("4");
            listaCapsulas.add("5");
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, listaCapsulas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tamCapsulas.setAdapter(adapter);


        button_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }
        });

        button_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeCliente.getText().toString();
                String tamCap = tamCapsulas.getSelectedItem().toString();

                if (nome.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Preencha o campo nome", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("nomeCliente", nome);
                    bundle.putString("tamanhoCapsulas", tamCap);
                    manipulacaoSegundaPagFragment.setArguments(bundle);

                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.manipulacaoFrameLayout, manipulacaoSegundaPagFragment).commit();
                }
            }
        });

    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void IniciarComponentes(@NonNull View view){
        button_Voltar = view.findViewById(R.id.btnVoltarManipulacaoInicio);
        button_proximo = view.findViewById(R.id.btnProximoManipulacaoInicio);
        tamCapsulas = view.findViewById(R.id.editMITamanhoCap);
        nomeCliente = view.findViewById(R.id.editMINomeCliente);
    }
}