package com.example.crypteasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private EditText editTextNoEncrypt;
    private EditText editTextEncryptKey;
    private TextView textViewResultEncrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        // Initialiser les vues
        editTextNoEncrypt = findViewById(R.id.no_encrypt);
        editTextEncryptKey = findViewById(R.id.encrypt_key);
        textViewResultEncrypt = findViewById(R.id.result_encrypt);

        // Ajouter un TextWatcher pour le champ de saisie de la clé de cryptage
        editTextEncryptKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ne rien faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Mettre à jour le texte chiffré lorsque la clé de cryptage change
                updateEncryptedText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ne rien faire après que le texte a changé
            }
        });

        // Ajouter un TextWatcher pour le champ de saisie non crypté
        editTextNoEncrypt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ne rien faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Mettre à jour le texte chiffré automatiquement lorsqu'il change
                updateEncryptedText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ne rien faire après que le texte a changé
            }
        });

        // start button Team
        Button btn = findViewById(R.id.btn_team);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teamActivity = new Intent(MainActivity.this, Team_activity.class);
                startActivity(teamActivity);
            }
        });
        // end button Team

        // start button Partager
        Button btnShare = findViewById(R.id.share_result_encrypt);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupérer le texte crypté à partir du TextView
                String encryptedText = textViewResultEncrypt.getText().toString();

                // Créer une intention pour partager le texte crypté
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, encryptedText);

                // Afficher la boîte de dialogue pour choisir l'application de partage
                startActivity(Intent.createChooser(shareIntent, "Partager le texte crypté via :"));
            }
        });
        // end button Partager

        // start navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuEncrypt);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuDecrypt:
                        startActivity(new Intent(getApplicationContext(), Decrypt_activity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuEncrypt:
                        return true;
                }
                return false;
            }
        });
        // end navigation
    }

    // Méthode pour mettre à jour le texte chiffré en fonction de la clé de cryptage
    private void updateEncryptedText() {
        String nonEncryptedText = editTextNoEncrypt.getText().toString();
        String encryptKeyText = editTextEncryptKey.getText().toString();

        // Vérifier si la clé de cryptage n'est pas vide
        if (!encryptKeyText.isEmpty()) {
            int encryptKey = Integer.parseInt(encryptKeyText);
            String encryptedText = encrypt(nonEncryptedText, encryptKey);
            textViewResultEncrypt.setText(encryptedText);
        } else if(nonEncryptedText.isEmpty()){
            textViewResultEncrypt.setText("");
        }
        else {
            // Si la clé de cryptage est vide, afficher un texte par défaut
            textViewResultEncrypt.setText(getString(R.string.choose_encrypt_key));
        }
    }

    // Fonction de chiffrement de Jules César
    private String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
                char encryptedChar = (char) (((currentChar - base + shift) % 26) + base);
                result.append(encryptedChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
