package com.rnagames.guesswho.Pojos;

public class PojoPartida {

    private Integer Numero;
    private String Nombre;

    public PojoPartida ()
    {
        Numero = 0;
        Nombre = "Partida";
    }

    public Integer getNumero() {
        return Numero;
    }

    public void setNumero(Integer numero) {
        Numero = numero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
