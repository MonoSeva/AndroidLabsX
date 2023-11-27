package com.example.appnanrj;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dop);

        Button btn = findViewById(R.id.btn);
        Button nextButton3 = findViewById(R.id.nextButton3);
        Button backButton3 = findViewById(R.id.backButton3);
        EditText nameText = findViewById(R.id.nameText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                Name nameS = new Name(name);
                Intent intent = new Intent(DopActivity.this, DopActivity2.class);
                intent.putExtra("name", nameS);
                startActivity(intent);
            }
        });

        nextButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DopActivity.this, DopActivity2.class);
                startActivity(intent);
            }
        });

        backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DopActivity.this, Clicker.class);
                startActivity(intent);
            }
        });
    }
}
