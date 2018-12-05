package com.rnagames.guesswho;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rnagames.guesswho.Adapter.PartidaAdapter;
import com.rnagames.guesswho.Pojos.PojoPartida;

import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.firestore.FieldValue.delete;


public class activity_lobby extends AppCompatActivity {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference cola = db.collection("Cola");
    public PartidaAdapter partidaAdapter;
    String getNivelURL="https://guess-who-223421.appspot.com/getNivel.php";
        String gamertag;
        String  TAG = "MENSAJE!";
        TextView tvGamertag,tvNivel;
        RecyclerView rvCola;
        Button bCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        tvGamertag=findViewById(R.id.tvGamertag);
        tvNivel=findViewById(R.id.tvNivel);
        rvCola = findViewById(R.id.rvListaPartidas);
        bCrear = findViewById(R.id.bCrearPartida);

        Bundle recibirUsuario = getIntent().getExtras();
        gamertag = recibirUsuario.getString("gamertag");
        tvGamertag.setText(gamertag);
      getNivel();

    generarRecyclerView();

    }

    public void generarRecyclerView(){
        Query query = cola.orderBy("Nombre", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<PojoPartida> opciones = new FirestoreRecyclerOptions.Builder<PojoPartida>()
                .setQuery(query,PojoPartida.class)
                .build();

        partidaAdapter = new PartidaAdapter(opciones);
        rvCola.setHasFixedSize(true);
        rvCola.setLayoutManager(new LinearLayoutManager(this));
        rvCola.setAdapter(partidaAdapter);


    }
    @Override
    protected void onStart(){
    super.onStart();
    partidaAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        partidaAdapter.stopListening();

        //db.collection("cities").document(documentReference.getId()).delete();

    }
    public void getNivel( ){

      StringRequest postRequest = new StringRequest(Request.Method.POST, getNivelURL,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                   tvNivel.setText(tvNivel.getText().toString()+response);
                  }
              },
              new Response.ErrorListener()
              {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      // error
                      Toast.makeText(activity_lobby.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              }
      ) {
          @Override
          protected Map<String, String> getParams()
          {
              Map<String, String>  params = new HashMap<>();
              params.put("gamertag", gamertag);


              return params;
          }
      };
      RequestQueue pide = Volley.newRequestQueue(this);

      pide.add(postRequest);
  }

  public void clickCrearPartida (View view) {
      // Create a new user with a first and last name
      Map<String, Object> user = new HashMap<>();
      user.put("Nombre", gamertag);
      user.put("Vista", 2);

      // Add a new document with a generated ID
      db.collection("Cola")
              .add(user)
              .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                  @Override
                  public void onSuccess(DocumentReference documentReference) {
                      Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                      Toast.makeText(activity_lobby.this,"Esperando",Toast.LENGTH_SHORT).show();
                      rvCola.setVisibility(View.INVISIBLE);
                      bCrear.setVisibility(View.INVISIBLE);
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Log.w(TAG, "Error adding document", e);
                  }
              });
  }
}
