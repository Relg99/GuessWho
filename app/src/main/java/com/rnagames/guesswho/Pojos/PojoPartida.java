package com.rnagames.guesswho.Pojos;

public class PojoPartida {

    private int VistaTablero;
    private String Nombre;

    public PojoPartida(){
        //Constructor vacio necesario
    }
    public PojoPartida (String Nombre,int VistaTablero)
    {
      this.Nombre=Nombre;
      this.VistaTablero=VistaTablero;
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
