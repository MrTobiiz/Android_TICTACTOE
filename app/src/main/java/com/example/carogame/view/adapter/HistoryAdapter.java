package com.example.carogame.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.model.history.HistoryItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> list;

    public HistoryAdapter(List<HistoryItem> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBoardSize, tvGameMode, tvDateTime;
        TextView tvPlayerX, tvPlayerO, tvResult;
        TextView tvMoves, tvDuration;

        public ViewHolder(View view) {
            super(view);

            tvBoardSize = view.findViewById(R.id.tvBoardSize);
            tvGameMode = view.findViewById(R.id.tvGameMode);
            tvDateTime = view.findViewById(R.id.tvDateTime);
            tvPlayerX = view.findViewById(R.id.tvPlayerX);
            tvPlayerO = view.findViewById(R.id.tvPlayerO);
            tvResult = view.findViewById(R.id.tvResult);
            tvMoves = view.findViewById(R.id.tvMoves);
            tvDuration = view.findViewById(R.id.tvDuration);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (list == null || list.isEmpty()) return;

        HistoryItem item = list.get(position);

        // Tránh null
        holder.tvBoardSize.setText(item.boardSize != null ? item.boardSize : "");
        holder.tvGameMode.setText(item.gameMode != null ? item.gameMode : "");
        holder.tvDateTime.setText(item.dateTime != null ? item.dateTime : "");
        holder.tvPlayerX.setText(item.playerX != null ? item.playerX : "");
        holder.tvPlayerO.setText(item.playerO != null ? item.playerO : "");
        holder.tvResult.setText(item.result != null ? item.result : "");
        holder.tvMoves.setText(item.moves != null ? item.moves : "");
        holder.tvDuration.setText(item.duration != null ? item.duration : "");

        // 🎯 Highlight kết quả
        if (item.result != null) {
            if (item.result.contains("X")) {
                holder.tvResult.setTextColor(Color.parseColor("#4CAF50")); // xanh
                holder.tvResult.setBackgroundColor(Color.parseColor("#E8F5E9"));
            } else if (item.result.contains("O")) {
                holder.tvResult.setTextColor(Color.parseColor("#F44336")); // đỏ
                holder.tvResult.setBackgroundColor(Color.parseColor("#FFEBEE"));
            } else {
                holder.tvResult.setTextColor(Color.GRAY); // hòa
            }
        }
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }
}