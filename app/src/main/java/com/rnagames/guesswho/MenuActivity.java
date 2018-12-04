package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MENSAJEEEE";
    public String gamertag;
    public int res = 1;
    public boolean sesionIniciada=false;
    public Button bRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bRegistro=findViewById(R.id.bRegistro);
        String nombreUsuario = "Ricardo";
        Bundle recibirUsuario = getIntent().getExtras();
        checkInicioSesion(recibirUsuario);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference animalRef = database.getReference("Animal/Gato");
        DatabaseReference crearPartidaRef = database.getReference("Juego/"+nombreUsuario);

        animalRef.setValue("Blanco");
        crearPartidaRef.setValue(1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated..
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

//WWAA

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    public void clikJugar (View view) {
        if(sesionIniciada==false) {
            try {
                Intent i = new Intent(this, LogInActivity.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this, "Ingrese datos.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Intent i = new Intent(this, activity_lobby.class);
            i.putExtra("gamertag", gamertag);
            startActivity(i);
        }
    }

    public void clikRegistro (View view) {
        if(sesionIniciada==false) {
            try {
                Intent i = new Intent(this, RegistroActivity.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this, "Ingrese datos.", Toast.LENGTH_SHORT).show();
            }
        }else{
            bRegistro.setText("Registro");
            sesionIniciada=false;
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickLeaderboard (View view) {
        try {

            Intent i = new Intent(this, LeaderboardActivity.class);
           // i.putExtra("facnum", res);
            // Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }

    public void clickPersonajes (View view) {
        try {
            Intent i = new Intent(this, PersonajesActivity.class);
           // i.putExtra("facnum", res);
            // Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }
    public void checkInicioSesion(Bundle recibirUsuario){

        if(recibirUsuario != null) {
            gamertag = recibirUsuario.getString("gamertag");
            sesionIniciada=true;
            bRegistro.setText("Cerrar Sesion");
                Intent i = new Intent(MenuActivity.this, activity_lobby.class);
                i.putExtra("gamertag", gamertag);
                startActivity(i);
            }



    }
}
