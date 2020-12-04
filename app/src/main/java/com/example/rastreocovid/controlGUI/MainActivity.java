package com.example.rastreocovid.controlGUI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import com.example.rastreocovid.R;
import com.example.rastreocovid.modelo.Debug;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
        mostrarInfoDebug();
    }

    private void enlazarControles() {

    }

    private void mostrarInfoDebug() {
        Context context = getApplicationContext();
        Debug.mostrarPersonasPorConsola(context);
        Debug.mostrarAmigosPorConsola(context);
    }

}