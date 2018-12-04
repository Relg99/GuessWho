package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    public String URL = "https://guess-who-223421.appspot.com/login.php";
    EditText etUsuario, etPass;
    TextView tvLoginError;
    boolean loginCorrecto=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUsuario = findViewById(R.id.etUsuario);
        etPass = findViewById(R.id.etContraseña);
        tvLoginError=findViewById(R.id.tvLoginError);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout((int) (WindowManager.LayoutParams.WRAP_CONTENT), (int) (WindowManager.LayoutParams.WRAP_CONTENT));
    }

    public void clickInicioSesion(View view) {
        tvLoginError.setText("");

        if (etPass.getText().toString().equals("") || etUsuario.getText().toString().equals("")) {
            tvLoginError.setText("No dejes espacios vacíos.");

        } else {


            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                        if(response.equals("1")){
                            loginCorrecto=true;
                            Intent i = new Intent(LogInActivity.this, MenuActivity.class);
                            i.putExtra("gamertag", etUsuario.getText().toString());
                            startActivity(i);
                        }else{
                            tvLoginError.setText("Verifica tus credenciales.");
                        }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(LogInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("gamertag", etUsuario.getText().toString());
                    params.put("contraseña", etPass.getText().toString());

                    return params;
                }
            };
            RequestQueue pide = Volley.newRequestQueue(this);

              pide.add(postRequest);
        }


    }

    public void clickRegistro (View view) {
        try {
            Intent i = new Intent(this, RegistroActivity.class);
            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }
}
