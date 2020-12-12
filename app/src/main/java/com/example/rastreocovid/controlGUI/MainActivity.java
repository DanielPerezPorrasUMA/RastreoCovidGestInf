package com.example.rastreocovid.controlGUI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rastreocovid.R;
import com.example.rastreocovid.modelo.Debug;
import com.example.rastreocovid.modelo.Persona;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    private LinearLayout linealPersonas, linealAmigos, linealDisponibles;
    //private List<Integer> listaPersonas = new ArrayList<>(); // Lista para ID de personas
    private List<Persona> listaPersonas = new ArrayList<>(); // Lista para personas
    private List<Persona> listaAmigos = new ArrayList<>();
    //private List<Persona> listaDisponibles;

    private Button btnQuitar, btnAniadir;
    private final List<TableLayout> tablasPersonas = new ArrayList<>();
    private final List<TableLayout> tablasAmigos = new ArrayList<>();
    private final List<TableLayout> tablasDisponibles = new ArrayList<>();

    private int numeroPersonas = 0; // Número de personas en la lista

    // IDs ¡cuidado con no confundirlos!
    private int idSeleccionadoBD = -1; // ID de la persona seleccionada en la BD
    private int idSeleccionado = -1; // ID de la persona seleccionada en la lista Personas
    private int idAnterior = -1; // ID de la persona anteriormente seleccionada en la lista Personas


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
        for (Persona p : listaPersonas) {
            mostrarPersona(p);
        }

        // Asignamos listeners después de insertar las
        // filas en la lista
        setListenersPersonas();

    }
    private void mostrarPersona(Persona p) {
        View vistaP = getLayoutInflater().inflate(R.layout.activity_persona, linealPersonas, false);
        vistaP.setId(p.getId());

        // Rellenar campos del layout "inflado"
        TextView id = (TextView) vistaP.findViewById(R.id.txtPersonaID);
        id.setText(" " + p.getId());
        TextView nombre = (TextView) vistaP.findViewById(R.id.txtPersonaNombre);
        nombre.setText(p.toString());

        // Registrar TableLayout
        TableLayout t = (TableLayout) vistaP.findViewById(R.id.tlPersonas);
        tablasPersonas.add(t);

        linealPersonas.addView(vistaP);
        numeroPersonas++;
    }
    private void setListenersPersonas() {

        for (int i = 0; i < tablasPersonas.size(); i++) {
            TableLayout t = tablasPersonas.get(i);
            Persona p = listaPersonas.get(i);
            int finalI = i;
            t.setOnClickListener(v -> {

                // Quitar color a la fila anterior
                if (idSeleccionado > -1) {
                    descolorearFila(tablasPersonas.get(idSeleccionado));
                }

                // Colorear fila seleccionada
                colorearFila(t);

                // Actualizar IDs
                idAnterior = idSeleccionado;
                idSeleccionado = finalI;
                idSeleccionadoBD = p.getId();

                // Mostrar amigos y disponibles
                mostrarAmigosYDisponibles();

            });
        }
    }

    private void enlazarControles() {
        btnQuitar = findViewById(R.id.btnQuitar);
        btnAniadir = findViewById(R.id.btnAniadir);
        linealPersonas = findViewById(R.id.linealPersonas);
        linealAmigos = findViewById(R.id.linealAmigos);
        linealDisponibles = findViewById(R.id.linealDisponibles);

        btnQuitar.setOnClickListener(v -> {
            quitarAmigo();
        });

        btnAniadir.setOnClickListener(v -> {
            aniadirAmigo();
        });

    }

    // Try y Catch?
    private void mostrarAmigosYDisponibles() {
        tablasAmigos.clear();
        tablasDisponibles.clear();
        linealAmigos.removeAllViews();
        linealDisponibles.removeAllViews();

        Persona p = listaPersonas.get(idSeleccionado);
        listaAmigos = p.getAmigos();

        for (Persona amigo : listaAmigos) {
            mostrarAmigo(amigo);
        }

        for (Persona disponible : listaPersonas) {
            if (disponible.getId() != p.getId() && !listaAmigos.contains(disponible)) {
                mostrarDisponible(disponible);
            }
        }
    }

    private void mostrarAmigo(Persona a) {
        View vistaA = getLayoutInflater().inflate(R.layout.activity_persona, null);

        vistaA.setId(a.getId());

        TextView id = (TextView) vistaA.findViewById(R.id.txtPersonaID);
        id.setText("" + a.getId());

        TextView nombre = (TextView) vistaA.findViewById(R.id.txtPersonaNombre);
        nombre.setText(a.toString());

        TableLayout t = (TableLayout) vistaA.findViewById(R.id.tlPersonas);
        tablasAmigos.add(t);

        t.setOnClickListener(v -> {
            colorearFila(t);
            if(idSeleccionadoBD > -1) { // Solución Temporal
                for(TableLayout tAux : tablasAmigos) {
                    if (!tAux.equals(t)){
                        descolorearFila(tAux);
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
        id.setText("" + d.getId());

        TextView nombre = (TextView) vistaD.findViewById(R.id.txtPersonaNombre);
        nombre.setText(d.toString());

        TableLayout t = (TableLayout) vistaD.findViewById(R.id.tlPersonas);
        tablasDisponibles.add(t);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorearFila(t);
                if(idSeleccionadoBD > -1) { // Solución Temporal
                    for(TableLayout tAux : tablasDisponibles) {
                        if (!tAux.equals(t)){
                            descolorearFila(t);
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

    private void colorearFila(TableLayout layout) {
        layout.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
    }

    private void descolorearFila(TableLayout layout) {
        layout.setBackground(null);
    }

    private void mostrarInfoDebug() {
        Context context = getApplicationContext();
        Debug.mostrarPersonasPorConsola(context);
        Debug.mostrarAmigosPorConsola(context);
    }

}