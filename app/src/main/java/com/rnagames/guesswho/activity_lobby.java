package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rnagames.guesswho.Adapter.AdapterPartidas;
import com.rnagames.guesswho.Datos.OrigenDatosPartidas;

import java.util.HashMap;
import java.util.Map;


public class activity_lobby extends AppCompatActivity {
    String getNivelURL="https://guess-who-223421.appspot.com/getNivel.php";
        String gamertag;
        TextView tvGamertag,tvNivel;

        OrigenDatosPartidas Origen;
        AdapterPartidas AdPartidas;
        LinearLayoutManager llmOrientacion;
        public RecyclerView rvListaPartidas;
        public int res ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        rvListaPartidas = findViewById(R.id.rvListaPartidas);
        Origen = new OrigenDatosPartidas();
        AdPartidas = new AdapterPartidas();
        llmOrientacion = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        AdPartidas.datos = Origen.datosPartida();
        AdPartidas.contexto = this;
        rvListaPartidas.setLayoutManager(llmOrientacion);
        rvListaPartidas.setAdapter(AdPartidas);

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
