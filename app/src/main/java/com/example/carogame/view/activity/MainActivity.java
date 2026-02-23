package com.example.carogame.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.databinding.ActivityMainBinding;
import com.example.carogame.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupButtons();
    }

    private void setupButtons() {

        setupButton(binding.btn3x3, 3);
        setupButton(binding.btn6x6, 6);
        setupButton(binding.btn9x9, 9);
        setupButton(binding.btn10x10, 10);
        setupCustomButton();
    }

    private void setupCustomButton(){
        binding.btnCustom.setOnClickListener(v -> {
            String input = binding.edtCustomSize.getText().toString().trim();
            if (input.isEmpty()) {
                binding.edtCustomSize.setError("Vui lòng nhập kích thước");
                return;
        }
            int boardSize = Integer.parseInt(input);
            if (boardSize < 3 || boardSize > 20) {
                binding.edtCustomSize.setError("Chỉ từ 3 đến 20");
                return;
            }
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(Constants.KEY_BOARD_SIZE, boardSize);
            intent.putExtra(Constants.KEY_IS_BOT, binding.switchBot.isChecked());
            startActivity(intent);
        });
    }

    private void setupButton(android.view.View button, int boardSize) {

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);

            intent.putExtra(Constants.KEY_BOARD_SIZE, boardSize);
            intent.putExtra(Constants.KEY_IS_BOT, binding.switchBot.isChecked());

            startActivity(intent);
        });
    }
}
