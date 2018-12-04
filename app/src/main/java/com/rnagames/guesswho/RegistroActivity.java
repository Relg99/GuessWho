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

    EditText etNombre, etApellido, etGamerTag, etCorreo, etContrasena1, etContrasena2;

    public boolean GamerTagExistente=false,CorreoExistente=false,ContrasenasCoinciden = false;
    public String URLCheck = "http://guess-who-223421.appspot.com/ChecarDuplicado.php";
    public String URLSubir = "http://guess-who-223421.appspot.com/SubirRegistro.php";
    public String sContrasena1, sContrasena2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etGamerTag = findViewById(R.id.etGamerTag);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena1 = findViewById(R.id.etContraseñaIntento1);
        etContrasena2 = findViewById(R.id.etContraseñaIntento2);

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
        if (etNombre.getText().toString().equals("")||etApellido.getText().toString().equals("")||etGamerTag.getText().toString().equals("")||etCorreo.getText().toString().equals("")||etContrasena1.getText().toString().equals("")||etContrasena2.getText().toString().equals(""))
        {
            Toast.makeText(this,"No ha completado todos los datos.",Toast.LENGTH_SHORT).show();
        }
        else {

            StringRequest postRequest = new StringRequest(Request.Method.POST, URLCheck,
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
                                    checarContrasenas();
                                    if (ContrasenasCoinciden)
                                    {
                                        subirDatos();
                                    }
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
                    Map<String, String> params = new HashMap<>();
                    params.put("gamertag", etGamerTag.getText().toString());
                    params.put("correo", etCorreo.getText().toString());

                    return params;
                }

            };
            RequestQueue pide = Volley.newRequestQueue(this);

            pide.add(postRequest);
        }

    }

    public void checarContrasenas ()
    {
        sContrasena1 = etContrasena1.getText().toString();
        sContrasena2 = etContrasena2.getText().toString();
        if (sContrasena1.equals(sContrasena2))
        {
            ContrasenasCoinciden = true;
        }
        else
        {
            Toast.makeText(this,"Sus contraseñas no coinciden.",Toast.LENGTH_SHORT).show();
        }

    }

    public void subirDatos ()
    {

            StringRequest postRequest = new StringRequest(Request.Method.POST, URLSubir,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

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
                    Map<String, String> params = new HashMap<>();

                    params.put("sNombre",etNombre.getText().toString());
                    params.put("sApellido",etApellido.getText().toString());
                    params.put("sGamerTag",etGamerTag.getText().toString());
                    params.put("sCorreo",etCorreo.getText().toString());
                    params.put("sContraseña1",etContrasena1.getText().toString());

                    return params;
                }

            };
            RequestQueue pide = Volley.newRequestQueue(this);

            pide.add(postRequest);
        //----------------------------------------------

        try {
            Intent i = new Intent(this, LogInActivity.class);
             i.putExtra("facnum", res);
             Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }
    }

    public void clickCancelar (View view)
    {
        finish();
    }


}
