package com.example.appnanrj;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class HelloActivity1 extends AppCompatActivity {

    private static final String TAG = HelloActivity1.class.getSimpleName();

    private RecyclerView rvUsers;
    private DatabaseHandler db;

    private Button btnLogLoad;
    private Handler handlerHelA;
    private Button buttonReg;
    private EditText userLogin;
    private EditText userId;
    private EditText userPass;

    private UserAdapter userAdapter = new UserAdapter();

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact1);

        db = new DatabaseHandler(this, null);
        buttonReg = findViewById(R.id.button_reg);
        userLogin = findViewById(R.id.user_login);
        userId = findViewById(R.id.user_id);
        userPass = findViewById(R.id.user_pass);
        btnLogLoad = findViewById(R.id.btnLogLoad);

        TextView linkToAuth = findViewById(R.id.link_to_auth);

        DatabaseThread databaseThread = new DatabaseThread(db);
        databaseThread.start();

        handlerHelA = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        List<User> users = (List<User>) msg.obj;
                        String usersLog = "";
                        for (User user : users) {
                            usersLog += user.toString() + ",\n";
                        }
                        Log.d(TAG, "Users:\n " + usersLog);
                        userAdapter.setData(users);
                        break;
                }
            }
        };

        linkToAuth.setOnClickListener(view -> {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        });

        buttonReg.setOnClickListener(view -> {

            String login = userLogin.getText().toString().trim();
            String id = userId.getText().toString().trim();
            String pass = userPass.getText().toString().trim();

            if (login.isEmpty() || id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(HelloActivity1.this, "Не все поля заполнены", Toast.LENGTH_LONG).show();
            } else {
                User user = new User(Integer.parseInt(id), login, pass);

                Handler handlerDBThread = databaseThread.getHandler();
                Message message = Message.obtain();
                message.what = 1;
                message.obj = user;
                handlerDBThread.sendMessage(message);

                Toast.makeText(HelloActivity1.this, "Пользователь " + login + " добавлен", Toast.LENGTH_LONG).show();
            }
        });

        btnLogLoad.setOnClickListener(view -> databaseThread.logUsers(handlerHelA));

        rvUsers = findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvUsers.setAdapter(userAdapter);


        userAdapter.setListener(new UserAdapter.UserClickListener() {
            @Override
            public void onItemClick(User user) {
                rvUsers.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                HelloActivity1.this,
                                "onItemClick() user=" + user,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
            }

            @Override
            public void onMenuDeleteClick(User user) {
                Handler handlerDBThread = databaseThread.getHandler();
                Message message = Message.obtain();
                message.what = 3;
                message.obj = user.getLogin();
                handlerDBThread.sendMessage(message);

                Toast.makeText(
                        HelloActivity1.this,
                        "onMenuDeleteClick() user=" + user,
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onMenuPassChangeClick(User user) {

                MyData data = new MyData(user, context);

                Handler handlerDBThread = databaseThread.getHandler();
                Message message = Message.obtain();
                message.what = 2;
                message.obj = data;
                handlerDBThread.sendMessage(message);


            }
        });

    }
}

