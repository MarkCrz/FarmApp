package com.farmapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.farmapp.Dados.ClassListLotesProdutos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LotesProdutos extends AppCompatActivity {

    private AppCompatButton button_voltar;
    private AppCompatButton button_cadastrar;
    private ListView lvLotesProdutos;
    ArrayList<ClassListLotesProdutos> listaLotes = new ArrayList<>();
    private LotesCadastrarFragment lotesCadastrarFragment = new LotesCadastrarFragment();
    private LotesEditarFragment lotesEditarFragment = new LotesEditarFragment();

    private TextView nomeProduto;

    private String nomeLote;
    private String nomeProdutoString;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes_produtos);
        IniciarComponentes();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            nomeProduto.setText((extras.getString("nomeProduto").substring(0, 1).toUpperCase() + extras.getString("nomeProduto").substring(1).toLowerCase()));
            nomeProdutoString = extras.getString("nomeProduto").substring(0, 1).toUpperCase() + extras.getString("nomeProduto").substring(1).toLowerCase();
        }


        lvLotesProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                nomeLote = listaLotes.get(i).getLoteNome();

                Bundle bundle = new Bundle();
                bundle.putString("nomeProduto", nomeProdutoString);
                bundle.putString("nomeLote", nomeLote);
                lotesEditarFragment.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.LotesEditarFrameLayout, lotesEditarFragment).commit();
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LotesProdutos.this, Lotes.class);
                startActivity(intent);
            }
        });

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("nomeProduto", nomeProduto.getText().toString());
                lotesCadastrarFragment.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.LotesCadastrarFrameLayout, lotesCadastrarFragment).commit();
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
                trocarTela(LotesProdutos.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(LotesProdutos.this, Login.class);
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
        db.collection("Lotes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        if (d.get("nomeProduto").toString().equals(nomeProduto.getText().toString())) {
                            ClassListLotesProdutos lote = new ClassListLotesProdutos(d.get("nome").toString().toUpperCase(Locale.ROOT), d.get("quantidade").toString());
                            listaLotes.add(lote);
                        }
                    }
                    ArrayAdapter<ClassListLotesProdutos> adapter = new ArrayAdapter<>(
                            LotesProdutos.this, android.R.layout.simple_list_item_1, listaLotes
                    );

                    lvLotesProdutos.setAdapter(adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Errou ao buscar os dados" + e);
            }
        });
    }

    private void IniciarComponentes(){
        button_voltar = findViewById(R.id.btnVoltarLotesProdutos);
        button_cadastrar = findViewById(R.id.btnCadastrarLotesProdutos);
        lvLotesProdutos = findViewById(R.id.listViewProdutosLotes);
        nomeProduto = findViewById(R.id.txtLotesProdutosNome);
    }
}