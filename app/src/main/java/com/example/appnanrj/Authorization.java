
package com.example.appnanrj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Authorization extends AppCompatActivity {

    private Handler handler;
    private DatabaseHandler db;
    private EditText userLogin;
    private EditText userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        userLogin = findViewById(R.id.user_login_auth);
        userPass = findViewById(R.id.user_pass_auth);
        Button button = findViewById(R.id.button_auth);
        TextView linkToReg = findViewById(R.id.link_to_reg);

        db = new DatabaseHandler(this, null);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String login = userLogin.getText().toString().trim();
                String pass = userPass.getText().toString().trim();

                if (login.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Authorization.this, "Не все поля заполнены", Toast.LENGTH_LONG).show();
                }

                boolean data = (Boolean) msg.obj;
                if (data) {
                    Toast.makeText(Authorization.this, "Пользователь " + login + " авторизован", Toast.LENGTH_LONG).show();
                    userLogin.getText().clear();
                    userPass.getText().clear();

                    Intent intent2 = new Intent(Authorization.this, ListOfData.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(Authorization.this, "Пользователь " + login + " не авторизован", Toast.LENGTH_LONG).show();
                }
            }
        };

        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authorization.this, HelloActivity1.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = userLogin.getText().toString().trim();
                String pass = userPass.getText().toString().trim();
                DatabaseThread thread = new DatabaseThread(db);
                thread.authorization(login, pass, handler);
                //thread.getHandler().sendMessage(Message.obtain(handler));
            }
        });
    }
}