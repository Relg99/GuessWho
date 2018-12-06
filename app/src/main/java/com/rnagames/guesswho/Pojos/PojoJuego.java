package com.rnagames.guesswho.Pojos;

public class PojoJuego {
    private String JugadorP = "";
    private String JugadorS = "";
    private String Pregunta = "";
    private String JugadoresL = "00";
    private Boolean PartidaEnCurso = false;
    private Boolean TurnoJP = true;

    public PojoJuego(){
        //Vacio
    }

    public PojoJuego(String jugadorP, String jugadorS, String pregunta, String jugadoresL, Boolean partidaEnCurso, Boolean turnoJP) {
        this.JugadorP = jugadorP;
        this.JugadorS = jugadorS;
        this.Pregunta = pregunta;
        this.JugadoresL = jugadoresL;
        this.PartidaEnCurso = partidaEnCurso;
        this.TurnoJP = turnoJP;
    }

    public String getJugadorP() {
        return JugadorP;
    }

    public void setJugadorP(String jugadorP) {
        this.JugadorP = jugadorP;
    }

    public String getJugadorS() {
        return JugadorS;
    }

    public void setJugadorS(String jugadorS) {
        this.JugadorS = jugadorS;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        this.Pregunta = pregunta;
    }

    public String getJugadoresL() {
        return JugadoresL;
    }

    public void setJugadoresL(String jugadoresL) {
        this.JugadoresL = jugadoresL;
    }

    public Boolean getPartidaEnCurso() {
        return PartidaEnCurso;
    }

    public void setPartidaEnCurso(Boolean partidaEnCurso) {
        this.PartidaEnCurso = partidaEnCurso;
    }

    public Boolean getTurnoJP() {
        return TurnoJP;
    }

    public void setTurnoJP(Boolean turnoJP) {
        this.TurnoJP = turnoJP;
    }
}
