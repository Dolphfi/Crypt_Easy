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

public class Decrypt_activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private EditText editTextNoDecrypt;
    private EditText editTextDecryptKey;
    private TextView textViewResultDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        // Initialiser les vues
        editTextNoDecrypt = findViewById(R.id.no_Decrypt);
        editTextDecryptKey = findViewById(R.id.decrypt_key);
        textViewResultDecrypt = findViewById(R.id.result_Decrypt);

        // Ajouter un TextWatcher pour le champ de saisie de la clé de décryptage
        editTextDecryptKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ne rien faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Mettre à jour le texte décrypté lorsque la clé de décryptage change
                updateDecryptedText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ne rien faire après que le texte a changé
            }
        });

        // Ajouter un TextWatcher pour le champ de saisie du texte à décrypter
        editTextNoDecrypt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ne rien faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Mettre à jour le texte décrypté automatiquement lorsqu'il change
                updateDecryptedText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ne rien faire après que le texte a changé
            }
        });

        // start navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuDecrypt);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuDecrypt:
                        return true;
                    case R.id.menuEncrypt:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        // end navigation
    }

    // Méthode pour mettre à jour le texte décrypté en fonction de la clé de décryptage
    private void updateDecryptedText() {
        String encryptedText = editTextNoDecrypt.getText().toString();
        String decryptKeyText = editTextDecryptKey.getText().toString();

        // Vérifier si la clé de décryptage n'est pas vide
        if (!decryptKeyText.isEmpty()) {
            int decryptKey = Integer.parseInt(decryptKeyText);
            String decryptedText = decrypt(encryptedText, decryptKey);
            textViewResultDecrypt.setText(decryptedText);
        } else if(encryptedText.isEmpty()){
            textViewResultDecrypt.setText("");
        }
        else {
            // Si la clé de décryptage est vide, afficher un texte par défaut
            textViewResultDecrypt.setText(getString(R.string.choose_decrypt_key));
        }
    }

    // Méthode pour le processus de décryptage
    private String decrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isLetter(currentChar)) {
                char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
                char decryptedChar = (char) (((currentChar - base - shift + 26) % 26) + base);
                result.append(decryptedChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
