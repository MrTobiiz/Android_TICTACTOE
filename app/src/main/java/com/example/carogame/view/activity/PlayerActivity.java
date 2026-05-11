package com.example.carogame.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.R;
import com.example.carogame.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận cờ từ MenuActivity
        boolean isBotMode = getIntent().getBooleanExtra("IS_BOT_MODE", false);

        if (isBotMode) {
            // Ẩn Player2, chỉ nhập Player1
            binding.Player2.setVisibility(View.GONE);
        }


        binding.btnchoi.setOnClickListener(v -> {
            String p1 = binding.Player1.getText().toString().trim();
            String p2 = binding.Player2.getText().toString().trim();

            // Nhận lại dữ liệu từ MenuActivity
            Intent intentFromMenu = getIntent();
            int boardSize = intentFromMenu.getIntExtra("BOARD_SIZE", 15);

            if (p1.isEmpty() || (!isBotMode && p2.isEmpty())) {
                Toast.makeText(this,"Bạn phải nhập tên người chơi!", Toast.LENGTH_SHORT).show();
                if (p1.isEmpty()) {
                    binding.Player1.requestFocus();
                } else {
                    binding.Player2.requestFocus();
                }
            } else {
                // Nếu là chế độ bot thì gán tên mặc định cho Player2
                if (isBotMode) {
                    p2 = "Máy (BOT)";
                }

                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("PLAYER1_NAME", p1);
                intent.putExtra("PLAYER2_NAME", p2);
                intent.putExtra("BOARD_SIZE", boardSize);
                intent.putExtra("IS_BOT_MODE", isBotMode);
                startActivity(intent);
                finish();
            }
        });
        binding.btnback.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Thoát trận đấu?")
                    .setMessage("Bạn có chắc muốn quay lại màn hình menu?")
                    .setPositiveButton("Có", (d, w) -> finish())
                    .setNegativeButton("Không", null)
                    .show();
        });

    }



}
