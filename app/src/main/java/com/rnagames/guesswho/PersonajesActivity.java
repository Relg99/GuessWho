package com.rnagames.guesswho;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
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
    Double width;
    Double height;
    // Datos OrigenDatos;
    //LinearLayoutManager llmOrientacion;
    ArrayList<Pojo_Personajes> arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);

        //Relacionado con tamaño de vistas
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (double) size.x;
        height = (double) size.y;

        fAdapter = new FichaAdapter();
        rvVista = findViewById(R.id.rvVista);
        //llmOrientacion = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        arreglo = new ArrayList<>();
        fAdapter.juego = false;
        fAdapter.widthRecyler = width.intValue();
        fAdapter.heightRecycler = height.intValue();

        fAdapter.contexto = this;
        fAdapter.datos = arreglo;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(layoutManager);
        mostrar();
    }

    public void mostrar() {
        JsonArrayRequest peticion = new JsonArrayRequest(
                Request.Method.GET,
                "https://guess-who-223421.appspot.com/selectPersonaje.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Pojo_Personajes personaje = new Pojo_Personajes();
                                personaje.setPersonaje_ID(jsonObject.getString("Personaje_ID"));
                                personaje.setNombre(jsonObject.getString("Nombre"));
                                if(jsonObject.getString("Genero_Masculino").equals("0")) {
                                    personaje.setGenero_Masculino(false);
                                }else{
                                    personaje.setGenero_Masculino(true);

                                }
                                if(jsonObject.getString("Estudiante").equals("0")) {
                                    personaje.setEstudiante(false);
                                }else{
                                    personaje.setEstudiante(true);

                                }
                                if(jsonObject.getString("Lentes").equals("0")) {
                                    personaje.setLentes(false);
                                }else{
                                    personaje.setLentes(true);
                                }
                                personaje.setColor_Ojos(jsonObject.getString("FK_ColorOjos"));
                                personaje.setColor_Piel(jsonObject.getString("FK_ColorPiel"));

                                personaje.setColor_Cabello(jsonObject.getString("FK_ColorCabello"));

                                personaje.setURL_Foto(personaje.getURL_Foto() + jsonObject.getString("Foto"));

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
                        Toast.makeText(PersonajesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue pide = Volley.newRequestQueue(this);
        pide.add(peticion);
    }


}
