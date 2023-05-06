package com.mohit.ncs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveButton;
    private Button nextButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        nextButton = findViewById(R.id.nextButton);
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        nextButton.setEnabled(false);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();
                if(data.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Something to Continue",Toast.LENGTH_SHORT).show();
                }else {
                    Set<String> set = sharedPreferences.getStringSet("MySet", new HashSet<String>());
                    set.add(data);
                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().putStringSet("MySet", set).apply();
                    editText.getText().clear();
                    nextButton.setEnabled(true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Set<String> set = sharedPreferences.getStringSet("MySet", new HashSet<String>());
                intent.putExtra("data", new ArrayList<String>(set));
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        nextButton.setEnabled(false);
    }


}