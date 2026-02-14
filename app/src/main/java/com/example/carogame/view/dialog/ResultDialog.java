package com.example.carogame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.carogame.databinding.DialogResultBinding;

public class ResultDialog extends Dialog {

    private DialogResultBinding binding;

    private final String message;
    private final Runnable onReplay;
    private final Runnable onExit;

    public ResultDialog(
            @NonNull Context context,
            String message,
            Runnable onReplay,
            Runnable onExit
    ) {
        super(context);
        this.message = message;
        this.onReplay = onReplay;
        this.onExit = onExit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCancelable(false);

        setupUI();
    }

    private void setupUI() {

        binding.tvResult.setText(message);

        binding.btnReplay.setOnClickListener(v -> {
            dismiss();
            if (onReplay != null) onReplay.run();
        });

        binding.btnExit.setOnClickListener(v -> {
            dismiss();
            if (onExit != null) onExit.run();
        });
    }
}
