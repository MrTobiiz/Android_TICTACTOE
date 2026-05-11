package com.example.carogame.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carogame.databinding.ActivityMenuBinding;
import com.example.carogame.utils.Constants;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;
    private String player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Nhận tên (Phải đảm bảo PlayerActivity gửi đúng Key "PLAYER1_NAME")
        player1Name = getIntent().getStringExtra("PLAYER1_NAME");
        player2Name = getIntent().getStringExtra("PLAYER2_NAME");

        // Nếu null hoặc rỗng thì đặt tên mặc định cho thân thiện
        if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Người chơi 1";
        if (player2Name == null || player2Name.trim().isEmpty()) player2Name = "Người chơi 2";

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

        binding.btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
        });
        binding.btnOutofGame.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Thoát ứng dụng?")
                    .setMessage("Bạn có chắc muốn thoát trò chơi?")
                    .setPositiveButton("Có", (d, w) -> finishAffinity())
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    private void setupNumberPicker(){
        binding.numberPickerSize.setMinValue(3);
        binding.numberPickerSize.setMaxValue(20);
        binding.numberPickerSize.setValue(3);
        binding.numberPickerSize.setWrapSelectorWheel(true);
    }

    private void setupCustombutton(){
        binding.btnCustom.setOnClickListener(v -> {
            int boardSize = binding.numberPickerSize.getValue();
            navigateToGame(boardSize); // Dùng hàm chung cho đỡ lỗi
        });
    }

    private void setupButton(android.view.View button, int boardSize) {
        button.setOnClickListener(v -> navigateToGame(boardSize));
    }

    // Hàm chung để chuyển sang GameActivity - Đảm bảo ĐỦ dữ liệu
    private void navigateToGame(int size) {
        Intent intent = new Intent(this, PlayerActivity.class);
        boolean isBot = binding.switchBot.isChecked();

        // Gửi Key đồng bộ sang GameActivity
        intent.putExtra("PLAYER1_NAME", player1Name);
        intent.putExtra("IS_BOT_MODE", isBot);
        intent.putExtra("BOARD_SIZE", size);

        startActivity(intent);
    }
}