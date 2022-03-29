package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConnexionActivity extends AppCompatActivity {


    private EditText Name=null;
    private String nom=null;
    private Button connexion=null;
    public double resultat=0;
    private String affichage=null;
    private TextView memoire=null;

    public final static String IMC = "imcStock";
    public final static String NAME = "nomStock";

    private View.OnClickListener clickBouton = new View.OnClickListener() {
        public void onClick(View v) {

            if(Name.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), R.string.errorName, Toast.LENGTH_SHORT);
                toast.show();
            }else{
                nom = Name.getText().toString();
                SharedPreferences preferences =  getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(NAME, nom);
                editor.apply();
                Intent mainActivity = new Intent(ConnexionActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        }
    };

    private View.OnClickListener clickName = new View.OnClickListener() {
        public void onClick(View v) {
            Name.getText().clear();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        memoire = (TextView) findViewById(R.id.memoire);
        Name = (EditText) findViewById(R.id.nom);
        connexion = (Button) findViewById(R.id.calculIMC);
        Name.setOnClickListener(clickName);
        connexion.setOnClickListener(clickBouton);
        resultat = getIntent().getDoubleExtra(MainActivity.CUSTOM_EXTRA, 0);


        affichage = getResources().getString(R.string.imc) + resultat;
        if (resultat!=0) {
            Toast toast = Toast.makeText(getApplicationContext(), affichage, Toast.LENGTH_LONG);
            toast.show();
            String resString = ""+resultat;
            SharedPreferences preferences2 =  getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString(IMC, resString);

            editor.apply();

        }



        SharedPreferences preferences =  getPreferences(MODE_PRIVATE);
        SharedPreferences preferences2 =  getPreferences(MODE_PRIVATE);


        String imc = preferences2.getString(MainActivity.IMC, "0");
        String prenom = preferences.getString(NAME, "");
        memoire.setText("Bonjour ! "+prenom +" Votre ancien IMC : " + imc);
        Name.setText(prenom);


    }
}