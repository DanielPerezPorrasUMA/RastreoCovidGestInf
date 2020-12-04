package com.example.rastreocovid.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
