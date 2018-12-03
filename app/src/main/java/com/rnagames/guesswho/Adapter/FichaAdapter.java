package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.rnagames.guesswho.R;

import java.util.ArrayList;

public class FichaAdapter {

package com.rnagames.guesswho.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.rnagames.guesswho.R;

import java.util.ArrayList;

    public class FichaAdapter extends RecyclerView.Adapter <FichaAdapter.FichaHolder>
    {

        public ArrayList<Pojo_Personajes> datos;
        public Context contexto;

        @NonNull
        @Override
        public FichaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View vista = View.inflate(contexto,R.layout./* Aqui va el layout del boton unirse*/,null);
            FichaHolder F =  new FichaHolder(vista);
            return F;
        }

        @Override
        public void onBindViewHolder(@NonNull FichaHolder holder, int position) {
            holder.tvNombre.setText(datos.get(position).getNombre());
            /*
            Aqui va los datos que se pondran en la lista de unirse.

             */
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        class FichaHolder extends RecyclerView.ViewHolder
        {
            TextView tvNombre,tvApellido,tvSexo,tvRegistro,tvDomicilio,tvFechaNacimiento,tvCelular,tvCP,tvColonia,tvMunicipio,tvCarrera;

            public FichaHolder(@NonNull View itemView) {
                super(itemView);
                tvNombre = itemView.findViewById(R.id.tvNombre);
                tvApellido = itemView.findViewById(R.id.tvApellido);

                /*
                Aqui va donde se pondra la informacion recopilada.
                 */

            }
        }
    }

}
