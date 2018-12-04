package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    public int res;
    public static final String TAG = "MENSAJEEEE";

    EditText etNombre, etApellido, etGamerTag, etCorreo, etContraseña1, etContraseña2;

    public boolean GamerTagExistente=false,CorreoExistente=false;
    public String URL = "http://192.168.56.1/ejercicios/PM/ChecarDuplicado.php";


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
        else {


            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            switch (response) {
                                case "11":
                                    GamerTagExistente = true;
                                    CorreoExistente = true;
                                    Toast.makeText(RegistroActivity.this, "Tu Gamer Tag y Correo ya estan vinculados a una cuenta.", Toast.LENGTH_SHORT).show();
                                    break;

                                case "10":
                                    GamerTagExistente = true;
                                    CorreoExistente = false;
                                    Toast.makeText(RegistroActivity.this, "El gamer tag ya esta en uso.", Toast.LENGTH_SHORT).show();
                                    break;

                                case "01":
                                    CorreoExistente = true;
                                    GamerTagExistente = false;
                                    Toast.makeText(RegistroActivity.this, "Tu correo ya esta vinculado a una cuenta.", Toast.LENGTH_SHORT).show();
                                    break;

                                case "00":
                                    GamerTagExistente = false;
                                    CorreoExistente = false;
                                    break;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(RegistroActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("gamertag", etGamerTag.getText().toString());
                    params.put("correo", etCorreo.getText().toString());

                    return params;
                }
            };
            RequestQueue pide = Volley.newRequestQueue(this);

            pide.add(postRequest);
        }

    }

    public void clickCancelar (View view)
    {
        finish();
    }


}
