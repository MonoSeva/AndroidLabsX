package com.example.appnanrj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListOfData extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataAL;
    private ListView list;
    private SparseBooleanArray checkedItems;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Button nextButton1 = findViewById(R.id.nextButton1);
        Button backButton1 = findViewById(R.id.backButton1);
        Button button7 = findViewById(R.id.button7);
        list = findViewById(R.id.dynamic);
        EditText nameText1 = findViewById(R.id.nameText1);
        Button button6 = findViewById(R.id.button6);

        dataAL = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, dataAL);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        button7.setOnClickListener(view -> {
            String data = nameText1.getText().toString().trim();
            dataAL.add(data);
            adapter.notifyDataSetChanged();
            nameText1.getText().clear();
        });

        button6.setOnClickListener(view -> {
            checkedItems = list.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<>();

            for (int i = 0; i < checkedItems.size(); i++) {
                int position = checkedItems.keyAt(i);
                if (checkedItems.valueAt(i)) {
                    selectedItems.add(dataAL.get(position));
                }
            }

            dataAL.removeAll(selectedItems);
            adapter.notifyDataSetChanged();
        });

        nextButton1.setOnClickListener(view -> {
            Intent intent3 = new Intent(ListOfData.this, Clicker.class);
            startActivity(intent3);
        });

        backButton1.setOnClickListener(view -> {
            Intent intent3 = new Intent(ListOfData.this, MainActivity.class);
            startActivity(intent3);
        });

    }

}
