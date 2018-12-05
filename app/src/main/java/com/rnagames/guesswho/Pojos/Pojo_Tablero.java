package com.rnagames.guesswho.Pojos;


import com.rnagames.guesswho.R;

public class Pojo_Tablero {
    private int Personaje_ID;
    private String Nombre;
    private boolean Genero_Masculino;
    private boolean Estudiante;
    private boolean Lentes;
    private int Color_Ojos;
    private int Color_Piel;
    private int Color_Cabello;
    private String URL_Foto;

    public Pojo_Tablero() {
        Personaje_ID = 0;
        Nombre = "Default";
        Genero_Masculino = true;
        Estudiante = true;
        Lentes = false;
        Color_Ojos = R.color.cafe;
        Color_Piel = R.color.piel_oscura;
        Color_Cabello = R.color.cafe;
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


    public void setGenero_Masculino(boolean genero_Masculino) {
        Genero_Masculino = genero_Masculino;
    }
    public boolean isGenero_Masculino() {
    return Genero_Masculino;
    }
    public boolean isEstudiante() {
        return Estudiante;
    }

    public void setEstudiante(boolean estudiante) {
        Estudiante = estudiante;
    }

    public boolean isLentes() {
        return Lentes;
    }

    public void setLentes(boolean lentes) {
        Lentes = lentes;
    }

    public int getColor_Ojos() {
        return Color_Ojos;
    }

    public void setColor_Ojos(int color_Ojos) {
        Color_Ojos = color_Ojos;
    }

    public int getColor_Piel() {
        return Color_Piel;
    }

    public void setColor_Piel(int color_Piel) {
        Color_Piel = color_Piel;
    }

    public int getColor_Cabello() {
        return Color_Cabello;
    }

    public void setColor_Cabello(int color_Cabello) {
        Color_Cabello = color_Cabello;
    }

    public String getURL_Foto() {
        return URL_Foto;
    }

    public void setURL_Foto(String URL_Foto) {
        this.URL_Foto = URL_Foto;
    }


}
