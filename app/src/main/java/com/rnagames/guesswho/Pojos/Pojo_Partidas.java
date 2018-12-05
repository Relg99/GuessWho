package com.rnagames.guesswho.Pojos;

public class Pojo_Partidas {

    private Integer NumeroPartida;
    private String NombrePartida;

    public Pojo_Partidas()
    {
        NumeroPartida = 0;
        NombrePartida = "Partida";
    }

    public Integer getNumeroPartida() {
        return NumeroPartida;
    }

    public void setNumeroPartida(Integer numeroPartida) {
        NumeroPartida = numeroPartida;
    }

    public String getNombrePartida() {
        return NombrePartida;
    }

    public void setNombrePartida(String nombrePartida) {
        NombrePartida = nombrePartida;
    }
}
