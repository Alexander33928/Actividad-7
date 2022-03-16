package com.example.actividad7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText eNome;
private EditText eEmail;
private EditText eCurso;
private Button btnSalvar;
private Button btnBuscar;
private PessoaDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Cadastro");
        eNome = (EditText) findViewById(R.id.nome);
        eEmail = (EditText) findViewById(R.id.email);
        eCurso = (EditText) findViewById(R.id.curso);
        btnSalvar = (Button) findViewById(R.id.salvar);
        btnBuscar = (Button) findViewById(R.id.findAll);
        btnSalvar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        db = new PessoaDB(this);
    }

    @Override
    public void onClick(View view) {
if (view.getId()==R.id.salvar){
    Pessoa p = new Pessoa();
    p.setNome(eNome.getText().toString().trim());
    p.setCurso(eCurso.getText().toString().trim());

    db.save(p);
}
else if(view.getId()==R.id.findAll){
    List<Pessoa> pessoas = db.findAll();
    for (int i=0;i<pessoas.size();i++){
        System.out.print(pessoas.get(i).getId()+"");
        System.out.print(pessoas.get(i).getNome()+"");
        System.out.print(pessoas.get(i).getEmail()+"");
        System.out.println(pessoas.get(i).getCurso()+"");
    }
    Intent i= new Intent(this,Cadastrados.class);
    i.putExtra("objList",(Serializable) pessoas);
    startActivity(i);
}
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}