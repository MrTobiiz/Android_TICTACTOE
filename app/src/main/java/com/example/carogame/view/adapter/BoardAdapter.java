package com.example.carogame.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.databinding.ItemCellBinding;
import com.example.carogame.model.Cell;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.CellViewHolder> {

    public interface OnCellClickListener {
        void onCellClick(Cell cell);
    }

    private final List<Cell> cellList;
    private final OnCellClickListener listener;

    public BoardAdapter(List<Cell> cellList, OnCellClickListener listener) {
        this.cellList = cellList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCellBinding binding = ItemCellBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new CellViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        Cell cell = cellList.get(position);
        holder.bind(cell, listener);
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    // ✅ ViewHolder dùng Binding thay vì findViewById
    public static class CellViewHolder extends RecyclerView.ViewHolder {

        private final ItemCellBinding binding;

        public CellViewHolder(@NonNull ItemCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Cell cell, final OnCellClickListener listener) {

            String value = cell.getValue();
            binding.tvCell.setText(value);

            // 🎨 Đổi màu theo X / O
            if ("X".equals(value)) {
                binding.tvCell.setTextColor(
                        binding.getRoot().getContext().getColor(R.color.colorX)
                );
            } else if ("O".equals(value)) {
                binding.tvCell.setTextColor(
                        binding.getRoot().getContext().getColor(R.color.colorO)
                );
            } else {
                binding.tvCell.setTextColor(
                        binding.getRoot().getContext().getColor(android.R.color.black)
                );
            }

            // Click
            binding.getRoot().setOnClickListener(v -> {
                if (cell.isEmpty()) {
                    listener.onCellClick(cell);
                }
            });
        }

    }
}
