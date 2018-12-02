package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PersonajesActivity extends AppCompatActivity {

    public int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);

        Intent i = getIntent();

        res = i.getIntExtra("facnum",-200);
    }
}
