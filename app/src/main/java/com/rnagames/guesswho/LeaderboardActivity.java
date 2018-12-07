package com.rnagames.guesswho;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rnagames.guesswho.Adapter.LeaderBoardAdapter;
import com.rnagames.guesswho.Pojos.Pojo_Leaderboard;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView rvVista;
    LeaderBoardAdapter fAdapter;
    ArrayList<Pojo_Leaderboard> arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        rvVista=findViewById(R.id.rvLeaderboard);
        fAdapter = new LeaderBoardAdapter();
        LinearLayoutManager llmOrientacion;
        llmOrientacion = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);

        arreglo = new ArrayList<>();
        fAdapter.contexto = this;
        fAdapter.datos = arreglo;
        rvVista.setAdapter(fAdapter);
        rvVista.setLayoutManager(llmOrientacion);
        mostrar();
    }
    public void mostrar() {
    JsonArrayRequest peticion = new JsonArrayRequest(
            Request.Method.GET,
            "https://guess-who-223421.appspot.com/selectLeaderboard.php",
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            Pojo_Leaderboard puntaje = new Pojo_Leaderboard();
                            puntaje.setLeaderBoard_ID(jsonObject.getInt("LeaderBoard_ID"));
                            puntaje.setGanador(jsonObject.getString("FK_Ganador"));
                            puntaje.setPerdedor(jsonObject.getString("FK_Perdedor"));
                            puntaje.setNivelGanador(jsonObject.getInt("NivelGanador"));
                            puntaje.setNivelPerdedor(jsonObject.getInt("NivelPerdedor"));

                            arreglo.add(puntaje);
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
                    Toast.makeText(LeaderboardActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
    );
    RequestQueue pide = Volley.newRequestQueue(this);
        pide.add(peticion);
}

}
