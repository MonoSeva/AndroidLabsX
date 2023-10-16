package com.example.appnanrj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText editTextTextPassword = findViewById(R.id.editTextTextPassword);
        EditText editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);


        buttonLogin.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this, ListOfData.class);
            startActivity(intent2);
        });

        buttonRegister.setOnClickListener(view -> {

        });
    }
}
