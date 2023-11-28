package com.example.appnanrj;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

class DatabaseThread extends Thread {

    private DatabaseHandler db;
    private Handler handlerDBThread;

    public DatabaseThread(DatabaseHandler db) {
        this.db = db;
    }

    @Override
    public void run() {
        Looper.prepare();

        handlerDBThread = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case 1:
                        User user = (User) msg.obj;
                        registrUser(user);
                        break;
                    case 2:
                        MyData myData = (MyData) msg.obj;
                        User user1 = myData.getObj11();
                        Context context1 = myData.getObj22();
                        passwChange(user1, context1);
                        break;
                    case 3:
                        String userLogin = (String) msg.obj;
                        deleteUser(userLogin);
                        break;
                }
            }
        };

        Looper.loop();
    }

    public Handler getHandler() {
        return handlerDBThread;
    }


    public void logUsers(Handler handlerHelA) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                List<User> userList = db.getAllUsers();
                message.what = 1;
                message.obj = userList;
                handlerHelA.sendMessage(message);
            }
        });
        thread.start();
    }

    public void registrUser(User user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.addUser(user);
            }
        });
        thread.start();
    }

    public void passwChange(User user, Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handlerDBThread.post(new Runnable() {
                    @Override
                    public void run() {
                        db.PassChangeClick(user, context);
                    }
                });
            }
        });
        thread.start();
    }

    public void deleteUser(String loginToDelete) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.deleteUserFromDB(loginToDelete);
            }
        });
        thread.start();
    }

    public void authorization(String login, String pass, Handler handlerHelA) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                boolean userDB = db.getUser(login, pass);
                message.obj = userDB;
                handlerHelA.sendMessage(message);
            }
        });
        thread.start();
    }
}
