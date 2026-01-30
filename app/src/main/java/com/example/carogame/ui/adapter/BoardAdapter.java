package com.example.carogame.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.model.Cell;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.CellViewHolder> {

    public interface OnCellClickListener {
        void onCellClick(Cell cell);
    }

    private final List<Cell> cellList; // Sửa lại để dùng List, không phải mảng 2D
    private final OnCellClickListener listener;

    public BoardAdapter(List<Cell> cellList, OnCellClickListener listener) {
        this.cellList = cellList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cell, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        // Lấy Cell từ danh sách đã được làm phẳng
        Cell cell = cellList.get(position);
        holder.bind(cell, listener);
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    // CellViewHolder không cần thay đổi
    public static class CellViewHolder extends RecyclerView.ViewHolder {
        TextView tvCell;

        public CellViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCell = itemView.findViewById(R.id.tvCell);
        }

        public void bind(final Cell cell, final OnCellClickListener listener) {
            // Hiển thị giá trị của ô cờ
            tvCell.setText(cell.getValue());

            // Thiết lập sự kiện click
            itemView.setOnClickListener(v -> {
                // Chỉ xử lý click nếu ô đó còn trống
                if (cell.isEmpty()) {
                    listener.onCellClick(cell);
                }
            });
        }
    }
}
