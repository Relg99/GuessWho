package com.rnagames.guesswho.Pojos;



public class Pojo_Personajes {
    private int Personaje_ID;
    private String Nombre;
    private String URL_Foto;

    public Pojo_Personajes() {
        Personaje_ID = 0;
        Nombre = "Default";
        this.URL_Foto = "https://storage.googleapis.com/guess-who-files/personajes/";

    }
    public int getPersonaje_ID() {
        return Personaje_ID;
    }

    public void setPersonaje_ID(int personaje_ID) {
        Personaje_ID = personaje_ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getURL_Foto() {
        return URL_Foto;
    }

    public void setURL_Foto(String URL_Foto) {
        this.URL_Foto = URL_Foto;
    }

}
