package com.example.appnanrj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Authorization extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        EditText userLogin = findViewById(R.id.user_login_auth);
        EditText userPass = findViewById(R.id.user_pass_auth);
        Button button = findViewById(R.id.button_auth);
        TextView linkToReg = findViewById(R.id.link_to_reg);

        linkToReg.setOnClickListener(view -> {
            Intent intent = new Intent(Authorization.this, HelloActivity1.class);
            startActivity(intent);
        });

        button.setOnClickListener(view -> {
            String login = userLogin.getText().toString().trim();
            String pass = userPass.getText().toString().trim();

            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show();
            } else {
                DatabaseHandler db = new DatabaseHandler(this, null);
                boolean isAuth = db.getUser(login, pass);

                if (isAuth) {
                    Toast.makeText(this, "Пользователь " + login + " авторизован", Toast.LENGTH_LONG).show();
                    userLogin.getText().clear();
                    userPass.getText().clear();

                    Intent intent2 = new Intent(Authorization.this, ListOfData.class);
                    startActivity(intent2);

                } else {
                    Toast.makeText(this, "Пользователь " + login + " не авторизован", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
