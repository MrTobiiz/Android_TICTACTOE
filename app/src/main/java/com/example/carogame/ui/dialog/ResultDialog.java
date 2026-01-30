package com.example.carogame.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.carogame.R;

public class ResultDialog extends Dialog {

    public ResultDialog(
            Context context,
            String message,
            Runnable onReplay,
            Runnable onExit
    ) {
        super(context);

        setContentView(R.layout.dialog_result);
        setCancelable(false);

        TextView tvResult = findViewById(R.id.tvResult);
        Button btnReplay = findViewById(R.id.btnReplay);
        Button btnExit = findViewById(R.id.btnExit);

        tvResult.setText(message);

        btnReplay.setOnClickListener(v -> {
            dismiss();
            if (onReplay != null) onReplay.run();
        });

        btnExit.setOnClickListener(v -> {
            dismiss();
            if (onExit != null) onExit.run();
        });
    }
}
