package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.rnagames.guesswho.Pojos.Pojo_Leaderboard;
import com.rnagames.guesswho.R;
import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter <LeaderBoardAdapter.LeaderboardHolder>
{

    public ArrayList<Pojo_Leaderboard> datos;
    public Context contexto;

    @NonNull
    @Override
    public LeaderboardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = View.inflate(contexto, R.layout.adapter_leaderboard,null);
        LeaderboardHolder F =  new LeaderboardHolder(vista);
        return F;
    }//ayuda

    @Override
    public void onBindViewHolder(@NonNull final LeaderboardHolder holder, final int position) {
      holder.tvLeaderboard.setText("ID:"+datos.get(position).getLeaderBoard_ID()+" Ganador: "+
      datos.get(position).getGanador()+" Nivel: "+datos.get(position).getNivelGanador()+
              " Perdedor: "+datos.get(position).getPerdedor()+" Nivel: "+datos.get(position).getNivelPerdedor());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class LeaderboardHolder extends RecyclerView.ViewHolder {
        TextView tvLeaderboard;

        public LeaderboardHolder(@NonNull View itemView) {
            super(itemView);
            tvLeaderboard=itemView.findViewById(R.id.tvLeaderboard);
        }


    }


}