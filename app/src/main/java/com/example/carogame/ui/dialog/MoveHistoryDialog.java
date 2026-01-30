package com.example.carogame.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.carogame.R;
import com.example.carogame.model.Move;
import com.example.carogame.ui.adapter.MoveHistoryAdapter;


import java.util.List;

public class MoveHistoryDialog extends Dialog {

    public MoveHistoryDialog(@NonNull Context context, List<Move> moves) {
        super(context);
        setContentView(R.layout.dialog_move_history);

        RecyclerView rv = findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new MoveHistoryAdapter(moves));

        // ⭐ QUAN TRỌNG
        if (getWindow() != null) {
            getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
    }
}

