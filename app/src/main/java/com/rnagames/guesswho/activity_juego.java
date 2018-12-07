package com.rnagames.guesswho;

import android.content.Intent;
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
import android.widget.RelativeLayout;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.rnagames.guesswho.Adapter.FichaAdapter;
import com.rnagames.guesswho.Pojos.PojoJuego;
import com.rnagames.guesswho.Pojos.Pojo_Personajes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

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

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String KEY_JUGADORESL = "jugadoresL";
    private static final String KEY_PREGUNTA = "pregunta";
    private static final String KEY_TURNOJP = "turnoJP";
    private static final String KEY_PARTIDAENCURSO = "partidaEnCurso";
    private static final String KEY_ACTIVARBOTON = "activarBoton";

    //Datos de Firestore
    public String JugadorP = "";
    public String JugadorS = "";
    public String JugadoresL;
    public Boolean PartidaEnCurso;
    public String Pregunta;
    public Boolean TurnoJp;

    //Botones
    public ImageView bGenero;
    public ImageView bTez;
    public ImageView bOjos;
    public ImageView bLentes;
    public ImageView bEstudiante;

    int numeroTablero = 1;
    TextView tvElige;
    RecyclerView rvVista;
    LinearLayout juegoLayout, dimensionesFoto;
    Boolean jugadorP;
    FichaAdapter fAdapter;
    Button bAceptar;
    ImageView ivMiPersonaje;
    TextView tvMiPersonaje;
    ArrayList<Pojo_Personajes> tablero;
    String URLTablero;
    String miPersonaje, vsPersonaje;
    String preguntaRecibida;
    String idJuego;
    int miPersonajePos;
    Double width, height, tempTamanos;
    Boolean ActivarPreguntas = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Union de componentes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        fAdapter = new FichaAdapter();
        tvElige = findViewById(R.id.tvElige);
        rvVista = findViewById(R.id.rvTablero);
        dimensionesFoto = findViewById(R.id.dimensionesFoto);
        juegoLayout = findViewById(R.id.juegoLayout);
        bAceptar = findViewById(R.id.bConfirmar);
        ivMiPersonaje = findViewById(R.id.ivMiPersonaje);
        tvMiPersonaje = findViewById(R.id.tvMiPersonaje);
        bGenero = findViewById(R.id.bGenero);
        bLentes = findViewById(R.id.bLentes);
        bOjos = findViewById(R.id.bOjos);
        bTez = findViewById(R.id.bTez);
        bEstudiante = findViewById(R.id.bEstudiante);
        //

        //Inicializacion de variables
        juegoLayout.setVisibility(View.INVISIBLE);
        Bundle getGameInfo = getIntent().getExtras();
        jugadorP = getGameInfo.getBoolean("tipoJugador");
        numeroTablero = getGameInfo.getInt("numTablero");
        idJuego = getGameInfo.getString("IdJuego");
        URLTablero = "https://guess-who-223421.appspot.com/vista_" + numeroTablero + ".php";
        //

        //Relacionado con tamaño de vistas
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (double) size.x;
        height = (double) size.y;
        width = width - (width * .05);
        height = height - (height * 0.05);
        ViewGroup.LayoutParams recyclerSize = rvVista.getLayoutParams();
        tempTamanos = width * 0.70;
        recyclerSize.width = tempTamanos.intValue();
        recyclerSize.height = height.intValue();
        ViewGroup.LayoutParams leftBar = dimensionesFoto.getLayoutParams();
        tempTamanos = width - recyclerSize.width;
        leftBar.width = tempTamanos.intValue();
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
        fAdapter.widthRecyler = recyclerSize.width;
        fAdapter.heightRecycler = recyclerSize.height;
        fAdapter.juego = true;
        fAdapter.juegoEmpezado = false;
        fAdapter.datos = tablero;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(layoutManager);
        rvVista.getLayoutManager().findViewByPosition(1);

        getTablero();

        //Listener Firebase

        /*

        AGREGAR el que recibe las preguntas del contricante y lsa procesa
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
             case:miPersonaje;
             decir que simona
             gameOver();
                //
        }
*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("Juego").document(idJuego)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        PojoJuego JuegoActual = documentSnapshot.toObject(PojoJuego.class);
                        JugadorP = JuegoActual.getJugadorP();
                        JugadorS = JuegoActual.getJugadorS();
                        JugadoresL = JuegoActual.getJugadoresL();
                        PartidaEnCurso = JuegoActual.getPartidaEnCurso();
                        Pregunta = JuegoActual.getPregunta();
                        TurnoJp = JuegoActual.getTurnoJP();
                        ActivarPreguntas = JuegoActual.getActivarBoton();

                        if (jugadorP) {
                            if (JugadoresL.equals("01")) {
                                Toast.makeText(activity_juego.this,
                                        "Tu compañero esta listo", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (JugadoresL.equals("10")) {
                                Toast.makeText(activity_juego.this,
                                        "Tu compañero esta listo", Toast.LENGTH_SHORT).show();
                            }
                        }


                        if (jugadorP) {
                            if (TurnoJp) {
                                if (ActivarPreguntas) {
                                    bGenero.setClickable(true);
                                    bTez.setClickable(true);
                                    bOjos.setClickable(true);
                                    bLentes.setClickable(true);
                                    bEstudiante.setClickable(true);
                                    Toast.makeText(activity_juego.this,"Es tu turno.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                bGenero.setClickable(false);
                                bTez.setClickable(false);
                                bOjos.setClickable(false);
                                bLentes.setClickable(false);
                                bEstudiante.setClickable(false);
                                Preguntas();
                            }
                        } else {
                            if (!TurnoJp) {
                                if (!ActivarPreguntas) {
                                    bGenero.setClickable(true);
                                    bTez.setClickable(true);
                                    bOjos.setClickable(true);
                                    bLentes.setClickable(true);
                                    bEstudiante.setClickable(true);
                                    Toast.makeText(activity_juego.this,"Es tu turno.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                bGenero.setClickable(false);
                                bTez.setClickable(false);
                                bOjos.setClickable(false);
                                bLentes.setClickable(false);
                                bEstudiante.setClickable(false);
                                Preguntas();
                            }
                        }
                    }
                });
    }

    public void clickGenero(View view) {
        db.collection("Juego").document(idJuego)
                .update(KEY_PREGUNTA, "?genero")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_juego.this,
                                "Haz preguntado el genero del personaje.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickTez(View view) {
        db.collection("Juego").document(idJuego)
                .update(KEY_PREGUNTA, "?tez")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_juego.this,
                                "Haz preguntado si el personaje es moreno.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickOjos(View view) {
        db.collection("Juego").document(idJuego)
                .update(KEY_PREGUNTA, "?ojosClaros")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_juego.this,
                                "Haz preguntado si el personaje tiene ojos de color claro.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickLentes(View view) {
        db.collection("Juego").document(idJuego)
                .update(KEY_PREGUNTA, "?lentes")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_juego.this,
                                "Haz preguntado si el personaje tiene lentes.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickEstudiante(View view) {
        db.collection("Juego").document(idJuego)
                .update(KEY_PREGUNTA, "?estudianteCeti")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_juego.this,
                                "Haz preguntado si el personaje estudia en CETI.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickCheckTurno(View view) {
        if (jugadorP) {
            if (!TurnoJp) {
                db.collection("Juego").document(idJuego)
                        .update(KEY_ACTIVARBOTON, false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity_juego.this, "Termino tu turno.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "No es tu turno.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            if (TurnoJp) {
                db.collection("Juego").document(idJuego)
                        .update(KEY_ACTIVARBOTON, true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity_juego.this, "Termino tu turno.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "No es tu turno.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Preguntas() {
        Intent i = new Intent(this, PreguntaPU.class);
        preguntaRecibida = Pregunta;
        switch (preguntaRecibida) {
            case "?genero":
                i.putExtra("Pregunta", "?genero");
                i.putExtra("idJuego", idJuego);
                startActivity(i);
                //getMiGeneroMasculino(tablero.get(miPersonajePos).isGenero_Masculino());
                //EnviarRespuestaalBusDeDatos
                break;
            case "?tez":
                i.putExtra("Pregunta", "?tez");
                i.putExtra("idJuego", idJuego);
                startActivity(i);
                //getMiTezMoreno(tablero.get(miPersonajePos).getColor_Piel());
                //EnviarRespuestaalBusDeDatos
                break;
            case "?peloCafe":
                //getMiPeloCafe(tablero.get(miPersonajePos).getColor_Cabello());
                //
                break;
            case "?peloTratado":
                //getMiPeloTratado(tablero.get(miPersonajePos).getColor_Cabello());
                //
                break;
            case "?ojosClaros":
                i.putExtra("Pregunta", "?ojosClaros");
                i.putExtra("idJuego", idJuego);
                startActivity(i);
                //getMisOjosClaros(tablero.get(miPersonajePos).getColor_Ojos());
                //
                break;
            case "?lentes":
                i.putExtra("Pregunta", "?lentes");
                i.putExtra("idJuego", idJuego);
                startActivity(i);
                //getMisLentes(tablero.get(miPersonajePos).isLentes());
                //
                break;
            case "?estudianteCeti":
                i.putExtra("Pregunta", "?estudianteCeti");
                i.putExtra("idJuego", idJuego);
                startActivity(i);
                //getMiEstudianteCeti(tablero.get(miPersonajePos).isEstudiante());
                break;
            default:
                Toast.makeText(activity_juego.this, Pregunta,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void getTablero() {
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET,
                URLTablero,
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
                                if (jsonObject.getString("Genero_Masculino").equals("0")) {
                                    personaje.setGenero_Masculino(false);
                                } else {
                                    personaje.setGenero_Masculino(true);
                                }
                                if (jsonObject.getString("Estudiante").equals("0")) {
                                    personaje.setEstudiante(false);
                                } else {
                                    personaje.setEstudiante(true);
                                }
                                if (jsonObject.getString("Lentes").equals("0")) {
                                    personaje.setLentes(false);
                                } else {
                                    personaje.setLentes(true);
                                }
                                personaje.setColor_Ojos(jsonObject.getString("FK_ColorOjos"));
                                personaje.setColor_Piel(jsonObject.getString("FK_ColorPiel"));
                                personaje.setColor_Cabello(jsonObject.getString("FK_ColorCabello"));

                                personaje.setURL_Foto(personaje.getURL_Foto() + jsonObject.getString("Foto"));
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
                        Toast.makeText(activity_juego.this, "" + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {


        };
        RequestQueue pide = Volley.newRequestQueue(this);
        pide.add(postRequest);
    }

    public void startGame(View view) {
        if (!fAdapter.personajeElegido.equals("kk")) {
            switch (JugadoresL) {
                case "00":
                    if (jugadorP) {
                        db.collection("Juego").document(idJuego)
                                .update(KEY_JUGADORESL, "10");
                    } else {
                        db.collection("Juego").document(idJuego)
                                .update(KEY_JUGADORESL, "01");
                    }
                    break;
                case "01":
                    db.collection("Juego").document(idJuego)
                            .update(KEY_JUGADORESL, "11",
                                    KEY_PARTIDAENCURSO, true);
                    break;
                case "10":
                    db.collection("Juego").document(idJuego)
                            .update(KEY_JUGADORESL, "11",
                                    KEY_PARTIDAENCURSO, true);
                    break;
            }
            //Toast.makeText(activity_juego.this,""+JugadoresL,Toast.LENGTH_SHORT).show();
            fAdapter.juegoEmpezado = true;
            miPersonaje = fAdapter.personajeElegido;
            //AGREGAR subir al firebase nombre, dependiendo si es el uno o dos
            bAceptar.setVisibility(View.INVISIBLE);
            juegoLayout.setVisibility(View.VISIBLE);
            tvElige.setVisibility(View.INVISIBLE);
            //Toast.makeText(this, personajes.get(0).getNombre(), Toast.LENGTH_SHORT).show();
            encontrarPersonaje();
        } else {
            Toast.makeText(this, "Elige a un personaje antes.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void encontrarPersonaje() {
        for (int i = 0; i < tablero.size(); i++) {
            if (tablero.get(i).getNombre() == miPersonaje) {
                miPersonajePos = i;
                break;
            }

        }
        Picasso.get()
                .load(tablero.get(miPersonajePos).getURL_Foto())
                .into(ivMiPersonaje);
        tvMiPersonaje.setText(tablero.get(miPersonajePos).getNombre());

    }

    boolean getMiGeneroMasculino(Boolean genero) {

        return genero;
    }

    boolean getMiTezMoreno(String tez) {
        if (tez.equals("7F5E5E")) {
            return true;
        } else {
            return false;
        }
    }

    boolean getMiPeloCafe(String peloCafe) {
        if (peloCafe.equals("281F1F")) {
            return true;
        } else {
            return false;
        }
    }

    boolean getMiPeloTratado(String peloTratado) {
        if (peloTratado.equals("FFFFFF")) {
            return true;
        } else {
            return false;
        }
    }

    boolean getMisOjosClaros(String ojosClaros) {
        if (ojosClaros.equals("281F1F")) {
            return false;
        } else {
            return true;
        }
    }

    boolean getMisLentes(Boolean lentes) {

        return lentes;
    }

    boolean getMiEstudianteCeti(Boolean estudianteCeti) {
        return estudianteCeti;
    }
}
