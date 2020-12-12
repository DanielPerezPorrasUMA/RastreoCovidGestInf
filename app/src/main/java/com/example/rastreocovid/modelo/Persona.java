package com.example.rastreocovid.modelo;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Persona {

    private int id;
    private String nombre;
    private String apellidos;
    private final Context contexto;

    // Crea una nueva persona
    public Persona(Context contexto, String nombre, String apellidos) {

        Object siguienteIdObj = BDHelper.selectEscalar(contexto,
                "SELECT MAX(ID) + 1 FROM PERSONA");
        int siguienteId = 1;
        if (siguienteIdObj != null) {
            siguienteId = (int)siguienteIdObj;
        }

        BDHelper.modificar(contexto, "INSERT INTO PERSONA VALUES ("
                + siguienteId + ", '"
                + nombre + "', '"
                + apellidos + "')");

        this.id = siguienteId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contexto = contexto;

    }

    // Recupera una persona ya existente
    public Persona(Context contexto, int id) {

        Object[] fila = BDHelper.selectUnaFila(contexto,
                "SELECT NOMBRE, APELLIDOS FROM PERSONA WHERE ID = " + id);
        if (fila != null) {
            this.id = id;
            this.nombre = (String)fila[0];
            this.apellidos = (String)fila[1];
            this.contexto = contexto;
        } else {
            throw new RuntimeException("No se ha encontrado ninguna persona con id = " + id);
        }

    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public static List<Persona> getPersonas(Context contexto) {

        List<Object[]> id = BDHelper.select(contexto,
                "SELECT ID FROM PERSONA"
        );
        List<Persona> resultados = new ArrayList<>();

        for (Object[] tupla : id) {
            resultados.add(new Persona(contexto, (int)tupla[0]));
        }
        return resultados;

    }
    public List<Persona> getAmigos() {

        // Tenemos que seleccionar tanto los que tengan ID1=id como ID2=id.
        // Se puede usar una operación conjuntista como UNION, siempre que los tipos
        // de las columnas de cada consulta coincidan.
        String sentencia = "SELECT ID2 FROM AMIGOS WHERE ID1=" + id + " UNION " +
                "SELECT ID1 FROM AMIGOS WHERE ID2=" + id;
        List<Object[]> idsAmigos = BDHelper.select(contexto, sentencia);
        List<Persona> resultados = new ArrayList<>();

        for (Object[] tupla : idsAmigos) {
            int idAmigo = (int)tupla[0];
            resultados.add(new Persona(contexto, idAmigo));
        }
        return resultados;

    }

    // Setters
    public void setId(int valor) {
        BDHelper.modificar(contexto, "UPDATE PERSONA SET ID=" + valor + " WHERE ID=" + id);
        id = valor;
    }
    public void setNombre(String valor) {
        BDHelper.modificar(contexto, "UPDATE PERSONA SET NOMBRE=" + valor + " WHERE ID=" + id);
        nombre = valor;
    }
    public void setApellidos(String valor) {
        BDHelper.modificar(contexto, "UPDATE PERSONA SET APELLIDOS=" + valor + " WHERE ID=" + id);
        apellidos = valor;
    }
    public void aniadirAmigo(Persona amigo) {
        if (amigo.id == id) {
            throw new RuntimeException("Una persona no puede ser amiga de sí misma");
        } else if (BDHelper.selectUnaFila(contexto, "SELECT ID FROM PERSONA WHERE ID=" + amigo.id) != null) {
            throw new RuntimeException("La persona " + amigo.id + " y la persona " + id + " ya son amigas");
        } else {
            BDHelper.modificar(contexto, "INSERT INTO AMIGOS VALUES (" + id + ", " + amigo.id + ")");
        }
    }
    public void borrarAmigo(Persona amigo) {
        if (BDHelper.selectUnaFila(contexto, "SELECT ID FROM PERSONA WHERE ID=" + amigo.id) == null) {
            throw new RuntimeException("La persona " + amigo.id + " y la persona " + id + " no son amigas");
        } else {
            BDHelper.modificar(contexto, "DELETE FROM AMIGOS WHERE ID1=" + id + " AND ID2=" + amigo.id
                    + " OR ID1=" + amigo.id + " AND ID2=" + id);
        }
    }

    // Borra la persona, invalidando sus atributos
    public void borrar() {
        BDHelper.modificar(contexto, "DELETE FROM PERSONA WHERE ID=" + id);
        BDHelper.modificar(contexto, "DELETE FROM AMIGOS WHERE ID1=" + id + " OR ID2=" + id);
        id = -1;
        nombre = null;
        apellidos = null;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id == persona.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
