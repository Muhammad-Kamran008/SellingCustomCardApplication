package com.example.sellingcustomcardapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Locale;

public class SampleCardActivityGraduation extends AppCompatActivity {

    private static final String PREF_LANGUAGE = "LanguagePreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = preferences.getString(PREF_LANGUAGE, "en"); // Default language is English
        updateLocale(lang);
        setContentView(R.layout.activity_sample_card_graduation);
    }
    private void updateLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}