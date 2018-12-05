package com.rnagames.guesswho.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnagames.guesswho.Pojos.PojoPartida;
import com.rnagames.guesswho.R;
import com.rnagames.guesswho.Pojos.PojoPartida;
import com.rnagames.guesswho.R;

import java.util.List;

public class AdapterPartida extends RecyclerView.Adapter<AdapterPartida.HolderPartida> {

    List<PojoPartida> Partidas;

    public AdapterPartida(List<PojoPartida> Partidas) {
        this.Partidas = Partidas;
    }


    @NonNull
    @Override
    public HolderPartida onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vista_lista_partidas,viewGroup,false);
        HolderPartida F = new HolderPartida(vista);
        return F;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPartida holderPartida, int i) {
        PojoPartida Partida = Partidas.get(i);
        holderPartida.htvNumeroPartids.setText(String .valueOf(Partida.getNumero()));
        holderPartida.htvNombrePartida.setText(Partida.getNombre());
    }

    @Override
    public int getItemCount() {
        return Partidas.size();
    }

    public static class HolderPartida extends RecyclerView.ViewHolder
    {

        TextView htvNumeroPartids,htvNombrePartida;


        public HolderPartida(@NonNull View itemView) {
            super(itemView);

            htvNumeroPartids = itemView.findViewById(R.id.tvNumeroPartida);
            htvNombrePartida = itemView.findViewById(R.id.tvNombrePartida);

        }
    }
}
