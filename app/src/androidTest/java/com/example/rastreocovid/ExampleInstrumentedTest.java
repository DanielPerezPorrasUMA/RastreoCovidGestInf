package com.example.rastreocovid;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.rastreocovid.modelo.Persona;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //List<Persona> lista = Persona.getPersonas(appContext);
        //Persona p = lista.get(1);
        //List<Persona> listaAmigos = p.getAmigos();
        assertEquals("com.example.rastreocovid", appContext.getPackageName());
    }
}