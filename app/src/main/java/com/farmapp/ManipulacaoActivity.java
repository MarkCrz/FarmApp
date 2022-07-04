package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.farmapp.Dados.ManipulacaoClasse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManipulacaoActivity extends AppCompatActivity {

    private AppCompatButton button_voltar;
    private AppCompatButton button_cadastrar;
    private ManipulacaoInicioFragment manipulacaoInicioFragment = new ManipulacaoInicioFragment();
    private ListView lvManipulacao;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> listaManipulacao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulacao);
        IniciarComponentes();


//        lvManipulacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoInicioFragment).commit();
//            }
//        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManipulacaoActivity.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.manipulacaoFrameLayout, manipulacaoInicioFragment).commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settingsItem:
                trocarTela(ManipulacaoActivity.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(ManipulacaoActivity.this, Login.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BuscarTabelaDados();
    }

    private void trocarTela(Activity activity, Class classe) {
        Intent intent = new Intent(activity, classe);
        startActivity(intent);
    }

    private void BuscarTabelaDados () {
        db.collection("Manipulacoes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        listaManipulacao.add(d.get("nomeCliente").toString().toUpperCase(Locale.ROOT));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            ManipulacaoActivity.this, android.R.layout.simple_list_item_1, listaManipulacao
                    );

                    lvManipulacao.setAdapter(adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Ocorreu um problema na busca de dados" + e.toString());
            }
        });
    }

    private void IniciarComponentes(){
        button_voltar = findViewById(R.id.btnVoltarManipulacao);
        button_cadastrar = findViewById(R.id.btnCadastrarManipulacao);
        lvManipulacao = findViewById(R.id.listViewManipulacao);
    }
}