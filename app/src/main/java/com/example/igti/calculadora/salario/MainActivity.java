package com.example.igti.calculadora.salario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtSalarioBruto;
    private EditText edtDependentes;
    private EditText edtOutrosDescontos;
    private Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSalarioBruto = (EditText) findViewById(R.id.edtSalario);
        edtDependentes = (EditText) findViewById(R.id.edtDependentes);
        edtOutrosDescontos = (EditText) findViewById(R.id.edtDescontos);
        btCalcular = (Button) findViewById(R.id.btCalcular);

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean calc = true;
                if(TextUtils.isEmpty(edtSalarioBruto.getText())){
                    calc = false;
                    edtSalarioBruto.setError( "Preencha o salário" );
                }
                if(TextUtils.isEmpty(edtDependentes.getText())){
                    calc = false;
                    edtDependentes.setError( "Preencha os dependentes" );
                }
                if(TextUtils.isEmpty(edtOutrosDescontos.getText())){
                    calc = false;
                    edtOutrosDescontos.setError( "Preencha os descontos" );
                }
                if(calc) {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra("salario",edtSalarioBruto.getText().toString());
                    i.putExtra("dependentes",edtDependentes.getText().toString());
                    i.putExtra("descontos",edtOutrosDescontos.getText().toString());
                    startActivity(i);
                }
            }
        });

    }
}