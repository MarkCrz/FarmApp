package com.farmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Lotes extends AppCompatActivity {

    private ListView lvProdutosLotes;
    private AppCompatButton button_voltar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> listaProdutosLotes = new ArrayList<>();

    public String nomeProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        IniciarComponentes();


        lvProdutosLotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                nomeProduto = (lvProdutosLotes.getItemAtPosition(i).toString());

                Intent intent = new Intent(Lotes.this, LotesProdutos.class);
                intent.putExtra("nomeProduto", nomeProduto);
                startActivity(intent);
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lotes.this, TelaPrincipal.class);
                startActivity(intent);
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
                trocarTela(Lotes.this, Configuracoes.class);
                break;
            case R.id.logoutItem:
                trocarTela(Lotes.this, Login.class);
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
                        listaProdutosLotes.add(d.get("nome").toString().toUpperCase(Locale.ROOT));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Lotes.this, android.R.layout.simple_list_item_1, listaProdutosLotes
                    );

                    lvProdutosLotes.setAdapter(adapter);
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
        lvProdutosLotes = findViewById(R.id.listViewProdutosLotes);
        button_voltar = findViewById(R.id.btnVoltarLotes);
    }
}