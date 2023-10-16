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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText editTextTextPassword = findViewById(R.id.editTextTextPassword);
        EditText editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        buttonLogin.setOnClickListener(view -> {
            String email = editTextTextEmailAddress.getText().toString();
            String password = editTextTextPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                String savedEmail = sharedPreferences.getString("email", "");
                String savedPassword = sharedPreferences.getString("password", "");

                if (email.equals(savedEmail) && password.equals(savedPassword)) {
                    editor.apply();

                    Intent intent2 = new Intent(MainActivity.this, UserInfo.class);
                    startActivity(intent2);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRegister.setOnClickListener(view -> {
            String email = editTextTextEmailAddress.getText().toString();
            String password = editTextTextPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                Toast.makeText(MainActivity.this, "Вы зарегистрированы", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
