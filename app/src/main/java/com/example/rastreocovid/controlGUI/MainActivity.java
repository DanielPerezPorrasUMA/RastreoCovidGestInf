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
    private List<Persona> listaPersonas = new ArrayList<>(); // Lista para personas
    private List<Persona> listaAmigos = new ArrayList<>(); // Lista para amigos
    private List<Persona> listaDisponibles = new ArrayList<>(); // Lista para disponibles

    private Button btnQuitar, btnAniadir;
    private final List<TableLayout> tablasPersonas = new ArrayList<>();
    private final List<TableLayout> tablasAmigos = new ArrayList<>();
    private final List<TableLayout> tablasDisponibles = new ArrayList<>();

    private int idSeleccionado = -1; // ID de la persona seleccionada en la lista Personas
    private int idSeleccionadoAmigos = -1; // ID de la persona seleccionada en la lista Amigos
    private int idSeleccionadoDisp = -1; // ID de la persona seleccionada en la lista Disponibles


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
                idSeleccionado = finalI;

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

        // Mostrar los amigos (la lista cambia así,
        // que anulamos cualquier selección realizada previamente en ella)
        idSeleccionadoAmigos = -1;
        for (Persona amigo : listaAmigos) {
            mostrarAmigo(amigo);
        }
        setListenersAmigos();

        // Mostrar los disponibles (la lista cambia así,
        // que anulamos cualquier selección realizada previamente en ella)
        idSeleccionadoDisp = -1;
        for (Persona disponible : listaPersonas) {
            if (disponible.getId() != p.getId() && !listaAmigos.contains(disponible)) {
                listaDisponibles.add(disponible);
                mostrarDisponible(disponible);
            }
        }
        setListenersDisponibles();
    }

    private void mostrarAmigo(Persona a) {
        View vistaA = getLayoutInflater().inflate(R.layout.activity_persona, linealAmigos, false);
        vistaA.setId(a.getId());

        TextView id = (TextView) vistaA.findViewById(R.id.txtPersonaID);
        id.setText("" + a.getId());
        TextView nombre = (TextView) vistaA.findViewById(R.id.txtPersonaNombre);
        nombre.setText(a.toString());

        TableLayout t = (TableLayout) vistaA.findViewById(R.id.tlPersonas);
        tablasAmigos.add(t);
        linealAmigos.addView(vistaA);
    }
    private void setListenersAmigos() {
        for (int i = 0; i < tablasAmigos.size(); i++) {
            TableLayout t = tablasAmigos.get(i);
            int finalI = i;
            t.setOnClickListener(v -> {

                // Quitar color a la fila anterior
                if (idSeleccionadoAmigos > -1) {
                    descolorearFila(tablasAmigos.get(idSeleccionadoAmigos));
                }

                // Colorear fila seleccionada
                colorearFila(t);

                // Actualizar IDs
                idSeleccionadoAmigos = finalI;

            });
        }
    }

    private void mostrarDisponible(Persona d) {
        View vistaD = getLayoutInflater().inflate(R.layout.activity_persona, linealDisponibles, false);
        vistaD.setId(d.getId());

        TextView id = (TextView) vistaD.findViewById(R.id.txtPersonaID);
        id.setText("" + d.getId());
        TextView nombre = (TextView) vistaD.findViewById(R.id.txtPersonaNombre);
        nombre.setText(d.toString());

        TableLayout t = (TableLayout) vistaD.findViewById(R.id.tlPersonas);
        tablasDisponibles.add(t);
        linealDisponibles.addView(vistaD);
    }
    public void setListenersDisponibles() {
        for (int i = 0; i < tablasDisponibles.size(); i++) {
            TableLayout t = tablasDisponibles.get(i);
            int finalI = i;
            t.setOnClickListener(v -> {

                // Quitar color a la fila anterior
                if (idSeleccionadoDisp > -1) {
                    descolorearFila(tablasDisponibles.get(idSeleccionadoDisp));
                }

                // Colorear fila seleccionada
                colorearFila(t);

                // Actualizar IDs
                idSeleccionadoDisp = finalI;

            });
        }
    }

    private void aniadirAmigo() {
        // Comprobar selección
        if (idSeleccionado > -1 && idSeleccionadoDisp > -1) {

            Persona personaActual = listaPersonas.get(idSeleccionado);
            Persona disponibleSeleccionado = listaDisponibles.get(idSeleccionadoDisp);
            personaActual.aniadirAmigo(disponibleSeleccionado);

            // Actualizar contenido de las listas Amigos y Disponibles
            mostrarAmigosYDisponibles();

        }
    }

    private void quitarAmigo() {
        // Comprobar selección
        if (idSeleccionado > -1 && idSeleccionadoAmigos > -1) {

            Persona personaActual = listaPersonas.get(idSeleccionado);
            Persona amigoSeleccionado = listaDisponibles.get(idSeleccionadoAmigos);
            personaActual.borrarAmigo(amigoSeleccionado);

            // Actualizar contenido de las listas Amigos y Disponibles
            mostrarAmigosYDisponibles();

        }
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