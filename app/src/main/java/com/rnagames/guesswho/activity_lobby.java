package com.rnagames.guesswho;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rnagames.guesswho.Adapter.AdapterPartida;
import com.rnagames.guesswho.Pojos.PojoPartida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class activity_lobby extends AppCompatActivity {
    String getNivelURL="https://guess-who-223421.appspot.com/getNivel.php";
        String gamertag;
        TextView tvGamertag,tvNivel;
        public int res ;

    List<PojoPartida> Partidas;
    RecyclerView recycle;
    AdapterPartida AdapterP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        //------------------------------------------------------------------------------------------

        recycle = findViewById(R.id.rvVista);

        recycle.setLayoutManager(new LinearLayoutManager(this));

        Partidas = new ArrayList<>();

        FirebaseDatabase basedatos = FirebaseDatabase.getInstance();

        AdapterP = new AdapterPartida(Partidas);

        recycle.setAdapter(AdapterP);

        basedatos.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Partidas.removeAll(Partidas);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    PojoPartida Partida = snapshot.getValue(PojoPartida.class);
                    Partidas.add(Partida);
                }
                AdapterP.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //------------------------------------------------------------------------------------------

        tvGamertag=findViewById(R.id.tvGamertag);
        tvNivel=findViewById(R.id.tvNivel);
        Bundle recibirUsuario = getIntent().getExtras();
        gamertag = recibirUsuario.getString("gamertag");
        tvGamertag.setText(gamertag);
        getNivel(gamertag);

        Intent i = getIntent();

        res = i.getIntExtra("facnum",-200);

    }


    public void getNivel(final String gamertag){

      StringRequest postRequest = new StringRequest(Request.Method.POST, getNivelURL,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                   tvNivel.setText(tvNivel.getText().toString()+response);
                  }
              },
              new Response.ErrorListener()
              {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      // error
                      Toast.makeText(activity_lobby.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              }
      ) {
          @Override
          protected Map<String, String> getParams()
          {
              Map<String, String>  params = new HashMap<>();
              params.put("gamertag", gamertag);


              return params;
          }
      };
      RequestQueue pide = Volley.newRequestQueue(this);

      pide.add(postRequest);
  }

  public void clickCrearPartida (View view) {

  }
}
