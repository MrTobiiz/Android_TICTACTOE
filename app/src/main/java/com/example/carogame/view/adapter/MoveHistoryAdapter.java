package com.example.carogame.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.databinding.ItemMoveHistoryBinding;
import com.example.carogame.model.Move;

import java.util.List;

public class MoveHistoryAdapter
        extends RecyclerView.Adapter<MoveHistoryAdapter.ViewHolder> {

    private final List<Move> moves;

    public MoveHistoryAdapter(List<Move> moves) {
        this.moves = moves;
    }

    //Tạo giao diện cho mỗi dòng trong danh sách
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoveHistoryBinding binding = ItemMoveHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Move move = moves.get(position);

        holder.binding.tvMove.setText(
                (position + 1) + ". "
                        + move.toDisplayString()
        );
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemMoveHistoryBinding binding;

        ViewHolder(ItemMoveHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
