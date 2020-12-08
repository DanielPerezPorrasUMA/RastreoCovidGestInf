package com.example.rastreocovid.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BDHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NOMBRE_BASE_DATOS = "rastreo_covid_1";

    public BDHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablas(db);
        rellenarTablas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    // Métodos DML (manipular datos)
    // -----------------------------

    /**
     * Para realizar una consulta SELECT.
     * @param ctxt El contexto actual.
     * @param sentencia La sentencia a utilizar para la consulta.
     * @return Las tuplas obtenidas como resultado del SELECT.
     */
    public static List<Object[]> select(Context ctxt, String sentencia) {

        BDHelper bdh = new BDHelper(ctxt);
        SQLiteDatabase bdLegible = bdh.getReadableDatabase();
        Cursor cur = bdLegible.rawQuery(sentencia, null);

        List<Object[]> resultados = new ArrayList<>();
        if (cur.moveToFirst()) {
            do {
                int numCol = cur.getColumnCount();
                Object[] tupla = new Object[numCol];
                for (int i = 0; i < numCol; i++) {
                    switch (cur.getType(i)) {

                        case Cursor.FIELD_TYPE_BLOB:
                            tupla[i] = cur.getBlob(i);
                            break;

                        case Cursor.FIELD_TYPE_FLOAT:
                            tupla[i] = cur.getDouble(i);
                            break;

                        case Cursor.FIELD_TYPE_INTEGER:
                            tupla[i] = cur.getInt(i);
                            break;

                        case Cursor.FIELD_TYPE_STRING:
                            tupla[i] = cur.getString(i);
                            break;

                        default:
                            tupla[i] = null;
                            break;

                    }
                }
                resultados.add(tupla);
            } while (cur.moveToNext());
        }

        cur.close();
        bdLegible.close();
        bdh.close();

        return resultados;

    }

    /**
     * Para realizar una consulta SELECT que devuelva una única fila.
     * @param ctxt El contexto actual.
     * @param sentencia La sentencia a utilizar para la consulta.
     * @return La primera tupla obtenida al hacer la consulta,
     * o null si no hay tuplas en el resultado.
     */
    public static Object[] selectUnaFila(Context ctxt, String sentencia) {
        List<Object[]> listaResultado = select(ctxt, sentencia);
        if (listaResultado.size() > 0) {
            return listaResultado.get(0);
        } else {
            return null;
        }
    }

    /**
     * Para realizar una consulta SELECT que devuelva un único valor.
     * @param ctxt El contexto actual.
     * @param sentencia La sentencia a utilizar para la consulta.
     * @return El valor de la primera columna en la primera fila del resultado,
     * o null si no existe.
     */
    public static Object selectEscalar(Context ctxt, String sentencia) {
        Object[] tuplaResultado = selectUnaFila(ctxt, sentencia);
        if (tuplaResultado != null && tuplaResultado.length > 0) {
            return tuplaResultado[0];
        } else {
            return null;
        }
    }

    /**
     * Para realizar una consulta INSERT, UPDATE o DELETE.
     * @param ctxt El contexto actual.
     * @param sentencia La sentencia a utilizar para la consulta.
     */
    public static void modificar(Context ctxt, String sentencia) {
        BDHelper bdh = new BDHelper(ctxt);
        SQLiteDatabase bdEscribible = bdh.getWritableDatabase();
        bdEscribible.execSQL(sentencia);
        bdEscribible.close();
        bdh.close();
    }

    // Métodos DDL (crear y configurar las tablas)
    // -------------------------------------------

    private void crearTablas(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE \"PERSONA\" " +
                "( \"ID\" INTEGER PRIMARY KEY NOT NULL" +
                ", \"NOMBRE\" VARCHAR(100) NOT NULL" +
                ", \"APELLIDOS\" VARCHAR(100) NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE \"AMIGOS\" " +
                "( \"ID1\" INTEGER  NOT NULL" +
                ", \"ID2\" INTEGER  NOT NULL" +
                ", FOREIGN KEY(\"ID1\") REFERENCES \"PERSONA\"(\"ID\")" +
                ", FOREIGN KEY(\"ID2\") REFERENCES \"PERSONA\"(\"ID\")" +
                ", PRIMARY KEY(\"ID1\",\"ID2\")" +
                ")");

    }

    private void rellenarTablas(SQLiteDatabase db) {

        // Personas

        db.execSQL("INSERT INTO PERSONA VALUES (1, 'ANTONIO', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (2, 'JUAN', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (3, 'Carlos', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (4, 'Maria', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (5, 'Marina', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (6, 'Pilar', 'Vega')") ;

        db.execSQL("INSERT INTO PERSONA VALUES (7, 'Fernando', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (8, 'Andrea', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (9, 'Windows', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (10, 'Sergio', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (11, 'Pablo', 'Vega')") ;
        db.execSQL("INSERT INTO PERSONA VALUES (12, 'Ana', 'Vega')") ;

        // Amigos

        db.execSQL("INSERT INTO AMIGOS VALUES (1, 2) ");
        db.execSQL("INSERT INTO AMIGOS VALUES (1, 3) ");
        db.execSQL("INSERT INTO AMIGOS VALUES (1, 4) ");
        db.execSQL("INSERT INTO AMIGOS VALUES (2, 3) ");
        db.execSQL("INSERT INTO AMIGOS VALUES (2, 4) ");

        db.execSQL("INSERT INTO AMIGOS VALUES (2, 5)");
        db.execSQL("INSERT INTO AMIGOS VALUES (3, 4)");
        db.execSQL("INSERT INTO AMIGOS VALUES (3, 5)");
        db.execSQL("INSERT INTO AMIGOS VALUES (4, 6)");
        db.execSQL("INSERT INTO AMIGOS VALUES (4, 7)");

        db.execSQL("INSERT INTO AMIGOS VALUES (5, 6)");
        db.execSQL("INSERT INTO AMIGOS VALUES (5, 8)");
        db.execSQL("INSERT INTO AMIGOS VALUES (7, 8)");
        db.execSQL("INSERT INTO AMIGOS VALUES (7, 9)");
        db.execSQL("INSERT INTO AMIGOS VALUES (7, 10)");

        db.execSQL("INSERT INTO AMIGOS VALUES (8, 9)");
        db.execSQL("INSERT INTO AMIGOS VALUES (8, 11)");
        db.execSQL("INSERT INTO AMIGOS VALUES (9, 10)");
        db.execSQL("INSERT INTO AMIGOS VALUES (10, 11)");

        db.execSQL("INSERT INTO AMIGOS VALUES (10, 12)");
        db.execSQL("INSERT INTO AMIGOS VALUES (11, 12)");

    }

}
