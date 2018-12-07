package com.rnagames.guesswho.Pojos;

public class PojoPartida {

    private int VistaTablero;
    private String Nombre;
    private String IdJuego;

    public PojoPartida(){
        //Constructor vacio necesario
    }
    public PojoPartida (String Nombre,int VistaTablero,String IdJuego)
    {
      this.Nombre=Nombre;
      this.VistaTablero=VistaTablero;
      this.IdJuego=IdJuego;
    }

    public String getIdJuego() {
        return IdJuego;
    }

    public void setIdJuego(String idJuego) {
        this.IdJuego = idJuego;
    }

    public int getVistaTablero() {
        return VistaTablero;
    }

    public void setVistaTablero(int vistaTablero) {
        this.VistaTablero = vistaTablero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
}
