package com.example.carogame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carogame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // 2 giây
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ẩn ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Delay 2 giây rồi chuyển sang MenuActivity
        new Handler(getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}