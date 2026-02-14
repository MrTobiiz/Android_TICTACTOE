package com.example.carogame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.carogame.databinding.DialogMoveHistoryBinding;
import com.example.carogame.model.Move;
import com.example.carogame.view.adapter.MoveHistoryAdapter;

import java.util.List;

public class MoveHistoryDialog extends Dialog {

    private DialogMoveHistoryBinding binding;
    private final List<Move> moves;

    public MoveHistoryDialog(@NonNull Context context, List<Move> moves) {
        super(context);
        this.moves = moves;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogMoveHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupFullScreen();
    }

    private void setupRecyclerView() {
        binding.rvHistory.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        binding.rvHistory.setAdapter(
                new MoveHistoryAdapter(moves)
        );
    }

    private void setupFullScreen() {
        if (getWindow() != null) {
            getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
    }
}
