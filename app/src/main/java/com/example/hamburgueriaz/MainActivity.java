package com.example.hamburgueriaz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    //criar objetos
    private EditText meditTextTextNomeCliente;
    private CheckBox mcheckBoxBacon;
    private CheckBox mcheckBoxQueijo;
    private CheckBox mcheckBoxOnion;
    private Button mbuttonQuantMenos;
    private Button mbuttonQuantMais;
    private TextView mtextViewQuantidade;
    private TextView mtextViewResumoNomeCliente;
    private TextView mtextViewResumoBacon;
    private TextView mtextViewResumoQueijo;
    private TextView mtextViewResumoOnionRings;
    private TextView mtextViewResumoQuantidade;
    private TextView mtextViewResumoValorTotal;
    private Button mbuttonEnviarPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();

        //pegar o metodo e guardar na variavel
        meditTextTextNomeCliente = findViewById(R.id.editTextTextNomeCliente);
        mcheckBoxBacon = findViewById(R.id.checkBoxBacon);
        mcheckBoxQueijo = findViewById(R.id.checkBoxQueijo);
        mcheckBoxOnion = findViewById(R.id.checkBoxOnion);
        mbuttonQuantMenos = findViewById(R.id.buttonQuantMenos);
        mbuttonQuantMais = findViewById(R.id.buttonQuantMais);
        mtextViewQuantidade = findViewById(R.id.textViewQuantidade);
        mtextViewResumoNomeCliente = findViewById(R.id.textViewResumoNomeCliente);
        mtextViewResumoBacon = findViewById(R.id.textViewResumoBacon);
        mtextViewResumoQueijo = findViewById(R.id.textViewResumoQueijo);
        mtextViewResumoOnionRings = findViewById(R.id.textViewResumoOnionRings);
        mtextViewResumoQuantidade = findViewById(R.id.textViewResumoQuantidade);
        mtextViewResumoValorTotal = findViewById(R.id.textViewResumoValorTotal);
        mbuttonEnviarPedido = findViewById(R.id.buttonEnviarPedido);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });//ViewCompat

    //escutar nome digitado e guardar
        meditTextTextNomeCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCliente = String.valueOf(meditTextTextNomeCliente.getText());
                editor.putString("nome", nomeCliente);
                editor.commit();
                valorTotalPedido();

                //mtextViewResumoNomeCliente.setText(String.format("Nome Cliente: " + nomeCliente));
            }
        });

        mcheckBoxBacon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("bacon", true);
                    editor.commit();
                    valorTotalPedido();
                } else {
                    editor.putBoolean("bacon", false);
                    editor.commit();
                    valorTotalPedido();
                }
            }
        });

        mcheckBoxQueijo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("queijo", true);
                    editor.commit();
                    valorTotalPedido();
                } else {
                    editor.putBoolean("queijo", false);
                    editor.commit();
                    valorTotalPedido();
                }
            }
        });

        mcheckBoxOnion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("onionRings", true);
                    editor.commit();
                    valorTotalPedido();

                } else {
                    editor.putBoolean("onionRings", false);
                    editor.commit();
                    valorTotalPedido();

                }
            }
        });


    //fica escutando botao menos e botao mais
    mbuttonQuantMenos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            subtrair();
            int quantidade = Integer.parseInt(mtextViewQuantidade.getText().toString());
    }
    });//final botao quantidade menos

    mbuttonQuantMais.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            somar();
            int quantidade = Integer.parseInt(mtextViewQuantidade.getText().toString());
        }
    });//final botao quantidade mais

    mbuttonEnviarPedido.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enviarPedido();
        }
    });


    }//OnCreate

    private void subtrair() {
        //carrega na variável valorQuantidade o conteúdo da mtextQuantidade transformando em número
        int valorQuantidade = Integer.parseInt(mtextViewQuantidade.getText().toString());
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();


        //verifica se o valor é menor ou igual a zero... se for fica sempre zero... se não for aumenta um
        if(valorQuantidade<=0){
            valorQuantidade = 0;
            mtextViewQuantidade.setText(String.valueOf(valorQuantidade));
            mtextViewResumoQuantidade.setText(String.format("Quantidade: " + valorQuantidade));
            editor.putInt("quantidade", valorQuantidade);
            editor.commit();
            valorTotalPedido();
        }else {
            valorQuantidade = valorQuantidade - 1;
            mtextViewQuantidade.setText(String.valueOf(valorQuantidade));
            mtextViewResumoQuantidade.setText(String.format("Quantidade: " + valorQuantidade));
            editor.putInt("quantidade", valorQuantidade);
            editor.commit();
            valorTotalPedido();

        } //fim do if do subtrair
    }//fim da funcao subtrair

    private void somar(){
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();

        int valorQuantidade = Integer.parseInt(mtextViewQuantidade.getText().toString());
        valorQuantidade = valorQuantidade + 1;
            mtextViewQuantidade.setText(String.valueOf(valorQuantidade));
            mtextViewResumoQuantidade.setText(String.format("Quantidade: " + valorQuantidade));
            editor.putInt("quantidade", valorQuantidade);
            editor.commit();
        valorTotalPedido();

    }//fim da funcao somar

    private void enviarPedido(){

        valorTotalPedido();

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String nome = pref.getString("nome", null);
        String bacon = mtextViewResumoBacon.getText().toString();
        String queijo = mtextViewResumoQueijo.getText().toString();
        String onionRings = mtextViewResumoOnionRings.getText().toString();
        String quantidade = mtextViewResumoQuantidade.getText().toString();
        String valorTotal = mtextViewResumoValorTotal.getText().toString();


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email_destino@example.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de: " + nome);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Resumo do seu Pedido:\n\n"
                + "Seu nome: "+ nome + "\n"
                + bacon + "\n"
                + queijo + "\n"
                + onionRings + "\n"
                + quantidade + "\n"
                + valorTotal);
        emailIntent.setType("text/plain");

        //inicia o Intent
        try {
            startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException e) {
            //caso não haja nenhuma aplicação para enviar e-mails, pode mostrar uma mensagem ao utilizador
        }
        Toast.makeText(MainActivity.this, "enviei e-mail", Toast.LENGTH_SHORT).show();

    }//fim funcao enviar pedido

    public void valorTotalPedido(){

        float valorHamburguer = 20.00f;
        float vbacon = 0.00f;
        float vqueijo = 0.00f;
        float vonionRings = 0.00f;

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String nome = pref.getString("nome", null);
        mtextViewResumoNomeCliente.setText(String.format("Nome Cliente: " + nome));

        Boolean bacon = pref.getBoolean("bacon", false);
        if (bacon == true) {
            vbacon = 2.00f;
            mtextViewResumoBacon.setText(String.format("Tem Bacon? Sim"));
        } else {
            vbacon = 0.00f;
            mtextViewResumoBacon.setText(String.format("Tem Bacon? Não"));
        }

        Boolean queijo = pref.getBoolean("queijo", false);
        if (queijo == true) {
            vqueijo = 2.00f;
            mtextViewResumoQueijo.setText(String.format("Tem Queijo? Sim"));
        } else {
            vqueijo = 0.00f;
            mtextViewResumoQueijo.setText(String.format("Tem Queijo? Não"));
        }

        Boolean onionRings = pref.getBoolean("onionRings", false);
        if (onionRings == true) {
            vonionRings = 3.00f;
            mtextViewResumoOnionRings.setText(String.format("Tem Onion Rings? Sim"));
        } else {
            vonionRings = 0.00f;
            mtextViewResumoOnionRings.setText(String.format("Tem Onion Rings? Não"));
        }

        int quantidade = pref.getInt("quantidade", 0);

        float valorTotal = (valorHamburguer + vbacon + vqueijo + vonionRings) * quantidade;
        mtextViewResumoValorTotal.setText(String.format("Preço Final: %.2f",valorTotal));
    }

}//MainActivity