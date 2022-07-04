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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Produtos extends AppCompatActivity {

    private ProdutosCadastrar produtosFragment = new ProdutosCadastrar();
    private ProdutosEditar produtosEditar = new ProdutosEditar();
    private AppCompatButton button_produtosCadastrar;
    private AppCompatButton button_produtosVoltar;
    private ListView lvProdutos;
    private EditText editPesquisarProdutos;

    public String nomeProduto;

    ArrayList<String> listaProdutos = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        IniciarComponentes();

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                nomeProduto = (lvProdutos.getItemAtPosition(i).toString());

                Bundle bundle = new Bundle();
                bundle.putString("nomeProduto", nomeProduto);
                produtosEditar.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.produtosEditarFrameLayout, produtosEditar).commit();
            }
        });

        button_produtosVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Produtos.this, TelaPrincipal.class);
                startActivity(intent);
            }
        });

        button_produtosCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.produtosCFrameLayout, produtosFragment).commit();
            }
        });

        editPesquisarProdutos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    //BuscarDadosPesquisar();
                }
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
                trocarTela(Produtos.this, Configuracoes.class);
                break;
            case R.id.logoutItem:

                FirebaseAuth.getInstance().signOut();

                trocarTela(Produtos.this, Login.class);
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
        db.collection("Produtos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        listaProdutos.add(d.get("nome").toString().toUpperCase(Locale.ROOT));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Produtos.this, android.R.layout.simple_list_item_1, listaProdutos
                    );

                    lvProdutos.setAdapter(adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Ocorreu um problema na busca de dados" + e.toString());
            }
        });
    }

    private void BuscarDadosPesquisar() {
        db.collection("Produtos").document(editPesquisarProdutos.getText().toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    Log.d("db_teste", value.getString("nome").toString());
                }
            }
        });
    }


    private void IniciarComponentes(){
        button_produtosCadastrar = findViewById(R.id.btnCadastrarProdutos);
        button_produtosVoltar = findViewById(R.id.btnVoltarProdutos);
        lvProdutos = findViewById(R.id.listViewProdutos);
        editPesquisarProdutos = findViewById(R.id.editPesquisarProdutos);
    }
}