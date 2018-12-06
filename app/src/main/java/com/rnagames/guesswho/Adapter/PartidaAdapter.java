package com.rnagames.guesswho.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.rnagames.guesswho.Pojos.PojoPartida;
import com.rnagames.guesswho.R;

public class PartidaAdapter extends FirestoreRecyclerAdapter<PojoPartida,PartidaAdapter.PartidaHolder> {
    private OnClickListener Listener;

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
        holder.tvNombrePartida.setText(model.getNombre());

    }

    @NonNull
    @Override
    public PartidaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vista_lista_partidas,

                viewGroup,false);
        return new PartidaHolder(v);
    }

    public void eliminarPartida(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();
    }


    class PartidaHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePartida;
        Button bUnirse;

        public PartidaHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePartida=itemView.findViewById(R.id.tvNombrePartida);
            bUnirse = itemView.findViewById(R.id.bUnirse);
            bUnirse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Posicion = getAdapterPosition();
                    if (Posicion != RecyclerView.NO_POSITION && Listener != null){
                        Listener.onItemClick(getSnapshots().getSnapshot(Posicion),Posicion);
                    }

                }
            });
        }
    }

    public interface OnClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int Posicion);
    }

    public void setOnClickListener(OnClickListener Listener){
        this.Listener = Listener;
    }
}
