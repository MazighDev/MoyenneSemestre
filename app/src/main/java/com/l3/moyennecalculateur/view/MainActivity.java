package com.l3.moyennecalculateur.view;

import androidx.annotation.CheckResult;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.l3.moyennecalculateur.control.Module;
import com.l3.moyennecalculateur.R;
import com.l3.moyennecalculateur.model.ModuleDAO;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ModuleDAO moduleDAO = new ModuleDAO(this);
    private EditText txtModuleName, txtModuleCoeff, txtModuleEmd, txtModuleTd, txtModuleTp;
    private Button btnMoyenne, btnMoyenneGenerale;
    private TextView txtMoyenne;
    private double mMoyenne;
    private CheckBox rdbt1,rdbt2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtModuleName = findViewById(R.id.activity_main_module_name_txt);
        txtModuleCoeff = findViewById(R.id.activity_main_module_coeff_txt);
        txtModuleEmd = findViewById(R.id.activity_main_module_emd_txt);
        txtModuleTd = findViewById(R.id.activity_main_module_td_txt);
        txtModuleTp = findViewById(R.id.activity_main_module_tp_txt);
        txtMoyenne = findViewById(R.id.activity_main_moyenne_txt);
        btnMoyenne = findViewById(R.id.activity_main_moyenne_btn);
        btnMoyenneGenerale = findViewById(R.id.activity_main_moyenne_generale_btn);
        rdbt1 =(CheckBox) findViewById(R.id.rdbt1);
        rdbt2 =(CheckBox) findViewById(R.id.rdbt2);


        rdbt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if(checked){
                    txtModuleTd.setText("0000");
                    txtModuleTd.setEnabled(false);

                }
                else{
                    txtModuleTd.setText("");
                    txtModuleTd.setEnabled(true);
                }
            }
        });

        rdbt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if(checked){
                    txtModuleTp.setText("0000");
                    txtModuleTp.setEnabled(false);

                }
                else{
                    txtModuleTp.setText("");
                    txtModuleTp.setEnabled(true);
                }
            }
        });


        btnMoyenneGeneraleEnable();
        this.configureToolbar();



        btnMoyenne.setOnClickListener(v -> {

            if (inputIsOk()){
                Module module = new Module(txtModuleName.getText().toString(),
                        toInteger(txtModuleCoeff),
                        toDouble(txtModuleEmd),
                        toDouble(txtModuleTd),
                        toDouble(txtModuleTp));
                int sizeOfTable = moduleDAO.getLength();

                    final CheckBox checkBox = (CheckBox) findViewById(R.id.rdbt1);
                    if (checkBox.isChecked()) {
                        //double moy = (module.getEmd() * 2 + module.getTp()) / 3;
                        double moy =(module.getEmd()*0.6 + module.getTp()*0.4);
                        txtMoyenne.setText(formatDouble(moy));
                    }

                    final CheckBox checkBox1 = (CheckBox) findViewById(R.id.rdbt2);
                    if (checkBox1.isChecked()) {
                       // txtMoyenne.setText(formatDouble((module.getEmd() * 2 + module.getTd()) / 3));
                        txtMoyenne.setText(formatDouble((module.getEmd()*0.6 + module.getTd()*0.4)));
                    }
                    if (checkBox.isChecked() && checkBox1.isChecked()) {
                        txtMoyenne.setText(formatDouble(module.getEmd()));
                    }
                    if (!checkBox.isChecked() && !checkBox1.isChecked()) {
                        txtMoyenne.setText(formatDouble(module.getMoyenne()));
                    }



                moduleDAO.addModule(module);
                btnMoyenneGeneraleEnable();
                if(sizeOfTable < moduleDAO.getLength()) {
                    showToast("Module N°" + moduleDAO.getLength() + " ajouté avec succée.");
                    txtModuleName.setText("");
                }
                else
                    showToast("Problème inatendu, module non ajouté.");
            } else
                txtMoyenne.setText("");
        });

        btnMoyenneGenerale.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoyenneGenerale.class);
            startActivity(intent);
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_text_clear:
                this.clearInput();
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

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moduleDAO.clearDb();
                    showToast(nbModule + " module supprimé avec succée");
                    btnMoyenneGeneraleEnable();
                    clearInput();
                }
            });

            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showToast("Opération annulé");
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else
            showToast("Aucun module à supprimer, la DB est vide.");
    }

    private void clearInput() {
        txtModuleName.setText("");
        txtModuleCoeff.setText("");
        txtModuleEmd.setText("");
        txtModuleTd.setText("");
        txtModuleTp.setText("");
        txtMoyenne.setText(".....");
        this.showToast("Champs de saisi vidé avec sucée!");
    }


    private void btnMoyenneGeneraleEnable() {
        btnMoyenneGenerale.setEnabled(moduleDAO.getLength() > 0);
    }

    private boolean inputIsOk() {
        if(!txtModuleName.getText().toString().isEmpty() &&
                !txtModuleCoeff.getText().toString().isEmpty() &&
                !txtModuleEmd.getText().toString().isEmpty()  &&
                    !txtModuleTd.getText().toString().isEmpty() &&
                !txtModuleTp.getText().toString().isEmpty())
          {
            if(toDouble(txtModuleCoeff)>0 && toDouble(txtModuleCoeff) <= 20){
                if(toDouble(txtModuleEmd)>=0 && toDouble(txtModuleEmd)<=20){
                    if(toDouble(txtModuleTd)>=0 && toDouble(txtModuleTd)<=20){
                        if(toDouble(txtModuleTp)>=0 && toDouble(txtModuleTp)<=20){
                            return true;
                        } else if(!rdbt2.isChecked())
                            showToast("Le TP ne peut pas être négatif ou supperieur à 20");
                    } else if(!rdbt1.isChecked())
                        showToast("Le TD ne peut pas être négatif ou supperieur à 20");
                } else
                    showToast("L'EMD ne peut pas être négatif ou supperieur à 20");
            } else
                showToast("Le coefficient ne peut pas être négatif ou supperieur à 20");
        } else if(!rdbt1.isChecked() || !rdbt2.isChecked())
            showToast("Tous les champs doivent être saisis");

        return false;
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private double toDouble(EditText txtEditText) {
        if(canBeDouble(txtEditText)) {
            return Double.parseDouble(txtEditText.getText().toString());
        }
        return -1;
    }

    private int toInteger(EditText txtEditText) {
        if(canBeInteger(txtEditText)) {
            return Integer.parseInt(txtEditText.getText().toString());
        }
        return -1;
    }

    private boolean canBeDouble(EditText txtEditText) {
        try {
            double d = Double.parseDouble(txtEditText.getText().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean canBeInteger(EditText txtEditText) {
        try {
            int i = Integer.parseInt(txtEditText.getText().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(number);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public double getMoyenne(double emd, double td, double tp){
        return 00.00;
    }
}