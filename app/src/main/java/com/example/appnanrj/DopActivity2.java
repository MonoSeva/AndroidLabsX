package com.example.appnanrj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DopActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dop2);

        TextView textView5 = findViewById(R.id.textView5);
        Button btn5 = findViewById(R.id.btn5);
        Intent resIntent = getIntent();
        Name name = (Name) resIntent.getSerializableExtra("name");

        if (name != null) {
            textView5.setText(name.getName());
        }

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToActivity4 = new Intent(DopActivity2.this, DopActivity.class);
                startActivity(intentToActivity4);
            }
        });
    }
}
