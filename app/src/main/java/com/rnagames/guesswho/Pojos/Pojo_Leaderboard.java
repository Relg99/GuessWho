package com.rnagames.guesswho.Pojos;

public class Pojo_Leaderboard {
    private int LeaderBoard_ID;
  private  String ganador;
  private String perdedor;
  private int NivelGanador;
  private int NivelPerdedor;

    public Pojo_Leaderboard() {
        LeaderBoard_ID = 0;
        this.ganador = "";
        this.perdedor = "";
        NivelGanador = 1;
        NivelPerdedor = 1;
    }

    public void setLeaderBoard_ID(int leaderBoard_ID) {
        LeaderBoard_ID = leaderBoard_ID;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public void setPerdedor(String perdedor) {
        this.perdedor = perdedor;
    }

    public void setNivelGanador(int nivelGanador) {
        NivelGanador = nivelGanador;
    }

    public void setNivelPerdedor(int nivelPerdedor) {
        NivelPerdedor = nivelPerdedor;
    }

    public int getLeaderBoard_ID() {
        return LeaderBoard_ID;
    }

    public String getGanador() {
        return ganador;
    }

    public String getPerdedor() {
        return perdedor;
    }

    public int getNivelGanador() {
        return NivelGanador;
    }

    public int getNivelPerdedor() {
        return NivelPerdedor;
    }
}
