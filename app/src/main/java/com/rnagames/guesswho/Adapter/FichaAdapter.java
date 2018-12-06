package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lb.auto_fit_textview.AutoResizeTextView;
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
    Point size = new Point();
    public boolean juego,juegoEmpezado;
    public String personajeElegido="kk";
    public int widthRecyler,heightRecycler;

    @NonNull
    @Override
    public FichaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = View.inflate(contexto, R.layout.adapter_tablero,null);
        WindowManager wm = (WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);

        //Toast.makeText(contexto, "Width: "+size.x+"Height: "+size.y, Toast.LENGTH_SHORT).show();
        FichaHolder F =  new FichaHolder(vista);
        return F;
    }

    @Override
    public void onBindViewHolder(@NonNull final FichaHolder holder, final int position) {
        //Tamaño de tarjetas
        Double temp;
        ViewGroup.LayoutParams tamanoCartas=holder.llseleccionTarjeta.getLayoutParams();
        ViewGroup.LayoutParams tamanoFoto=holder.personaje.getLayoutParams();

        tamanoCartas.width=widthRecyler/4;
        tamanoCartas.height=heightRecycler/4;
        temp=(tamanoCartas.height*.76);
        tamanoFoto.height=temp.intValue();

        //  tamanoCartas.height=temp.intValue();
        Picasso.get()
                .load(datos.get(position).getURL_Foto())
                .into(holder.personaje);
        holder.tvpersonajeNombre.setText(datos.get(position).getNombre());
        holder.personaje.setTag(position);
        holder.tvpersonajeNombre.setTag(position);
        holder.llTarjeta.setTag(position);
       // holder.llseleccionTarjeta.setTag(position);
        holder.llseleccionTarjeta.setBackgroundColor(Color.rgb(255,255,255));
        holder.llTarjeta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(contexto, "Presiono", Toast.LENGTH_SHORT).show();
                holder.tvpersonajeNombre.getTag();
                holder.llTarjeta.getTag();
                holder.personaje.getTag();
               // holder.llseleccionTarjeta.getTag();

                if(juego==true) {
                    if(juegoEmpezado==false){

                        personajeElegido=holder.tvpersonajeNombre.getText().toString();
                        holder.llseleccionTarjeta.setBackgroundColor(Color.rgb(255,255,0));

                    }else{
                        holder.llseleccionTarjeta.setBackgroundColor(Color.rgb(255,255,255));

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
        LinearLayout llTarjeta,llseleccionTarjeta;
        ImageView personaje;
        AutoResizeTextView tvpersonajeNombre;
        public FichaHolder(@NonNull View itemView) {
            super(itemView);
            personaje = itemView.findViewById(R.id.ivFotoPersonaje);
            tvpersonajeNombre=itemView.findViewById(R.id.tvNombrePersonaje);
            llTarjeta=itemView.findViewById(R.id.llTarjeta);
            llseleccionTarjeta=itemView.findViewById(R.id.llseleccionTarjeta);
        }


    }


}