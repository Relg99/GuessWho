package com.rnagames.guesswho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    public int res = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Intent i = getIntent();

       // res = i.getIntExtra("facnum",-200);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.4));
    }

    public void clickInicioSesion (View view)
    {

    }


    public void clickRegistro (View view)
    {
        try {

            Intent i = new Intent(this, RegistroActivity.class);
            //i.putExtra("facnum", res);
            // Toast.makeText(this,""+res,Toast.LENGTH_SHORT).show();

            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Ingrese datos.",Toast.LENGTH_SHORT).show();
        }

    }
}
