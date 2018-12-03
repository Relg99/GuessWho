package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistroActivity extends AppCompatActivity {

    public int res;
    public static final String TAG = "MENSAJEEEE";

    TextView tvNombre,tvApellido,tvGamerTag, tvCorreo, tvContraseña1, getTvContraseña2;
    EditText etNombre, etApellido, etGamerTag, etCorreo, etContraseña1, etContraseña2;

    String sNombre, sApellido, sGamerTag, sCorreo, sContraseña1, sContraseña2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etGamerTag = findViewById(R.id.etGamerTag);
        etCorreo = findViewById(R.id.etCorreo);
        etContraseña1 = findViewById(R.id.etContraseñaIntento1);
        etContraseña2 = findViewById(R.id.etContraseñaIntento2);



        Intent i = getIntent();

        res = i.getIntExtra("facnum",-200);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        /*
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
        */
    }

    public void clickVerificar (View view)
    {
        if (etNombre.getText().toString().equals("")||etApellido.getText().toString().equals("")||etGamerTag.getText().toString().equals("")||etCorreo.getText().toString().equals("")||etContraseña1.getText().toString().equals("")||etContraseña2.getText().toString().equals(""))
        {
            Toast.makeText(this,"No ha completado todos los datos.",Toast.LENGTH_SHORT).show();
        }
        else
        {

        }

    }

    public void clickCancelar (View view)
    {
        finish();
    }


}
