package com.example.devoire;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.devoire.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private SharedPreferences preferencesApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesApp = getSharedPreferences("app_prefs", MODE_PRIVATE);

        // Charger le thème sauvegardé
        boolean estThemeSombre = preferencesApp.getBoolean("theme_sombre", false);
        appliquerTheme(estThemeSombre);

        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        // Charger l'email sauvegardé s'il existe
        String emailSauvegarde = preferencesApp.getString("email_utilisateur", "");
        if (!emailSauvegarde.isEmpty()) {
            etEmail.setText(emailSauvegarde);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String motDePasse = etPassword.getText().toString();

                if (email.equals("tsiarofy@gmail.com") && motDePasse.equals("tsiarofy")) {
                    // Capture de l'heure actuelle
                    Date dateActuelle = new Date();
                    SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                    String heureConnexion = formatHeure.format(dateActuelle);
                    String dateConnexion = formatDate.format(dateActuelle);

                    // Sauvegarder la dernière connexion
                    SharedPreferences.Editor editor = preferencesApp.edit();
                    editor.putString("heure_connexion", heureConnexion);
                    editor.putString("date_connexion", dateConnexion);
                    editor.putString("email_utilisateur", email);

                    // Sauvegarder la première connexion si elle n'existe pas
                    if (!preferencesApp.contains("date_premiere_connexion")) {
                        editor.putString("date_premiere_connexion", dateConnexion);
                        editor.putString("heure_premiere_connexion", heureConnexion);
                    }

                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, AcceuilActivity.class);
                    intent.putExtra("heure_connexion", heureConnexion);
                    intent.putExtra("date_connexion", dateConnexion);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void appliquerTheme(boolean estSombre) {
        if (estSombre) {
            findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    
}
}
