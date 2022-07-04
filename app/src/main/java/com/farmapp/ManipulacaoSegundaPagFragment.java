package com.farmapp;

import static android.R.layout.*;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.farmapp.Dados.ClassListLotesProdutos;
import com.farmapp.Dados.ManipulacaoClasse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ManipulacaoSegundaPagFragment extends Fragment {
    private AppCompatButton button_voltar;
    private AppCompatButton button_proximo, button_adicionar;
    private ManipulacaoConclusaoFragment manipulacaoConclusaoFragment = new ManipulacaoConclusaoFragment();

    private EditText editQuant, editMSegPagQtdProduto;

    private TextView txtVolume;

    private String nomeCliente, tamanhoCapsulas;

    private Spinner spinner;
    ArrayList<String> listaProdutos = new ArrayList<>();

    private ListView lvProdutos;
    ArrayList<ClassListLotesProdutos> listaItens = new ArrayList<>();

    ArrayList<String> listaItensNome = new ArrayList<>();
    ArrayList<String> listaItensGramas = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Double valor = 0.0;

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

        if (listaProdutos.isEmpty()) {
            BuscarTabelaDados(view);
        }

        ArrayAdapter<ClassListLotesProdutos> adapter = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_list_item_1, listaItens
        );

        lvProdutos.setAdapter(adapter);


        button_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantCapsulas = editQuant.getText().toString();
                String quantProduto = editMSegPagQtdProduto.getText().toString();

                if (quantCapsulas.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Preencha o campo de quantidade de capsulas", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (quantProduto.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Preencha o campo de quantidade do produto", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    BuscarDadosLotes(spinner.getSelectedItem().toString(), Double.parseDouble(quantCapsulas), Double.parseDouble(quantProduto), view);

                }
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }
        });

        button_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantCaps = editQuant.getText().toString();


                if (quantCaps.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Preencha a quantidade de capsulas", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (listaItens.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Adicione um produto pelo menos", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                } else {
                    Double excipiente = 0.0;
                    Double capsulas = valor;
                    Double capsulasMult = 1.0;

                    if (tamanhoCapsulas == "000") {
                        if (valor > 1.37) {
                            while (capsulas > 1.37) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (1.37 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "00") {
                        if (valor > 0.95) {
                            while (capsulas > 0.95) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.95 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "0") {
                        if (valor > 0.68) {
                            while (capsulas > 0.68) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.68 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "1") {
                        if (valor > 0.50) {
                            while (capsulas > 0.50) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.50 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "2") {
                        if (valor > 0.37) {
                            while (capsulas > 0.37) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.37 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "3") {
                        if (valor > 0.30) {
                            while (capsulas > 0.30) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.30 - (capsulas / capsulasMult)) * 0.7;
                    } else if (tamanhoCapsulas == "4") {
                        if (valor > 0.21) {
                            while (capsulas > 0.21) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.21 - (capsulas / capsulasMult)) * 0.7;
                    } else {
                        if (valor > 0.17) {
                            while (capsulas > 0.17) {
                                capsulas = capsulas / 2;
                                if (capsulasMult == 1.0) {
                                    capsulasMult = 2.0;
                                } else {
                                    capsulasMult = capsulasMult + 1;
                                }
                            }
                        }
                        excipiente = (0.17 - (capsulas / capsulasMult)) * 0.7;
                    }

                    listaItensNome.add("Excipiente");
                    listaItensGramas.add(excipiente.toString());

                    Double quantCapsulas = (Double.parseDouble(quantCaps) * capsulasMult);

                    Bundle bundle = new Bundle();
                    bundle.putString("nomeCliente", nomeCliente);
                    bundle.putString("tamanhoCapsulas", tamanhoCapsulas);
                    bundle.putString("quantCapsulas", quantCapsulas.toString());
                    bundle.putString("volumeCaps", valor.toString());
                    bundle.putStringArrayList("itensNome", listaItensNome);
                    bundle.putStringArrayList("itensGramas", listaItensGramas);
                    manipulacaoConclusaoFragment.setArguments(bundle);

                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.manipulacaoFrameLayout, manipulacaoConclusaoFragment).commit();
                }


            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        nomeCliente = this.getArguments().getString("nomeCliente");
        tamanhoCapsulas = this.getArguments().getString("tamanhoCapsulas");
    }

    private void hideFragment () {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this).commit();
    }

    private void BuscarTabelaDados (View view) {
        db.collection("Produtos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        listaProdutos.add(d.get("nome").toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, listaProdutos);

                    spinner.setAdapter(adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Errou aou bsucar dados" + e);
            }
        });
    }

    private void BuscarDadosLotes (String nomeProduto, Double quantCapsulas, Double quantProduto, View view) {
        db.collection("Lotes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    Boolean verificador = false;
                    Double calculo = 0.0;
                    Double calcPadrao = 0.0;
                    for (DocumentSnapshot d : list) {
                        if (d.get("nomeProduto").toString().equals(nomeProduto)) {
                            verificador = true;

                            calcPadrao = (quantProduto * Double.parseDouble(d.get("fatorCorrecao").toString())) / Double.parseDouble(d.get("densidade").toString());
                            calculo = quantProduto * Double.parseDouble(d.get("fatorCorrecao").toString()) * quantCapsulas;
                        }
                    }
                    if (verificador) {
                        valor = valor + calcPadrao;
                        txtVolume.setText("Volume por cáp. " + valor + "ml");
                        ClassListLotesProdutos lote = new ClassListLotesProdutos(spinner.getSelectedItem().toString(), quantProduto.toString());



                        listaItensNome.add(spinner.getSelectedItem().toString());
                        listaItensGramas.add(calculo.toString());
                        listaItens.add(lote);

                        listaProdutos.remove(spinner.getSelectedItem().toString());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), simple_spinner_dropdown_item, listaProdutos);

                        spinner.setAdapter(adapter);

                        ArrayAdapter<ClassListLotesProdutos> adapter2 = new ArrayAdapter<>(
                                view.getContext(), simple_list_item_1, listaItens
                        );

                        lvProdutos.setAdapter(adapter2);
                    } else {
                        Snackbar snackbar = Snackbar.make(view, "Produto não possui lote", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Errou ao buscar os dados" + e);
            }
        });
    }

    private void IniciarComponentes(@NonNull View view){
        button_voltar = view.findViewById(R.id.btnVoltarManipulacaoSegPag);
        button_proximo = view.findViewById(R.id.btnProximoManipulacaoSegPag);
        button_adicionar = view.findViewById(R.id.btnMSegPagAdicionar);
        spinner = view.findViewById(R.id.spinnerMSegPag);
        editQuant = view.findViewById(R.id.editMSegPagQtd);
        editMSegPagQtdProduto = view.findViewById(R.id.editMSegPagQtdProduto);
        lvProdutos = view.findViewById(R.id.listViewManipulacaoSegPag);
        txtVolume = view.findViewById(R.id.txtManipulacaoSegPagVolume);
    }


}