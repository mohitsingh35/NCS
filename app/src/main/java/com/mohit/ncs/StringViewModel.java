package com.mohit.ncs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StringViewModel extends ViewModel {


    private MutableLiveData<List<StringModel>> filteredStrings;
    private List<StringModel> allStrings;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    public void init(Context context) {
        if (filteredStrings != null) {
            return;
        }
        filteredStrings = new MutableLiveData<>();
        allStrings = new ArrayList<>();
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, new ArrayList<String>());
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("MySet", new HashSet<String>());
        for (String str : set) {
            String[] items = str.split(",");
            for (String item : items) {
                allStrings.add(new StringModel(item.trim()));
            }
        }
        filteredStrings.setValue(allStrings);
        adapter.addAll(getStringList(allStrings));
    }
    private List<String> getStringList(List<StringModel> stringModels) {
        List<String> stringList = new ArrayList<>();
        for (StringModel model : stringModels) {
            stringList.add(model.getStr());
        }
        return stringList;
    }

    public MutableLiveData<List<StringModel>> getFilteredStrings() {
        return filteredStrings;
    }

    public void filterStrings(String searchInput) {
        List<StringModel> filteredList = new ArrayList<>();
        if (searchInput.isEmpty()) {
            filteredList.addAll(allStrings);
        } else {
            for (StringModel str : allStrings) {
                if (str.getStr().toLowerCase(Locale.getDefault()).contains(searchInput)) {
                    filteredList.add(str);
                }
                if (filteredList.size() >= 5) {
                    break;
                }
            }
        }
        filteredStrings.setValue(filteredList);
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public void onItemClick(int position, Context context) {
        String selectedItem = filteredStrings.getValue().get(position).getStr();
        Intent intent = new Intent(context, ThirdActivity.class);
        intent.putExtra("selectedItem", selectedItem);
        context.startActivity(intent);
    }

    public TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterStrings(s.toString().toLowerCase(Locale.getDefault()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    public void saveData() {
        Set<String> set = new HashSet<>();
        for (StringModel model : allStrings) {
            set.add(model.getStr());
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("MySet", set);
        editor.apply();
    }



}

