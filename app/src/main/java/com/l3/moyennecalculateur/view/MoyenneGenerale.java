package com.l3.moyennecalculateur.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.l3.moyennecalculateur.control.MarksView;
import com.l3.moyennecalculateur.control.MarksViewAdaptater;
import com.l3.moyennecalculateur.R;
import com.l3.moyennecalculateur.control.Module;
import com.l3.moyennecalculateur.model.ModuleDAO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoyenneGenerale extends AppCompatActivity {
    ListView listView;
    TextView moyenneView, remarqueView;
    ImageView image;
    ModuleDAO moduleDAO = new ModuleDAO(this);
    public static final String CHALLANGER = "Bravo! vous etes le meilleure et le plus intelligent homme du monde.";
    public static final String MASTER = "Excellent! t'es parmis les meilleure etudiants de la promo. ";
    public static final String PLATINIUM = "felicitation! je vous felecite d'avoir travailler si dur pour avoir une note pareil. ";
    public static final String GOLD = "trop bien ! je sais que vous etes intelligent,vous pouvez faire mieux.";
    public static final String SILVER = "Justesse !vous etes sur le point de refaire l'annee,saisissez la chance pour travailler dur.";
    public static final String BRONZE = "Rtrappage!c'est a deux doit de valider votre annee,profiter de bien travailler au rattrappage.";
    public static final String IRON = "Niveau trop faible!essayez de concentrez un peu sur vos etudes.";
    public static final String NO_REMARQUE = "Vuillez saisir vos notes afin qu'on puisse calculer votre moyenne !";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyenne_generale);

        image = (ImageView) findViewById(R.id.image);

        listView = findViewById(R.id.activity_moyenne_generale_listview);
        remarqueView = findViewById(R.id.activity_moyenne_generale_remarque_textview);
        moyenneView = findViewById(R.id.activity_moyenne_generale_00_00);

        this.configureToolbar();
        this.configureOutput();
    }

    private void configureOutput() {
        MarksViewAdaptater marksViewAdaptater = new MarksViewAdaptater(this, moduleDAO.getAllModule());
        listView.setAdapter(marksViewAdaptater);
        double moyenneGeneraleDouble = calculerMoyenne(moduleDAO.getAllModule());
        String moyenneGeneraleString = formatDouble(moyenneGeneraleDouble);
        setRemarque(moyenneGeneraleDouble);
        moyenneView.setText(moyenneGeneraleString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_moyenne_generale, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_refresh:
                configureOutput();
                Toast.makeText(this, "Table actualisé avec succée.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_activity_main_delete:
                showAlertDialogForClearDb(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialogForClearDb(Context context) {
        int nbModule = moduleDAO.getLength();
        if(nbModule != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Message de confirmation");
            builder.setMessage("Êtes vous sûr de vouloir supprimer les " +
                    nbModule +
                    " modules déjà enrgistrés ?");

            builder.setPositiveButton("Oui", (dialog, which) -> {
                moduleDAO.clearDb();
                configureOutput();
                Toast.makeText(context, nbModule + " module supprimé avec succée", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton(android.R.string.cancel, (dialog, which) ->
                    Toast.makeText(context, "Opération annulé", Toast.LENGTH_SHORT).show());
            AlertDialog dialog = builder.create();
            dialog.show();
        } else
            Toast.makeText(context, "Aucun module à supprimer, la DB est vide.", Toast.LENGTH_SHORT).show();
    }

    private void setRemarque(double moyenneGeneraleDouble) {
        if(moyenneGeneraleDouble > 19.5){
            remarqueView.setText(CHALLANGER);
            remarqueView.setTextColor(Color.GREEN);
            image.setImageResource(R.drawable.admis);

        }
        else if (moyenneGeneraleDouble > 17){
            remarqueView.setText(MASTER);
            remarqueView.setTextColor(Color.GREEN);
            image.setImageResource(R.drawable.admis);
        }
        else if (moyenneGeneraleDouble > 15){
            remarqueView.setText(PLATINIUM);
            remarqueView.setTextColor(Color.GREEN);
            image.setImageResource(R.drawable.admis);

        }
        else if (moyenneGeneraleDouble > 11){
            remarqueView.setText(GOLD);
            remarqueView.setTextColor(Color.GREEN);
            image.setImageResource(R.drawable.admis);

        }
        else if (moyenneGeneraleDouble > 9.9){
            remarqueView.setText(SILVER);
            remarqueView.setTextColor(Color.RED);
            image.setImageResource(R.drawable.ajourne);

        }
        else if (moyenneGeneraleDouble > 8){
            remarqueView.setText(BRONZE);
            remarqueView.setTextColor(Color.RED);
            image.setImageResource(R.drawable.ajourne);

        }
        else if (moyenneGeneraleDouble > 0){
            remarqueView.setText(IRON);
            remarqueView.setTextColor(Color.RED);
            image.setImageResource(R.drawable.ajourne);

        }
        else
            remarqueView.setText(NO_REMARQUE);
    }

    private double calculerMoyenne(ArrayList<MarksView> moduleList) {
        double moyenneGenerale = 0;
        int coefficient = 0;
        double emd,td,tp =0;
        for(MarksView note : moduleList) {
           coefficient += note.getCoefficient();



           moyenneGenerale += note.getMoyenne() * note.getCoefficient();
        }
        return moyenneGenerale / coefficient;
    }

    private String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(number);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        // Retour arriere
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}