package com.example.carogame.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.model.data.HistoryDAO;
import com.example.carogame.model.history.HistoryItem;
import com.example.carogame.view.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvTotalGames, tvEmptyState;
    Button btnBack, btnClear;

    HistoryDAO dao;
    List<HistoryItem> list;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        tvTotalGames = findViewById(R.id.tvTotalGames);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnBack = findViewById(R.id.btnBackFromHistory);
        btnClear = findViewById(R.id.btnClearHistory);

        dao = new HistoryDAO(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        btnBack.setOnClickListener(v -> finish());

        btnClear.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xóa lịch sử?")
                    .setMessage("Bạn chắc chắn muốn xóa toàn bộ?")
                    .setPositiveButton("Có", (d, w) -> {
                        dao.deleteAll();
                        loadData();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    private void loadData() {


        list = dao.getAll();

        adapter = new HistoryAdapter(list);
        recyclerView.setAdapter(adapter);

        tvTotalGames.setText("Tổng: " + list.size() + " trận");

        if (list.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    private void updateUI() {

        tvTotalGames.setText("Tổng: " + list.size() + " trận");

        if (list.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}