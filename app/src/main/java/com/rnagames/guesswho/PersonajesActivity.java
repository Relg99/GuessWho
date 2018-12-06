package com.rnagames.guesswho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rnagames.guesswho.Adapter.FichaAdapter;
import com.rnagames.guesswho.Pojos.Pojo_Personajes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonajesActivity extends AppCompatActivity {

    RecyclerView rvVista;
    FichaAdapter fAdapter;
    // Datos OrigenDatos;
    LinearLayoutManager llmOrientacion;
    ArrayList<Pojo_Personajes> arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);



        fAdapter = new FichaAdapter();
        rvVista = findViewById(R.id.rvVista);
        //llmOrientacion = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        arreglo = new ArrayList<>();

        fAdapter.contexto = this;
        fAdapter.datos=arreglo;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(layoutManager);
        mostrar();



    }

    public void mostrar(){
        JsonArrayRequest peticion = new JsonArrayRequest(
                Request.Method.GET,
                "https://guess-who-223421.appspot.com/selectFotos.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Pojo_Personajes personaje = new Pojo_Personajes();
                                personaje.setNombre(jsonObject.getString("Nombre"));
                                personaje.setURL_Foto(personaje.getURL_Foto()+jsonObject.getString("Foto"));
                                //Toast.makeText(PersonajesActivity.this,personaje.getURL_Foto()+jsonObject.getString("Foto") , Toast.LENGTH_SHORT).show();

                                arreglo.add(personaje);
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
                        Toast.makeText(PersonajesActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue pide = Volley.newRequestQueue(this);
        pide.add(peticion);

    }
}
