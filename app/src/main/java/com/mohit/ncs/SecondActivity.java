package com.mohit.ncs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private StringViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        viewModel = new ViewModelProvider(this).get(StringViewModel.class);
        viewModel.init(this);
        listView.setAdapter(viewModel.getAdapter());
        editText.addTextChangedListener(viewModel.getTextWatcher());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.onItemClick(position, this);
        });
        viewModel.getFilteredStrings().observe(this, new Observer<List<StringModel>>() {
            @Override
            public void onChanged(List<StringModel> stringModels) {
                viewModel.getAdapter().clear();
                viewModel.getAdapter().addAll(getStringList(stringModels));
                viewModel.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private String[] getStringList(List<StringModel> stringModels) {
        String[] stringList = new String[stringModels.size()];
        for (int i = 0; i < stringModels.size(); i++) {
            stringList[i] = stringModels.get(i).getStr();
        }
        return stringList;
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        viewModel.saveData();
//    }

}