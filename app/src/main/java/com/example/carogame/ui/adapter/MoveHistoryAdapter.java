package com.example.carogame.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.model.Move;

import java.util.List;

public class MoveHistoryAdapter
        extends RecyclerView.Adapter<MoveHistoryAdapter.ViewHolder> {

    private final List<Move> moves;

    public MoveHistoryAdapter(List<Move> moves) {
        this.moves = moves;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_move_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Move move = moves.get(position);
        holder.tvMove.setText(
                (position + 1) + ". "
                        + move.getPlayer()
                        + " → (" + move.getRow()
                        + ", " + move.getCol() + ")"
        );
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMove;

        ViewHolder(View itemView) {
            super(itemView);
            tvMove = itemView.findViewById(R.id.tvMove);
        }
    }
}

