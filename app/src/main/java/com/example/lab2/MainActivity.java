package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.lab2.Bai1.Bai1Activity;
import com.example.lab2.Bai2.Bai2Activity;

public class MainActivity extends AppCompatActivity {
    private Button btnB1;
    private Button btnB2;
    private Class hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnB1 = (Button) findViewById(R.id.btn_b1);
        btnB2 = (Button) findViewById(R.id.btn_b2);

        btnB1.setOnClickListener(v -> executeExercise(1));
        btnB2.setOnClickListener(v -> executeExercise(2));
    }

    private void executeExercise(int exerciseNumber) {
        switch (exerciseNumber) {
            case 1:
                hm = Bai1Activity.class;
                break;

            case 2:
                hm = Bai2Activity.class;
                break;

        }

        startActivity(new Intent(MainActivity.this, hm));
    }
}