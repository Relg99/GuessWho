package com.rnagames.guesswho.Pojos;

public class PojoPartida {

    private int Numero;
    private String Nombre;

    public PojoPartida(){
        //Constructor vacio necesario
    }
    public PojoPartida (String Nombre,int Numero)
    {
      this.Nombre=Nombre;
      this.Numero=Numero;
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
