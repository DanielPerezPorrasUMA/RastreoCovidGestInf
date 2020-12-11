package com.example.rastreocovid.controlGUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rastreocovid.R;
import com.example.rastreocovid.modelo.Debug;
import com.example.rastreocovid.modelo.Persona;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linealPersonas, linealAmigos, linealDisponibles;
    //private List<Integer> listaPersonas = new ArrayList<>(); // Lista para ID de personas
    private List<Persona> listaAmigos = new ArrayList<>();
    //private List<Persona> listaDisponibles;
    private List<Persona> listaPersonas = new ArrayList<>(); // Lista para personas

    private Button btnQuitar, btnAniadir;
    private List<TableLayout> tablasPersonas = new ArrayList<>();
    private List<TableLayout> tablasAmigos;
    private List<TableLayout> tablasDisponibles;

    private int numeroPersonas = 0;
    private int idSeleccionado = -1;
    private int idAnterior = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
        mostrarInfoDebug();
        mostrarListaPersonas();
    }

    private void mostrarListaPersonas() {
        listaPersonas = Persona.getPersonas(this);
        for(Persona p : listaPersonas) {mostrarPersona(p);}
    }

    private void mostrarPersona(Persona p) { // En progreso
        View vistaP = getLayoutInflater().inflate(R.layout.activity_persona, null);

        vistaP.setId(p.getId());

        TextView id = (TextView) vistaP.findViewById(R.id.txtPersonaID);
        id.setText(" "+p.getId());

        TextView nombre = (TextView) vistaP.findViewById(R.id.txtPersonaNombre);
        nombre.setText(p.toString());

        TableLayout t = (TableLayout) vistaP.findViewById(R.id.tlPersonas);
        tablasPersonas.add(t);

        t.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t.setBackgroundColor(Color.CYAN);

                if(idSeleccionado > -1) { // Solución Temporal
                    //tablas.get(idAnterior).setBackgroundColor(Color.WHITE);
                    for(TableLayout tAux : tablasPersonas) {
                        if (!tAux.equals(t)){
                            tAux.setBackgroundColor(Color.WHITE);
                        }
                    }
                }

                idAnterior = idSeleccionado;
                idSeleccionado = p.getId();
                // mostrar amigos y disponibles
                mostrarAmigosYDisponibles();
            }
        });

        linealPersonas.addView(vistaP);
        numeroPersonas++;
    }

    private void enlazarControles() {
        btnQuitar = findViewById(R.id.btnQuitar);
        btnAniadir = findViewById(R.id.btnAniadir);
        linealPersonas = findViewById(R.id.linealPersonas);
        linealAmigos = findViewById(R.id.linealAmigos);
        linealDisponibles = findViewById(R.id.linealDisponibles);

        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // Try y Catch?
    private void mostrarAmigosYDisponibles() {
        tablasAmigos = new ArrayList<>();
        tablasDisponibles = new ArrayList<>();
        linealAmigos.removeAllViews();
        linealDisponibles.removeAllViews();

        Persona p = listaPersonas.get(idSeleccionado);
        listaAmigos = p.getAmigos();

        for (Persona amigo : listaAmigos) {
            mostrarAmigo(amigo);
        }

        for (Persona disponible : listaPersonas) {
            if ((disponible.getId() != p.getId()) && !listaAmigos.contains(disponible)) {
                mostrarDisponible(disponible);
            }
        }
    }

    private void mostrarAmigo(Persona a) {
        View vistaA = getLayoutInflater().inflate(R.layout.activity_persona, null);

        vistaA.setId(a.getId());

        TextView id = (TextView) vistaA.findViewById(R.id.txtPersonaID);
        id.setText(" "+a.getId());

        TextView nombre = (TextView) vistaA.findViewById(R.id.txtPersonaNombre);
        nombre.setText(a.toString());

        TableLayout t = (TableLayout) vistaA.findViewById(R.id.tlPersonas);
        tablasAmigos.add(t);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.setBackgroundColor(Color.CYAN);
                if(idSeleccionado > -1) { // Solución Temporal
                    for(TableLayout tAux : tablasAmigos) {
                        if (!tAux.equals(t)){
                            tAux.setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            }
        });

        linealAmigos.addView(vistaA);
    }

    private void mostrarDisponible(Persona d) {
        View vistaD = getLayoutInflater().inflate(R.layout.activity_persona, null);

        vistaD.setId(d.getId());

        TextView id = (TextView) vistaD.findViewById(R.id.txtPersonaID);
        id.setText(" "+d.getId());

        TextView nombre = (TextView) vistaD.findViewById(R.id.txtPersonaNombre);
        nombre.setText(d.toString());

        TableLayout t = (TableLayout) vistaD.findViewById(R.id.tlPersonas);
        tablasDisponibles.add(t);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.setBackgroundColor(Color.CYAN);
                if(idSeleccionado > -1) { // Solución Temporal
                    for(TableLayout tAux : tablasDisponibles) {
                        if (!tAux.equals(t)){
                            tAux.setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            }
        });

        linealDisponibles.addView(vistaD);
    }

    private void aniadirAmigo() {
    }

    private void quitarAmigo() {
    }

    private void limpiarAmigosYDisponibles() {

    }

    private void mostrarInfoDebug() {
        Context context = getApplicationContext();
        Debug.mostrarPersonasPorConsola(context);
        Debug.mostrarAmigosPorConsola(context);
    }

}