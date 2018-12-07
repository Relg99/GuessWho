package com.rnagames.guesswho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CaracteristicasActivity extends AppCompatActivity {
    TextView ID, Nombre, GeneroProfesion, Lentes, Ojos, Piel, Cabello;
    ImageView fotazo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);
        fotazo=findViewById(R.id.ivPersonajeCaracteristica);
        ID = findViewById(R.id.tvRegistroCaracteristica);
        Nombre = findViewById(R.id.tvNombreCaracteristica);
        Lentes = findViewById(R.id.tvLentes);
        Ojos = findViewById(R.id.tvcolorOjos);
        GeneroProfesion = findViewById(R.id.tvProfesionGenero);
        Piel = findViewById(R.id.tvColorpiel);
        Cabello = findViewById(R.id.tvcolorCabello);
        Bundle getPersonaje = getIntent().getExtras();
        ID.setText("Registro: " + getPersonaje.getString("ID"));
        Nombre.setText(getPersonaje.getString("Nombre"));
        if (getPersonaje.getBoolean("Profesion") == false) {
            if (getPersonaje.getString("Nombre").equals("Coco") || getPersonaje.getString(
                    "Nombre").equals("Areli") || getPersonaje.getString("Nombre").equals("Gusa")
                    || getPersonaje.getString("Nombre").equals("Kenai")
                    || getPersonaje.getString("Nombre").equals("Pamplo") || getPersonaje.getString("Nombre").equals("Toño")
                    || getPersonaje.getString("Nombre").equals("Nancy") || getPersonaje.getString("Nombre").equals("Ulyses")) {
                GeneroProfesion.setText("Docente:");
            } else if (getPersonaje.getString("Nombre").equals("Roxana") || getPersonaje.getString("Nombre").equals("Hector")
                    || getPersonaje.getString("Nombre").equals("Tony") || getPersonaje.getString("Nombre").equals("Coco")) {
                GeneroProfesion.setText("Baja definitiva:");

            } else {
                GeneroProfesion.setText("Alumno:");

            }

        }
        if (getPersonaje.getBoolean("Genero") == false) {
            GeneroProfesion.setText(GeneroProfesion.getText() + "Mujer");
        } else {
            GeneroProfesion.setText(GeneroProfesion.getText() + "Hombre");

        }
        if (getPersonaje.getBoolean("Lentes") == true) {
            Lentes.setText("Usa lentes");
        } else {
            Lentes.setText("No usa lentes");
        }
        if (getPersonaje.getString("Ojos").equals("296030")) {
            Ojos.setText("Ojos claros");
        } else {
            Ojos.setText("Ojos oscuros");
        }
        if (getPersonaje.getString("Piel").equals("DAB2B2")) {
            Piel.setText("Tez clara");
        } else {
            Piel.setText("Tez oscura");
        }
        if (getPersonaje.getString("Cabello").equals("281F1F")) {
            Cabello.setText("Cabello café");
        } else if (getPersonaje.getString("Cabello").equals("FFFFFF")) {
            Cabello.setText("Cabello con tratamiento");
        } else {
            Cabello.setText("Cabello negro");
        }

        Picasso.get()
                .load(getPersonaje.getString("Foto"))
                .into(fotazo);


    }
}