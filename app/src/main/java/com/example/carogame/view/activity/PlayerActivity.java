package com.example.carogame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.R;
import com.example.carogame.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // 1. Khởi tạo binding TRƯỚC
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnchoi.setOnClickListener(v -> {

            String p1 = binding.Player1.getText().toString().trim();
            String p2 = binding.Player2.getText().toString().trim();

            if (p1.isEmpty()) p1 = "PLAYER1_NAME";
            if (p2.isEmpty()) p2 = "PLAYER2_NAME";

            // Trong PlayerActivity.java
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("PLAYER1_NAME", binding.Player1.getText().toString());
            intent.putExtra("PLAYER2_NAME", binding.Player2.getText().toString());
            startActivity(intent);
        });
    }


}
