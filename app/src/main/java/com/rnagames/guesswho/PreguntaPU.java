package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rnagames.guesswho.Pojos.PojoJuego;

import java.util.HashMap;
import java.util.Map;

public class PreguntaPU extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String KEY_TURNOJP = "turnoJP";
    private static final String KEY_PREGUNTA = "pregunta";

    public TextView tvPregunta;
    public Button bRespuesta1;
    public Button bRespuesta2;
    public Button bRespuesta3;

    public String Pregunta;
    public String tPregunta;
    public String tRespuesta1;
    public String tRespuesta2;
    public String tRespuesta3;
    public String idJuego;
    public Boolean TurnoJp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_pu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        tvPregunta = findViewById(R.id.tvPregunta);
        bRespuesta1 = findViewById(R.id.bRespuesta1);
        bRespuesta2 = findViewById(R.id.bRespuesta2);
        bRespuesta3 = findViewById(R.id.bRespuesta3);

        Intent i = getIntent();

        Pregunta = i.getStringExtra("Pregunta");
        idJuego = i.getStringExtra("idJuego");

        db.collection("Juego").document(idJuego)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PojoJuego Pregunta = documentSnapshot.toObject(PojoJuego.class);
                TurnoJp = Pregunta.getTurnoJP();
            }
        });

        PUPregunta();
    }

    public void PUPregunta() {
        switch (Pregunta) {
            case "?genero":
                tPregunta = "¿Tu personaje es masculino?";
                tRespuesta1 = "Si";
                tRespuesta2 = "No";

                tvPregunta.setText(tPregunta);
                bRespuesta1.setText(tRespuesta1);
                bRespuesta2.setText(tRespuesta2);
                bRespuesta3.setVisibility(View.INVISIBLE);
                break;
            case "?tez":
                tPregunta = "¿Tu personaje es moreno?";
                tRespuesta1 = "Si";
                tRespuesta2 = "No";

                tvPregunta.setText(tPregunta);
                bRespuesta1.setText(tRespuesta1);
                bRespuesta2.setText(tRespuesta2);
                bRespuesta3.setVisibility(View.INVISIBLE);
                break;
            case "?ojosClaros":
                tPregunta = "¿Tu personaje tiene ojos de color?";
                tRespuesta1 = "Si";
                tRespuesta2 = "No";

                tvPregunta.setText(tPregunta);
                bRespuesta1.setText(tRespuesta1);
                bRespuesta2.setText(tRespuesta2);
                bRespuesta3.setVisibility(View.INVISIBLE);
                break;
            case "?lentes":
                tPregunta = "¿Tu personaje tiene lentes?";
                tRespuesta1 = "Si";
                tRespuesta2 = "No";

                tvPregunta.setText(tPregunta);
                bRespuesta1.setText(tRespuesta1);
                bRespuesta2.setText(tRespuesta2);
                bRespuesta3.setVisibility(View.INVISIBLE);
                break;
            case "?estudianteCeti":
                tPregunta = "¿Tu personaje es estudiante del CETI?";
                tRespuesta1 = "Si";
                tRespuesta2 = "No";

                tvPregunta.setText(tPregunta);
                bRespuesta1.setText(tRespuesta1);
                bRespuesta2.setText(tRespuesta2);
                bRespuesta3.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void clickRespuesta1(View view) {
        switch (Pregunta) {
            case "?genero":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Sexo: masculino");
                CambiarTurno();
                this.finish();
                break;
            case "?tez":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Tez: Morena");
                CambiarTurno();
                this.finish();
                break;
            case "?ojosClaros":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Ojos: Claros");
                CambiarTurno();
                this.finish();
                break;
            case "?lentes":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Lentes: Si");
                CambiarTurno();
                this.finish();
                break;
            case "?estudianteCeti":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Estudiante CETI: Si");
                CambiarTurno();
                this.finish();
                break;
        }
    }

    public void clickRespuesta2(View view) {
        switch (Pregunta) {
            case "?genero":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Sexo: femenino");
                CambiarTurno();
                this.finish();
                break;
            case "?tez":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Tez: Clara");
                CambiarTurno();
                this.finish();
                break;
            case "?ojosClaros":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Ojos: Oscuros");
                CambiarTurno();
                this.finish();
                break;
            case "?lentes":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Lentes: No");
                CambiarTurno();
                this.finish();
                break;
            case "?estudianteCeti":
                db.collection("Juego").document(idJuego)
                        .update(KEY_PREGUNTA, "Estudiante CETI: No");
                CambiarTurno();
                this.finish();
                break;
        }
    }

    public void clickRespuesta3(View view) {

    }

    public void CambiarTurno() {
        if (TurnoJp) {
            db.collection("Juego").document(idJuego)
                    .update(KEY_TURNOJP, false);
        } else {
            db.collection("Juego").document(idJuego)
                    .update(KEY_TURNOJP, true);
        }
    }
}
