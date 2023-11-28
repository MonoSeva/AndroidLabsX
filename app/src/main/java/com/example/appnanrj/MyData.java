package com.example.appnanrj;

import android.content.Context;

public class MyData {
    private User obj1;
    private Context obj2;

    public MyData(User obj1, Context obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;

    }

    public User getObj11() {
        return obj1;
    }

    public Context getObj22() {
        return obj2;
    }


}