package com.example.devoire;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class AcceuilActivity extends AppCompatActivity {

    private TextView tvHeureConnexion;
    private TextView tvDateConnexion;
    private Button btnProfil;
    private Button btnParametres;
    private Button btnDeconnexion;
    private SharedPreferences preferencesApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        preferencesApp = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean estThemeSombre = preferencesApp.getBoolean("theme_sombre", false);
        appliquerTheme(estThemeSombre);

        tvHeureConnexion = findViewById(R.id.tv_heure_connexion);
        tvDateConnexion = findViewById(R.id.tv_date_connexion);
        btnProfil = findViewById(R.id.btn_profil);
        btnParametres = findViewById(R.id.btn_parametres);
        btnDeconnexion = findViewById(R.id.btn_deconnexion);

        String heureConnexion = getIntent().getStringExtra("heure_connexion");
        String dateConnexion = getIntent().getStringExtra("date_connexion");

        tvHeureConnexion.setText("Connecté à " + heureConnexion);
        tvDateConnexion.setText("Date : " + dateConnexion);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceuilActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        btnParametres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceuilActivity.this, ParametresActivity.class);
                startActivity(intent);
            }
        });

        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherDialogDeconnexion();
            }
        });
    }

    private void afficherDialogDeconnexion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment vous déconnecter ?")
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = preferencesApp.edit();
                        editor.remove("mot_de_passe");
                        editor.apply();

                        Intent intent = new Intent(AcceuilActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void appliquerTheme(boolean estSombre) {
        LinearLayout container = findViewById(R.id.container_acceuil);
        if (estSombre) {
            container.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            container.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }
}
