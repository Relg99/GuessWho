package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.rnagames.guesswho.R;
import com.rnagames.guesswho.activity_juego;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class FichaAdapter extends RecyclerView.Adapter <FichaAdapter.FichaHolder>
{

    public ArrayList<Pojo_Personajes> datos;
    public Context contexto;
    public boolean juego,juegoEmpezado;
    public String personajeElegido="kk";


    @NonNull
    @Override
    public FichaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = View.inflate(contexto, R.layout.adapter_tablero,null);
        FichaHolder F =  new FichaHolder(vista);
        return F;
    }

    @Override
    public void onBindViewHolder(@NonNull final FichaHolder holder, final int position) {
        Picasso.get()
                .load(datos.get(position).getURL_Foto())
                .into(holder.personaje);
        holder.tvpersonajeNombre.setText(datos.get(position).getNombre());
        holder.personaje.setTag(position);
        holder.tvpersonajeNombre.setTag(position);
        holder.llTarjeta.setTag(position);

        holder.llTarjeta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(contexto, "Presiono", Toast.LENGTH_SHORT).show();
                holder.tvpersonajeNombre.getTag();
                holder.llTarjeta.getTag();
                holder.personaje.getTag();
                if(juego==true) {
                    if(juegoEmpezado==false){
                        personajeElegido=holder.tvpersonajeNombre.getText().toString();
                    }else{
                        if(holder.tvpersonajeNombre.getText().equals("")) {
                            holder.tvpersonajeNombre.setText(datos.get(position).getNombre());
                            holder.personaje.setVisibility(View.VISIBLE);
                            holder.llTarjeta.setBackgroundColor(Color.rgb(255,0,0));

                        }else{
                            holder.tvpersonajeNombre.setText("");
                            holder.personaje.setVisibility(View.INVISIBLE);
                            holder.llTarjeta.setBackgroundColor(Color.rgb(0,0,0));

                        }
                    }

                }else{
                    //Abrir caracteristicas de personaje
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class FichaHolder extends RecyclerView.ViewHolder {
        LinearLayout llTarjeta;
        ImageView personaje;
        TextView tvpersonajeNombre;
        public FichaHolder(@NonNull View itemView) {
            super(itemView);
            personaje = itemView.findViewById(R.id.ivFotoPersonaje);
            tvpersonajeNombre=itemView.findViewById(R.id.tvNombrePersonaje);
            llTarjeta=itemView.findViewById(R.id.llTarjeta);

        }


    }


}