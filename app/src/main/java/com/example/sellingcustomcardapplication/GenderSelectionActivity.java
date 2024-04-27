package com.example.sellingcustomcardapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GenderSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_selection);

        ListView listView = findViewById(R.id.listViewGenderOptions);
        String[] options = {getString(R.string.card_for_her), getString(R.string.card_for_him)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];
                if (selectedOption.equals("Card for her")) {
                    // Navigate to SampleCardActivity for her
                    Intent intent = new Intent(GenderSelectionActivity.this, SampleCardActivityGirl.class);
                    intent.putExtra("gender", "her");
                    startActivity(intent);
                } else if (selectedOption.equals("Card for him")) {
                    // Navigate to SampleCardActivity for him
                    Intent intent = new Intent(GenderSelectionActivity.this, SampleCardActivityBoy.class);
                    intent.putExtra("gender", "him");
                    startActivity(intent);
                }
            }
        });
    }
}

