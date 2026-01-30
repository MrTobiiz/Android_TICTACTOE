package com.example.carogame.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.R;
import com.example.carogame.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private Switch switchBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchBot = findViewById(R.id.switchBot);

        setupButton(R.id.btn3x3, 3);
        setupButton(R.id.btn6x6, 6);
        setupButton(R.id.btn9x9, 9);
        setupButton(R.id.btn10x10, 10);
    }

    private void setupButton(int buttonId, int boardSize) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(Constants.KEY_BOARD_SIZE, boardSize);
            intent.putExtra(Constants.KEY_IS_BOT, switchBot.isChecked());
            startActivity(intent);
        });
    }
}
