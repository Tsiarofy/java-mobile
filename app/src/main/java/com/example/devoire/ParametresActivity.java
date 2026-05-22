package com.example.devoire;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ParametresActivity extends AppCompatActivity {

    private Button btnThemeClair;
    private Button btnThemeSombre;
    private Button btnRetour;
    private SharedPreferences preferencesApp;
    private LinearLayout container;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        preferencesApp = getSharedPreferences("app_prefs", MODE_PRIVATE);

        container = findViewById(R.id.container_parametres);
        btnThemeClair = findViewById(R.id.btn_theme_clair);
        btnThemeSombre = findViewById(R.id.btn_theme_sombre);
        btnRetour = findViewById(R.id.btn_retour_parametres);

        boolean estThemeSombre = preferencesApp.getBoolean("theme_sombre", false);
        appliquerTheme(estThemeSombre);

        btnThemeClair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerTheme(false);
            }
        });

        btnThemeSombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerTheme(true);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changerTheme(boolean estSombre) {
        SharedPreferences.Editor editor = preferencesApp.edit();
        editor.putBoolean("theme_sombre", estSombre);
        editor.apply();

        appliquerTheme(estSombre);
    }

    private void appliquerTheme(boolean estSombre) {
        if (estSombre) {
            container.setBackgroundColor(getResources().getColor(android.R.color.black));
            btnThemeClair.setTextColor(getResources().getColor(android.R.color.white));
            btnThemeSombre.setTextColor(getResources().getColor(android.R.color.white));
            btnRetour.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            container.setBackgroundColor(getResources().getColor(android.R.color.white));
            btnThemeClair.setTextColor(getResources().getColor(android.R.color.black));
            btnThemeSombre.setTextColor(getResources().getColor(android.R.color.black));
            btnRetour.setTextColor(getResources().getColor(android.R.color.black));
        }
    }
}
