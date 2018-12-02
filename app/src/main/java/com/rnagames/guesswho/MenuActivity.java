package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MENSAJEEEE";

    public int res = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String nombreUsuario = "Ricardo";

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference animalRef = database.getReference("Animal/Gato");
        DatabaseReference crearPartidaRef = database.getReference("Juego/"+nombreUsuario);

        animalRef.setValue("Blanco");
        crearPartidaRef.setValue(1);
        //myRef.setValue("Hello, World!");

        //Esto si es importante!

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    public void clikJugar (View view)
    {
        try {

            Intent i = new Intent(this, LogInActivity.class);
            //i.putExtra("facnum", res);
           // Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }

    public void clikRegistro (View view)
    {
        try {

            Intent i = new Intent(this, RegistroActivity.class);
            i.putExtra("facnum", res);
            // Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }
}
