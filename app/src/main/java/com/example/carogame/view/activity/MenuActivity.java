package com.example.carogame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.R;
import com.example.carogame.databinding.ActivityMenuBinding;
import com.example.carogame.utils.Constants;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews(){
        setupNumberPicker();
        setupButtons();
    }
    private void setupButtons() {

        setupButton(binding.btn3x3, 3);
        setupButton(binding.btn6x6, 6);
        setupButton(binding.btn9x9, 9);
        setupButton(binding.btn10x10, 10);
        setupCustombutton();
    }

    //Hàm xử lý việc nhập số lượng ô cờ muốn chơi
    private void setupNumberPicker(){
        binding.numberPickerSize.setMinValue(3);
        binding.numberPickerSize.setMaxValue(12);
        binding.numberPickerSize.setValue(3); //số mặc định lúc đầu
        binding.numberPickerSize.setWrapSelectorWheel(true); //làm cho khi ta cuộn xuống cuối sẽ quay lại đầu VD: 12 -> 3
    }

    private void setupCustombutton(){
        binding.btnCustom.setOnClickListener(v ->{
            int boardSize = binding.numberPickerSize.getValue();
            Intent intent = new Intent(this,GameActivity.class);
            intent.putExtra(Constants.KEY_BOARD_SIZE,boardSize);
            intent.putExtra(Constants.KEY_IS_BOT,binding.switchBot.isChecked());
            startActivity(intent);
        });
        binding.btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
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
