package com.example.appnanrj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Clicker extends AppCompatActivity {
    private int count1 = 0;
    private int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        TextView text = findViewById(R.id.textView2);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setText("Кнопка 1 нажата");
                count1++;
                text.setText("Кнопка 1 нажата " + count1 + " раз");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setText("Кнопка 2 нажата");
                count2++;
                text.setText("Кнопка 2 нажата " + count2 + " раз");
            }
        });

    }
}
