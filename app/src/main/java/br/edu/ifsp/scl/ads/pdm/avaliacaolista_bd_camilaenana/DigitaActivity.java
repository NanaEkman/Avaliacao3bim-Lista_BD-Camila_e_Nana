package br.edu.ifsp.scl.ads.pdm.avaliacaolista_bd_camilaenana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DigitaActivity extends AppCompatActivity {

    private EditText txtTexto;
    private RadioGroup radioGroup;
    private RadioButton radVermelho;
    private RadioButton radVerde;
    private RadioButton radAzul;
    private Button btnInsere;
    private Button btnCancela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digita);

        txtTexto = findViewById(R.id.txtTexto);
        radioGroup = findViewById(R.id.radioGroup);
        radVermelho = findViewById(R.id.radVermelho);
        radAzul = findViewById(R.id.radAzul);
        radVerde = findViewById(R.id.radVerde);
        btnInsere = findViewById(R.id.btnInsere);
        btnCancela = findViewById(R.id.btnCancela);

        btnInsere.setOnClickListener(new EscutadoBotaoInsere());
        btnCancela.setOnClickListener(new EscutadoBotaoCancela());

    }

    private class EscutadoBotaoInsere implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String text = txtTexto.getText().toString();

            if(text.isEmpty() || text == null){
                Toast.makeText(DigitaActivity.this, "Digite algum texto!!!!", Toast.LENGTH_SHORT).show();
            }else{
                int radSelecionado = radioGroup.getCheckedRadioButtonId();

                if(radSelecionado == -1){
                    Toast.makeText(DigitaActivity.this, "Obrigatório escolher uma cor!!!!", Toast.LENGTH_SHORT).show();
                }else{
                    int intCor = 0;
                    switch (radSelecionado){
                        case R.id.radVermelho:
                            intCor = Color.RED;
                            break;
                        case R.id.radVerde:
                            intCor = Color.GREEN;
                            break;
                        case R.id.radAzul:
                            intCor = Color.BLUE;
                            break;
                    }

                    //voltar para a main
                    Intent i = new Intent();
                    i.putExtra("texto", text);
                    i.putExtra("cor", Integer.toString(intCor));
                    setResult(RESULT_OK, i);
                    finish();

                }
            }



        }
    }

    private class EscutadoBotaoCancela implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            setResult(RESULT_CANCELED, i);
            finish();
        }
    }

}