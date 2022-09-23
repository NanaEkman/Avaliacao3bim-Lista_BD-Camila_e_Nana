package br.edu.ifsp.scl.ads.pdm.avaliacaolista_bd_camilaenana;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdapterLista extends CursorAdapter {
    public AdapterLista(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        return item;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textoLista = view.findViewById(android.R.id.text1);
        String texto = cursor.getString(cursor.getColumnIndexOrThrow("texto"));
        int cor = cursor.getInt(cursor.getColumnIndexOrThrow("cor"));
        textoLista.setText(texto);
        textoLista.setTextColor(cor);
    }
}
