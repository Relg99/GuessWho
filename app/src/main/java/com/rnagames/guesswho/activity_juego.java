package com.rnagames.guesswho;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rnagames.guesswho.Adapter.FichaAdapter;
import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/*
?genero
?tez
?peloCafe
?peloTratado
?ojosClaros
?lentes
?estudianteCeti

 */

public class activity_juego extends AppCompatActivity {

    int numeroTablero=1;
    TextView tvElige;
    RecyclerView rvVista;
    LinearLayout juegoLayout,dimensionesFoto;
    FichaAdapter fAdapter;
    Button bAceptar;
    ImageView ivMiPersonaje;
    TextView tvMiPersonaje;
    ArrayList<Pojo_Personajes> tablero;
    String URLTablero="https://guess-who-223421.appspot.com/vista_"+numeroTablero+".php";
    String miPersonaje;
    String preguntaRecibida;
    Button bGenero,bTez,bPelo;
    int miPersonajePos;
    Double width,height,tempTamanos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Union de componentes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        fAdapter = new FichaAdapter();
        tvElige=findViewById(R.id.tvElige);
        rvVista = findViewById(R.id.rvTablero);
        dimensionesFoto=findViewById(R.id.dimensionesFoto);
        juegoLayout=findViewById(R.id.juegoLayout);
        bAceptar=findViewById(R.id.bConfirmar);
        ivMiPersonaje=findViewById(R.id.ivMiPersonaje);
        tvMiPersonaje=findViewById(R.id.tvMiPersonaje);
        //

        //Inicializacion de variables
        juegoLayout.setVisibility(View.INVISIBLE);
        //

        //Relacionado con tamaño de vistas
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (double)size.x;
        height = (double) size.y;
        width=width-(width*.05);
        height=height-(height*0.05);
        ViewGroup.LayoutParams recyclerSize=rvVista.getLayoutParams();
        tempTamanos=width*0.70;
        recyclerSize.width=tempTamanos.intValue();
        recyclerSize.height= height.intValue();
        ViewGroup.LayoutParams leftBar=dimensionesFoto.getLayoutParams();
        tempTamanos=width-recyclerSize.width;
        leftBar.width=tempTamanos.intValue();
        //

        //Configuracion de Layout del Recyclerview
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        //

        //Inicializacion de adapter y recycler
        tablero = new ArrayList<>();
        fAdapter.contexto = this;
        fAdapter.widthRecyler=recyclerSize.width;
        fAdapter.heightRecycler=recyclerSize.height;
        fAdapter.juego=true;
        fAdapter.juegoEmpezado=false;
        fAdapter.datos=tablero;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(layoutManager);
        rvVista.getLayoutManager().findViewByPosition(1);

        getTablero();

        //Listener Firebase
        /*
        switch (preguntaRecibida){
            case "?genero":
                getMiGeneroMasculino(tablero.get(miPersonajePos).isGenero_Masculino());
                //EnviarRespuestaalBusDeDatos
            break;
            case"?tez":
                getMiTezMoreno(tablero.get(miPersonajePos).getColor_Piel());
                //EnviarRespuestaalBusDeDatos
                break;
            case"?peloCafe":
                getMiPeloCafe(tablero.get(miPersonajePos).getColor_Cabello());
                //

            case"?peloTratado":

                getMiPeloTratado(tablero.get(miPersonajePos).getColor_Cabello());
                //

            case "?ojosClaros":
                getMisOjosClaros(tablero.get(miPersonajePos).getColor_Ojos());
                //

            case"?lentes":
                getMisLentes(tablero.get(miPersonajePos).isLentes());
                //
            case"?estudianteCeti":
                getMiEstudianteCeti(tablero.get(miPersonajePos).isEstudiante());
                //
        }
*/
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
boolean getMiGeneroMasculino(Boolean genero){

     return genero;
}
boolean getMiTezMoreno(String tez){
        if(tez.equals("7F5E5E")) {
            return true;
        }else{
            return false;
        }
}
boolean getMiPeloCafe(String peloCafe){
        if(peloCafe.equals("281F1F")) {
            return true;
        }else{
            return false;
        }
}
boolean getMiPeloTratado(String peloTratado){
        if(peloTratado.equals("FFFFFF")) {
            return true;
        }else{
            return false;
        }
}
boolean getMisOjosClaros(String ojosClaros){
        if(ojosClaros.equals("281F1F")) {
            return false;
        }else{
            return true;
        }
}
boolean getMisLentes(Boolean lentes  ){

        return lentes;
}
boolean getMiEstudianteCeti(Boolean estudianteCeti){
        return estudianteCeti;
}
    }
