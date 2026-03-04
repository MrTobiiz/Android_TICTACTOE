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

    private final List<Cell> cellList; // Danh sách ô vd 3x3 -> 9 phần tử
    private final OnCellClickListener listener;

    public BoardAdapter(List<Cell> cellList, OnCellClickListener listener) {
        this.cellList = cellList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Gọi layout item_cell.xml để tạo ra số lượng ô cờ tương ứng
        ItemCellBinding binding = ItemCellBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new CellViewHolder(binding);
    }

    //Gắn dữ liệu vào ô tại vị trí position
    //VD:
    //position = 0 → ô đầu tiên
    //position = 8 → ô cuối (3x3)
    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        Cell cell = cellList.get(position);
        holder.bind(cell, listener);
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    // ViewHolder dùng Binding thay vì findViewById giúp tối ưu hiệu năng (không phải tạo lại view liên tục).
    public static class CellViewHolder extends RecyclerView.ViewHolder {

        private final ItemCellBinding binding;

        public CellViewHolder(@NonNull ItemCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Cell cell, final OnCellClickListener listener) {

            String value = cell.getValue();
            binding.tvCell.setText(value);

            // Đổi màu theo X / O
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
//3 nhiệm vụ chính:
//---------------------------------------------------------------
//1. Hiển thị bàn cờ lên màn hình
//  Nhận List<Cell> từ GameController
//  Cho RecyclerView biết:
//  Có bao nhiêu ô (getItemCount())
//  Mỗi ô hiển thị gì (onBindViewHolder())
//  Nếu không có Adapter -> không có ô cờ nào xuất hiện.
//---------------------------------------------------------------
//2. Cập nhật giao diện từng ô
//  Trong bind():
//  binding.tvCell.setText(value);
//  Nếu cell = "X" -> hiện X
//  Nếu cell = "O" -> hiện O
//  Nếu rỗng -> hiện trống
//  Và đổi màu theo X/O.
//  Adapter chịu trách nhiệm vẽ lại UI khi game thay đổi.
//---------------------------------------------------------------
//3. Bắt sự kiện click và gửi ra ngoài
//  listener.onCellClick(cell);
//  Adapter không xử lý luật chơi.
//  Nó chỉ nói:
//  “Người chơi vừa bấm ô này nè!”
//  Rồi GameActivity và GameController xử lý tiếp.
//---------------------------------------------------------------