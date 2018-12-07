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

    public EditText etNombre;
    public EditText etApellido;
    public EditText etGamerTag;
    public EditText etCorreo;
    public EditText etContrasena1;
    public EditText etContrasena2;

    public boolean GamerTagExistente = false;
    public boolean CorreoExistente = false;
    public boolean ContrasenasCoinciden = false;
    public boolean correoValidado = false;
    public boolean tamanoValidado = false;
    public String URLCheck = "http://guess-who-223421.appspot.com/ChecarDuplicado.php";
    public String URLSubir = "http://guess-who-223421.appspot.com/SubirRegistro.php";
    public String sContrasena1;
    public String sContrasena2;

    //
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
    }

    public void clickVerificar(View view) {
        if (etNombre.getText().toString().equals("")
                || etApellido.getText().toString().equals("")
                || etGamerTag.getText().toString().equals("")
                || etCorreo.getText().toString().equals("")
                || etContrasena1.getText().toString().equals("")
                || etContrasena2.getText().toString().equals("")) {
            Toast.makeText(this, "No ha completado todos los datos.",
                    Toast.LENGTH_SHORT).show();
        } else {
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
                                    Toast.makeText(RegistroActivity.this, "El gamertag ya está en uso.", Toast.LENGTH_SHORT).show();
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
                                    checarEmail();
                                    tamanoValidado();
                                    if (ContrasenasCoinciden && correoValidado && tamanoValidado) {
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
                            Toast.makeText(RegistroActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void checarContrasenas() {
        sContrasena1 = etContrasena1.getText().toString();
        sContrasena2 = etContrasena2.getText().toString();
        if (sContrasena1.equals(sContrasena2)) {
            ContrasenasCoinciden = true;
        } else {
            etContrasena2.setError("Sus contraseñas no coinciden.");
            ContrasenasCoinciden = false;
        }
    }

    public void checarEmail() {

        String email = etCorreo.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            correoValidado = true;
        } else {
            etCorreo.setError("Email invalido!");
            correoValidado = false;
        }
    }

    public void tamanoValidado() {
        if (etNombre.length() < 2 || etNombre.length() > 16 || etApellido.length() < 2 ||
                etApellido.length() > 20 || etGamerTag.length() < 4 || etGamerTag.length() > 10 ||
                etCorreo.length() > 40 || etContrasena1.length() < 6 || etContrasena1.length() > 20) {
            Toast.makeText(this, "Verifique que los campos de tu registro sean correctos"
                    , Toast.LENGTH_SHORT).show();
            tamanoValidado = false;
        } else {
            tamanoValidado = true;
        }
        if (etNombre.length() < 2 || etNombre.length() > 16) {
            etNombre.setError("El nombre debe contener mínimo 2 carácteres y máximo 16");
        }
        if (etApellido.length() < 2 || etApellido.length() > 20) {
            etApellido.setError("El apellído debe contener mínimo 2 carácteres y máximo 20");
        }
        if (etGamerTag.length() < 4 || etGamerTag.length() > 10) {
            etGamerTag.setError("El gamertag debe contener mínimo 4 carácteres y máximo 10");
        }
        if (etCorreo.length() > 40) {
            etCorreo.setError("El correo debe contener máximo 40 carácteres");
        }
        if (etContrasena1.length() < 6 || etContrasena1.length() > 20) {
            etContrasena1.setError("La contraseña debe tener mínimo 6 carácteres y máximo 20");
        }
    }

    public void subirDatos() {
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
                        Toast.makeText(RegistroActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("sNombre", etNombre.getText().toString());
                params.put("sApellido", etApellido.getText().toString());
                params.put("sGamerTag", etGamerTag.getText().toString());
                params.put("sCorreo", etCorreo.getText().toString());
                params.put("sContraseña1", etContrasena1.getText().toString());

                return params;
            }
        };
        RequestQueue pide = Volley.newRequestQueue(this);

        pide.add(postRequest);
        Intent i = new Intent(this, MenuActivity.class);
        i.putExtra("gamertag", etGamerTag.getText().toString());
        i.putExtra("lobby", 0);
        i.putExtra("usuarioEnviado", 1);

        startActivity(i);
    }

    public void clickCancelar(View view) {
        finish();
    }


}
