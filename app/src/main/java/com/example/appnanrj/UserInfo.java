package com.example.appnanrj;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button btn12 = findViewById(R.id.btn12);
        Button btn13 = findViewById(R.id.btn13);

        TextView textViewUser = findViewById(R.id.textViewUser);
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        textViewUser.setText("login is " + username + " password is " + password);

        btn12.setOnClickListener(view -> {
            Intent intentToActivity3 = new Intent(UserInfo.this, MainActivity.class);
            startActivity(intentToActivity3);
        });

        btn13.setOnClickListener(view -> {
            Intent intentToActivity3 = new Intent(UserInfo.this, ListOfData.class);
            startActivity(intentToActivity3);
        });
    }
}
