
package com.example.sellingcustomcardapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sellingcustomcardapplication.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private static final String PREF_LANGUAGE = "LanguagePreference";

    private EditText editTextName;
    private Spinner spinnerCardSize;
    private CheckBox checkBoxConfirmOrder;
    private Button buttonDisplayPrice;
    private TextView textViewFinalMessage;

    // Sound effect
    private MediaPlayer mediaPlayer;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        spinnerCardSize = findViewById(R.id.spinnerCardSize);
        checkBoxConfirmOrder = findViewById(R.id.checkBoxConfirmOrder);
        buttonDisplayPrice = findViewById(R.id.buttonDisplayPrice);
        textViewFinalMessage = findViewById(R.id.textViewFinalMessage);
        RadioButton radioButtonOccasion1 = findViewById(R.id.radioButtonOccasion1);
        RadioButton radioButtonOccasion2 = findViewById(R.id.radioButtonOccasion2);
        RadioButton radioButtonOccasion3 = findViewById(R.id.radioButtonOccasion3);
        RadioButton radioButtonOccasion4 = findViewById(R.id.radioButtonOccasion4);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        // Set onClickListener for the button
        buttonDisplayPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play sound effect
                playSound();

                // Extract selected data
                String occasionTitle = "";
                int selectedRadioButtonId = getSelectedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    occasionTitle = ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
                }
                String personName = editTextName.getText().toString();
                String cardSize = spinnerCardSize.getSelectedItem().toString();

                // Display final message
                String finalMessage = getString(R.string.final_message, occasionTitle, personName, cardSize);
                textViewFinalMessage.setText(finalMessage);
                textViewFinalMessage.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Move to the respective activity based on the selected radio button
                        int radioButtonId = getSelectedRadioButtonId();
                        if (radioButtonId == R.id.radioButtonOccasion1) {
                            startActivity(new Intent(HomeActivity.this, SampleCardActivityBirthday.class));
                        } else if (radioButtonId == R.id.radioButtonOccasion2) {
                            startActivity(new Intent(HomeActivity.this, SampleCardActivityWedding.class));
                        } else if (radioButtonId == R.id.radioButtonOccasion3) {
                            startActivity(new Intent(HomeActivity.this, SampleCardActivityGraduation.class));
                        } else if (radioButtonId == R.id.radioButtonOccasion4) {
                            startActivity(new Intent(HomeActivity.this, GenderSelectionActivity.class));
                        }
                    }
                }, 3000); // Delay in milliseconds (e.g., 3000 for 3 seconds)
            }
        });

        // Navigation menu item click listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int id = item.getItemId();
                if (id == R.id.nav_language_english) {
                    setLocale("en"); // Set language to English
                    return true;
                } else if (id == R.id.nav_language_arabic) {
                    setLocale("ar"); // Set language to Arabic
                    return true;
                }
                // Close the navigation drawer when an item is selected
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });




    }


    private void setLocale(String lang) {
        // Save the selected language preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(PREF_LANGUAGE, lang).apply();

        // Apply the language change to the current activity
        updateLocale(lang);

        // Restart all activities to apply the language change
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void updateLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound_click); // Replace R.raw.click_sound with your sound resource
        }
        mediaPlayer.start();
    }

    // Release the media player when activity is destroyed
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    // Function to get the ID of the selected radio button
    private int getSelectedRadioButtonId() {
        RadioButton radioButtonOccasion1 = findViewById(R.id.radioButtonOccasion1);
        RadioButton radioButtonOccasion2 = findViewById(R.id.radioButtonOccasion2);
        RadioButton radioButtonOccasion3 = findViewById(R.id.radioButtonOccasion3);
        RadioButton radioButtonOccasion4 = findViewById(R.id.radioButtonOccasion4);

        if (radioButtonOccasion1.isChecked()) {
            return radioButtonOccasion1.getId();
        } else if (radioButtonOccasion2.isChecked()) {
            return radioButtonOccasion2.getId();
        } else if (radioButtonOccasion3.isChecked()) {
            return radioButtonOccasion3.getId();
        } else if (radioButtonOccasion4.isChecked()) {
            return radioButtonOccasion4.getId();
        } else {
            Toast.makeText(this, "Please select an occasion", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reset all RadioButtons to unchecked state
        RadioButton radioButtonOccasion1 = findViewById(R.id.radioButtonOccasion1);
        RadioButton radioButtonOccasion2 = findViewById(R.id.radioButtonOccasion2);
        RadioButton radioButtonOccasion3 = findViewById(R.id.radioButtonOccasion3);
        RadioButton radioButtonOccasion4 = findViewById(R.id.radioButtonOccasion4);

        radioButtonOccasion1.setChecked(false);
        radioButtonOccasion2.setChecked(false);
        radioButtonOccasion3.setChecked(false);
        radioButtonOccasion4.setChecked(false);
    }

}
