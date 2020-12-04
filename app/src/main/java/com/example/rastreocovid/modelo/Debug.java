package com.example.rastreocovid.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Debug {

    private static final String DEBUG_TAG = "RastreoCovid";

    public static void mostrarPersonasPorConsola(Context context) {

        BDHelper bdh = new BDHelper(context);
        SQLiteDatabase bdLegible = bdh.getReadableDatabase();
        Cursor cur = bdLegible.rawQuery("SELECT ID, NOMBRE, APELLIDOS FROM PERSONA", null);

        if (cur.moveToFirst()) {
            Log.i(DEBUG_TAG, "Personas en la base de datos:");
            do {
                int id = cur.getInt(0);
                String nombre = cur.getString(1);
                String apellidos = cur.getString(2);
                Log.i(DEBUG_TAG, "[" + id + "] " + nombre + " " + apellidos);
            } while (cur.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No hay personas en la base de datos");
        }

        cur.close();
        bdLegible.close();
        bdh.close();

    }

    public static void mostrarAmigosPorConsola(Context context) {

        BDHelper bdh = new BDHelper(context);
        SQLiteDatabase bdLegible = bdh.getReadableDatabase();
        Cursor cur = bdLegible.rawQuery("SELECT ID1, ID2 FROM AMIGOS", null);

        if (cur.moveToFirst()) {
            Log.i(DEBUG_TAG, "Amigos en la base de datos:");
            do {
                int id1 = cur.getInt(0);
                int id2 = cur.getInt(1);
                Log.i(DEBUG_TAG, "(" + id1 + ", " + id2 + ")");
            } while (cur.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No hay amigos en la base de datos");
        }

        cur.close();
        bdLegible.close();
        bdh.close();

    }

}
