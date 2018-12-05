package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnagames.guesswho.Pojos.Pojo_Partidas;
import com.rnagames.guesswho.R;

import java.util.ArrayList;

public class AdapterPartidas extends RecyclerView.Adapter<AdapterPartidas.HolderPartidas> {

    public ArrayList<Pojo_Partidas> datos;
    public Context contexto;

    @NonNull
    @Override
    public HolderPartidas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = View.inflate(contexto,R.layout.vista_lista_partidas,null);
        HolderPartidas F = new HolderPartidas(vista);

        return F;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPartidas holderPartidas, int i) {
        holderPartidas.tvNumroPartida.setText(String.valueOf(datos.get(i).getNumeroPartida()));
        holderPartidas.tvNombrePartida.setText(datos.get(i).getNombrePartida());

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }


    class HolderPartidas extends RecyclerView.ViewHolder
    {

        TextView tvNumroPartida, tvNombrePartida;

        public HolderPartidas(@NonNull View itemView) {
            super(itemView);
            tvNumroPartida = itemView.findViewById(R.id.tvNumeroPartida);
            tvNombrePartida = itemView.findViewById(R.id.tvNombrePartida);
        }
    }
}
