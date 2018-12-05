package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.rnagames.guesswho.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FichaAdapter extends RecyclerView.Adapter <FichaAdapter.FichaHolder>
{

    public ArrayList<Pojo_Personajes> datos;
    public Context contexto;

    @NonNull
    @Override
    public FichaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = View.inflate(contexto, R.layout.adapter_personajes,null);
        FichaHolder F =  new FichaHolder(vista);
        return F;
    }

    @Override
    public void onBindViewHolder(@NonNull FichaHolder holder, int position) {

        Picasso.get()
                .load(datos.get(position).getURL_Foto())
                .into(holder.personaje);
        Log.d("DEBUG","Llegue");
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class FichaHolder extends RecyclerView.ViewHolder
    {
        ImageView personaje;

        public FichaHolder(@NonNull View itemView) {
            super(itemView);
            personaje = itemView.findViewById(R.id.ivPersonaje);


        }
    }
}