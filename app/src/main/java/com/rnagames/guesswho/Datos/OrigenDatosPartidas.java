package com.rnagames.guesswho.Datos;

import com.rnagames.guesswho.Pojos.Pojo_Partidas;

import java.util.ArrayList;

public class OrigenDatosPartidas {
    public ArrayList<Pojo_Partidas> datosPartida()
    {
        ArrayList<Pojo_Partidas> pop = new ArrayList<Pojo_Partidas>();

        Pojo_Partidas Partida;

        Partida = new Pojo_Partidas();
        Partida.setNumeroPartida(1);
        Partida.setNombrePartida("Rica99");
        pop.add(Partida);

        Partida = new Pojo_Partidas();
        Partida.setNumeroPartida(2);
        Partida.setNombrePartida("Alancito");
        pop.add(Partida);

        return pop;
    }
}
