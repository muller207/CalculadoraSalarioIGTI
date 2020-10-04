package com.example.igti.calculadora.salario;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView txSalario;
    private TextView txINSS;
    private TextView txIRRF;
    private TextView txOutrosDescontos;
    private TextView txSalarioLiquido;
    private TextView txDescontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txSalario = (TextView) findViewById(R.id.txSalarioBruto);
        txINSS = (TextView) findViewById(R.id.txINSS);
        txIRRF = (TextView) findViewById(R.id.txIRRF);
        txOutrosDescontos = (TextView) findViewById(R.id.txOutrosDescontos);
        txSalarioLiquido = (TextView) findViewById(R.id.txSalarioLiquido);
        txDescontos = (TextView) findViewById(R.id.txDescontos);
        Button btVoltar = (Button) findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calcular();
    }

    protected void calcular() {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
        NumberFormat pctf = new DecimalFormat("#,##0.00%", new DecimalFormatSymbols(new Locale ("pt", "BR")));

        Intent i = getIntent();
        String salario = i.getStringExtra("salario");
        String dependentes = i.getStringExtra("dependentes");
        String descontos = i.getStringExtra("descontos");
        double dSalario = Double.parseDouble(salario);
        double dDependentes = Double.parseDouble(dependentes);
        double dDescontos = Double.parseDouble(descontos);
        double dINSS = calculaINSS(dSalario);
        double dBaseCalc = dSalario - dINSS - dDependentes*189.59;
        double dIRRF = calculaIRFF(dBaseCalc);
        double pDescontos = (dDescontos + dINSS + dIRRF) / dSalario;
        txSalario.setText(nf.format(dSalario));
        txINSS.setText(nf.format(dINSS));
        txIRRF.setText(nf.format(dIRRF));
        txOutrosDescontos.setText(nf.format(dDescontos));
        txSalarioLiquido.setText(nf.format(dSalario-dINSS-dIRRF-dDescontos));
        txDescontos.setText(pctf.format(pDescontos));
    }

    protected double calculaINSS(double dSalario){
        if (dSalario <= 1045) {
            return (dSalario * 0.075);
        } else if(dSalario >1045 && dSalario <=2089.6) {
            return (dSalario * 0.09) - 15.67;
        }else if(dSalario >2089.6 && dSalario <=3134.4){
            return (dSalario * 0.12) - 78.36;
        }else if(dSalario >3134.4 && dSalario <=6101.06){
            return (dSalario * 0.14) - 141.05;
        }else{
            return 713.1d;
        }
    }

    protected double calculaIRFF(double dBaseCalc){
        if (dBaseCalc <= 1903.98) {
            return 0d;
        } else if(dBaseCalc >1903.98 && dBaseCalc <=2826.65) {
            return (dBaseCalc * 0.075) - 142.8;
        }else if(dBaseCalc >2826.65 && dBaseCalc <=3751.05){
            return (dBaseCalc * 0.15) - 354.8;
        }else if(dBaseCalc >3751.05 && dBaseCalc <=4664.68){
            return (dBaseCalc * 0.225) - 636.13;
        }else{
            return (dBaseCalc * 0.275) - 869.36;
        }
    }
}