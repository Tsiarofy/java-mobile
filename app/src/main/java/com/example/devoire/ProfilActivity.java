package com.example.devoire;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    private TextView tvEmail;
    private TextView tvDatePremierConnexion;
    private TextView tvDernierConnexion;
    private TextView tvDerniereVisiteProfil;
    private TextView tvNombreVisites;
    private EditText etNouveauEmail;
    private Button btnChangerEmail;
    private Button btnRetour;
    private Button btnDeconnexion;
    private SharedPreferences preferencesApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        preferencesApp = getSharedPreferences("app_prefs", MODE_PRIVATE);

        boolean estThemeSombre = preferencesApp.getBoolean("theme_sombre", false);
        appliquerTheme(estThemeSombre);

        tvEmail = findViewById(R.id.tv_email_profil);
        tvDatePremierConnexion = findViewById(R.id.tv_date_premier_connexion);
        tvDernierConnexion = findViewById(R.id.tv_dernier_connexion);
        tvDerniereVisiteProfil = findViewById(R.id.tv_derniere_visite_profil);
        tvNombreVisites = findViewById(R.id.tv_nombre_visites);
        etNouveauEmail = findViewById(R.id.et_nouveau_email);
        btnChangerEmail = findViewById(R.id.btn_changer_email);
        btnRetour = findViewById(R.id.btn_retour);
        btnDeconnexion = findViewById(R.id.btn_deconnexion_profil);

        afficherInfosProfil();

        mettreAJourVisiteProfil();

        btnChangerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerEmail();
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void afficherInfosProfil() {
        String email = preferencesApp.getString("email_utilisateur", "Non défini");
        String datePremier = preferencesApp.getString("date_premiere_connexion", "Non défini");
        String heurePremier = preferencesApp.getString("heure_premiere_connexion", "");
        String dateLastSession = preferencesApp.getString("date_connexion", "Non défini");
        String heureLastSession = preferencesApp.getString("heure_connexion", "");

        tvEmail.setText("Email : " + email);
        tvDatePremierConnexion.setText("Première connexion : " + datePremier + " à " + heurePremier);
        tvDernierConnexion.setText("Dernière connexion : " + dateLastSession + " à " + heureLastSession);
    }

    private void mettreAJourVisiteProfil() {
        Date maintenant = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String derniereVisite = format.format(maintenant);

        int nombreVisites = preferencesApp.getInt("nombre_visites_profil", 0);
        nombreVisites++;

        SharedPreferences.Editor editor = preferencesApp.edit();
        editor.putString("derniere_visite_profil", derniereVisite);
        editor.putInt("nombre_visites_profil", nombreVisites);
        editor.apply();

        tvDerniereVisiteProfil.setText("Dernière visite du profil : " + derniereVisite);
        tvNombreVisites.setText("Nombre de visites du profil : " + nombreVisites);
    }

    private void changerEmail() {
        String nouveauEmail = etNouveauEmail.getText().toString().trim();

        if (nouveauEmail.isEmpty()) {
            Toast.makeText(this, "Le champ ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nouveauEmail.contains("@") || !nouveauEmail.contains(".")) {
            Toast.makeText(this, "L'email doit contenir @ et .", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = preferencesApp.edit();
        editor.putString("email_utilisateur", nouveauEmail);
        editor.apply();

        etNouveauEmail.setText("");
        tvEmail.setText("Email : " + nouveauEmail);
        Toast.makeText(this, "Email mis à jour", Toast.LENGTH_SHORT).show();
    }

    private void appliquerTheme(boolean estSombre) {
        LinearLayout container = findViewById(R.id.container_profil);
        if (estSombre) {
            container.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            container.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }
}
