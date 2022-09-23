package br.edu.ifsp.scl.ads.pdm.avaliacaolista_bd_camilaenana;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Camila Devita Basaglia SC3010058 e Nana de Souza Ekman Simões SC3010414

    private Button btnAbreActivity;
    private ListView lista;

    AdapterLista adapterLista;

    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbreActivity = findViewById(R.id.btnAbreActivity);
        lista = findViewById(R.id.lista);

        btnAbreActivity.setOnClickListener(new EscutadoBotao());



        bd = openOrCreateDatabase("textosColoridos", MODE_PRIVATE, null);

        bd.execSQL("CREATE TABLE IF NOT EXISTS textos (texto VARCHAR, cor INTEGER)");

        Cursor cursor = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);

        adapterLista = new AdapterLista(this, cursor);
        lista.setAdapter(adapterLista);
        lista.setOnItemClickListener(new EscutadorLista());
        lista.setOnItemLongClickListener(new EscutadorLista());

    }

    private class EscutadoBotao implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), DigitaActivity.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String texto = i.getStringExtra("texto");
                int cor = Integer.parseInt( i.getStringExtra("cor") );

                // no retorno insere no banco e chama adapter da lista
                bd.execSQL("INSERT INTO textos(texto, cor) VALUES( '" + texto + "' , " + cor + ")");
                Cursor cursor = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);
                adapterLista.changeCursor(cursor);

            }
        }

    }

    private class EscutadorLista implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = (Cursor) adapterLista.getItem(i);

            String texto = cursor.getString(cursor.getColumnIndexOrThrow("texto"));
            int cor = cursor.getInt(cursor.getColumnIndexOrThrow("cor"));
            String textCor = "";
            switch (cor){
                case Color.RED:
                    textCor = "Vermelho";
                    break;
                case Color.GREEN:
                    textCor = "Verde";
                    break;
                case Color.BLUE:
                    textCor = "Azul";
                    break;
            }


            Toast.makeText(MainActivity.this, "Texto: " + texto + "\nCor: " + textCor, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = (Cursor) adapterLista.getItem(i);
            int rowid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

            bd.execSQL("DELETE FROM textos WHERE _rowid_ = " + rowid);

            cursor = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);
            adapterLista.changeCursor(cursor);
            return false;
        }
    }

}