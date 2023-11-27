package com.example.appnanrj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelloActivity1 extends AppCompatActivity {
    private static final String TAG = HelloActivity1.class.getSimpleName();

    private DatabaseHandler db = new DatabaseHandler(this, null);
    Context context = this;
    private RecyclerView rvUsers;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact1);

        DatabaseHandler db = new DatabaseHandler(this, null);

        EditText userLogin = findViewById(R.id.user_login);
        EditText userId = findViewById(R.id.user_id);
        EditText userPass = findViewById(R.id.user_pass);
        Button button = findViewById(R.id.button_reg);
        TextView linkToAuth = findViewById(R.id.link_to_auth);

        Button btnLoad = findViewById(R.id.btnLoad);

        linkToAuth.setOnClickListener(view -> {
            Intent intent = new Intent(HelloActivity1.this, Authorization.class);
            startActivity(intent);
        });

        button.setOnClickListener(view -> {
            String login = userLogin.getText().toString().trim();
            String id = userId.getText().toString().trim();
            String pass = userPass.getText().toString().trim();

            if (login.isEmpty() | id.isEmpty() | pass.isEmpty()) {
                Toast.makeText(HelloActivity1.this, "Не все поля заполнены", Toast.LENGTH_LONG).show();
            } else {
                User user = new User(Integer.parseInt(id), login, pass);
                db.addUser(user);
                Toast.makeText(HelloActivity1.this, "Пользователь " + login + " добавлен", Toast.LENGTH_LONG).show();
            }
        });

        btnLoad.setOnClickListener(view -> {
            List<User> users = db.getAllUsers();
            StringBuilder usersLog = new StringBuilder();
            for (User user : users) {
                usersLog.append(user.toString()).append(",\n");
            }
            Log.d(TAG, "Users:\n" + usersLog);
            userAdapter.setData(users);
        });

        rvUsers = findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        userAdapter = new UserAdapter();
        rvUsers.setAdapter(userAdapter);

        userAdapter.setListener(new UserAdapter.UserClickListener() {
            @Override
            public void onItemClick(User user) {
                Toast.makeText(HelloActivity1.this, "onItemClick() user=" + user.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuDeleteClick(User user) {
                Toast.makeText(HelloActivity1.this, "onMenuDeleteClick() user=" + user.toString(), Toast.LENGTH_SHORT).show();

                db.deleteUserFromDB(user.getLogin());

                List<User> users = db.getAllUsers();
                StringBuilder usersLog = new StringBuilder();
                for (User u : users) {
                    usersLog.append(u.toString()).append(",\n");
                }
                Log.d(TAG, "Users:\n" + usersLog);
                userAdapter.setData(users);
            }

            @Override
            public void onMenuPassChangeClick(User user) {
                db.PassChangeClick(user, context);
            }
        });
    }
}
