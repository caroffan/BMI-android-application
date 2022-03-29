package com.example.imc;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class MainActivity extends AppCompatActivity {


    private EditText poids = null;
    private EditText taille = null;
    private int metre = 0;
    private int centimetre = 0;
    private RadioGroup unite = null;
    private CheckBox megaFonction = null;
    private Button imc = null;
    private Button raz = null;
    private TextView aide = null;
    private double res = 0;
    private ImageView logo = null;
    private Spinner genre = null;

    public final static String CUSTOM_EXTRA = "RESULTAT";
    public final static String IMC = "imcStock";

    private View.OnClickListener clickBoutonIMC = new View.OnClickListener() {
        public void onClick(View v) {
            String kgpoids = poids.getText().toString();
            String mtaille = taille.getText().toString();
            double valuepoids = 0;
            double valuetaille = 0;

            if (!"".equals(kgpoids)) {
                valuepoids = Double.parseDouble(kgpoids);
            }
            if (!"".equals(mtaille)) {
                valuetaille = Double.parseDouble(mtaille);
            }
            if (valuetaille != 0) {
                if (metre == 1) {
                    res = valuepoids / (valuetaille * valuetaille);


                } else if (centimetre == 1) {
                    valuetaille = valuetaille * 0.01;
                    res = valuepoids / (valuetaille * valuetaille);


                }

                if (megaFonction.isChecked() == true) {
                    String genreSelected = genre.getSelectedItem().toString();
                    String chaineRes = null;
                    if (genreSelected == "Masculin") {
                        chaineRes = getResources().getString(R.string.imc_poli_Monsieur);
                    } else if (genreSelected == "Féminin") {
                        chaineRes = getResources().getString(R.string.imc_poli_Madame);
                    }
                    aide.setText(chaineRes + res);
                } else if (megaFonction.isChecked() == false) {
                    String chaineRes2 = getResources().getString(R.string.imc);
                    aide.setText(chaineRes2 + res);
                }



            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT);
                toast.show();
            }



        }
    };
    private View.OnClickListener clickBoutonRAZ = new View.OnClickListener() {
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
        }
    };

    private View.OnClickListener clickBoutonRetour = new View.OnClickListener() {
        public void onClick(View v) {
            Intent ConnexionActivity = new Intent(MainActivity.this, ConnexionActivity.class);
            ConnexionActivity.putExtra(CUSTOM_EXTRA, res);
            startActivity(ConnexionActivity);
        }
    };


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            aide.setText(R.string.text);
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };


    private RadioGroup.OnCheckedChangeListener changeUnite = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            View radioButton = radioGroup.findViewById(i);
            int index = radioGroup.indexOfChild(radioButton);
            switch (index) {
                case 0:
                    metre = 1;
                    centimetre = 0;
                    break;
                case 1:
                    metre = 0;
                    centimetre = 1;
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Active le bouton Up
        ab.setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(clickBoutonRetour);


        genre = (Spinner) findViewById(R.id.genre);
        List<String> genres = new ArrayList<String>();
        genres.add("Masculin");
        genres.add("Féminin");

        poids = (EditText) findViewById(R.id.Poids);
        taille = (EditText) findViewById(R.id.Taille);

        megaFonction = (CheckBox) findViewById(R.id.checkBox);
        imc = (Button) findViewById(R.id.IMC);
        raz = (Button) findViewById(R.id.RAZ);
        aide = (TextView) findViewById(R.id.aide);
        megaFonction = (CheckBox) findViewById(R.id.checkBox);
        logo = (ImageView) findViewById(R.id.logo);
        unite = (RadioGroup) findViewById(R.id.radioGroup);

        imc.setOnClickListener(clickBoutonIMC);
        raz.setOnClickListener(clickBoutonRAZ);

        unite.setOnCheckedChangeListener(changeUnite);
        poids.addTextChangedListener(textWatcher);
        taille.addTextChangedListener(textWatcher);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        logo.startAnimation(animation);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre.setAdapter(adapter);






    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.calendar:



                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;

            case R.id.mail:
                openURL();
                sendEmail();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            //R.id.calendar.setText(ConverterDate.ConvertDate(year, month + 1, day));
        }
    }

    public void openURL( )  {
        String url="https://google.com";

        // An implicit intent, request a URL.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(intent);
    }

    public void sendEmail( )  {

        // List of recipients
        String[] recipients=new String[]{""};

        String subject = getResources().getString(R.string.objetMail);

        String content = getResources().getString(R.string.textMail)+res;

        Intent intentEmail = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        intentEmail.putExtra(Intent.EXTRA_EMAIL, recipients);
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        intentEmail.putExtra(Intent.EXTRA_TEXT, content);

        intentEmail.setType("text/plain");

        startActivity(Intent.createChooser(intentEmail, "Choose an email client from..."));
    }
}
