package com.rnagames.guesswho;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rnagames.guesswho.Adapter.FichaAdapter;
import com.rnagames.guesswho.Pojos.Pojo_FULL;
import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_juego extends AppCompatActivity {

    int numeroTablero=1;
    TextView tvElige;
    RecyclerView rvVista;
    LinearLayout juegoLayout;
    FichaAdapter fAdapter;
    Button bAceptar;
    ImageView ivMiPersonaje;
    TextView tvMiPersonaje;
    ArrayList<Pojo_Personajes> tablero;
    String URLTablero="https://guess-who-223421.appspot.com/vista_"+numeroTablero+".php";
    String miPersonaje;
    int miPersonajePos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        fAdapter = new FichaAdapter();
        tvElige=findViewById(R.id.tvElige);
        rvVista = findViewById(R.id.rvTablero);
        juegoLayout=findViewById(R.id.juegoLayout);
        bAceptar=findViewById(R.id.bConfirmar);
        ivMiPersonaje=findViewById(R.id.ivMiPersonaje);
        tvMiPersonaje=findViewById(R.id.tvMiPersonaje);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        tablero = new ArrayList<>();
        fAdapter.contexto = this;
        fAdapter.juego=true;
        fAdapter.juegoEmpezado=false;
        fAdapter.datos=tablero;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(layoutManager);
        rvVista.getLayoutManager().findViewByPosition(1);
        juegoLayout.setVisibility(View.INVISIBLE);
        getTablero();



    }

    public void getTablero() {
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, URLTablero,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Pojo_Personajes personaje = new Pojo_Personajes();
                                personaje.setPersonaje_ID(Integer.parseInt(jsonObject.getString("Personaje_ID")));
                                personaje.setNombre(jsonObject.getString("Nombre"));
                                if(jsonObject.getString("Genero_Masculino").equals("0")){
                                    personaje.setGenero_Masculino(false);
                                }else{personaje.setGenero_Masculino(true);
                                }
                                if(jsonObject.getString("Estudiante").equals("0")){
                                    personaje.setEstudiante(false);
                                }else{personaje.setEstudiante(true);
                                }
                                if(jsonObject.getString("Lentes").equals("0")){
                                    personaje.setLentes(false);
                                }else{personaje.setLentes(true);
                                }
                                personaje.setColor_Ojos(jsonObject.getString("FK_ColorOjos"));
                                personaje.setColor_Piel(jsonObject.getString("FK_ColorPiel"));
                                personaje.setColor_Cabello(jsonObject.getString("FK_ColorCabello"));

                                personaje.setURL_Foto(personaje.getURL_Foto()+jsonObject.getString("Foto"));
                                tablero.add(personaje);

                                fAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(activity_juego.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {


        };
        RequestQueue pide = Volley.newRequestQueue(this);

        pide.add(postRequest);
    }

    public void startGame(View view){
        if(!fAdapter.personajeElegido.equals("kk")) {
            fAdapter.juegoEmpezado=true;
            miPersonaje=fAdapter.personajeElegido;
            bAceptar.setVisibility(View.INVISIBLE);
            juegoLayout.setVisibility(View.VISIBLE);
            tvElige.setVisibility(View.INVISIBLE);
            //Toast.makeText(this, personajes.get(0).getNombre(), Toast.LENGTH_SHORT).show();
            encontrarPersonaje();
        }else{
            Toast.makeText(this, "Elige ha un personaje antes.", Toast.LENGTH_SHORT).show();
        }
    }
    public void encontrarPersonaje() {
        for (int i=0;i<tablero.size();i++){
            if(tablero.get(i).getNombre()==miPersonaje){
                miPersonajePos=i;
                break;
            }

        }
    Picasso.get()
            .load(tablero.get(miPersonajePos).getURL_Foto())
            .into(ivMiPersonaje);
        tvMiPersonaje.setText(tablero.get(miPersonajePos).getNombre());

}
    }
