package com.rnagames.guesswho.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.rnagames.guesswho.Pojos.PojoPartida;
import com.rnagames.guesswho.R;

public class PartidaAdapter extends FirestoreRecyclerAdapter<PojoPartida,PartidaAdapter.PartidaHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public PartidaAdapter(@NonNull FirestoreRecyclerOptions<PojoPartida> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PartidaHolder holder, int position, @NonNull PojoPartida model) {
        //holder.tvNumeroPartida.setText(String.valueOf(model.getNumero()));
        holder.tvNombrePartida.setText(model.getNombre());

    }

    @NonNull
    @Override
    public PartidaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vista_lista_partidas,

                viewGroup,false);
        return new PartidaHolder(v);
    }

    class PartidaHolder extends RecyclerView.ViewHolder{

        TextView tvNumeroPartida,tvNombrePartida;
        public PartidaHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePartida=itemView.findViewById(R.id.tvNombrePartida);
            //tvNumeroPartida=itemView.findViewById(R.id.tvNumeroPartida);
        }
    }
}
