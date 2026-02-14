package com.example.carogame.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.carogame.databinding.ActivityGameBinding;
import com.example.carogame.model.ai.BotPlayer;
import com.example.carogame.model.ai.HeuristicBot;
import com.example.carogame.controller.GameController;
import com.example.carogame.model.Cell;
import com.example.carogame.view.adapter.BoardAdapter;
import com.example.carogame.view.dialog.MoveHistoryDialog;
import com.example.carogame.view.dialog.ResultDialog;
import com.example.carogame.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;

    private BoardAdapter adapter;
    private GameController gameController;
    private BotPlayer botPlayer;

    private boolean isBotMode;
    private int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ ViewBinding thay cho setContentView(R.layout...)
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boardSize = getIntent().getIntExtra(Constants.KEY_BOARD_SIZE, 3);
        isBotMode = getIntent().getBooleanExtra(Constants.KEY_IS_BOT, false);

        initGame();
    }

    private void initGame() {

        BotPlayer bot = isBotMode ? new HeuristicBot() : null;

        gameController = new GameController(boardSize, isBotMode, bot);

        setupButtons();
        setupBoard();
        updateStatus();
    }


    private void setupButtons() {

        binding.btnBack.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Thoát trận đấu?")
                    .setMessage("Bạn có chắc muốn quay lại màn hình chính?")
                    .setPositiveButton("Có", (d, w) -> finish())
                    .setNegativeButton("Không", null)
                    .show();
        });

        binding.btnHistory.setOnClickListener(v ->
                new MoveHistoryDialog(
                        this,
                        gameController.getMoveHistory()
                ).show()
        );
    }

    private void setupBoard() {

        adapter = new BoardAdapter(
                gameController.getFlatBoard(),
                cell -> onCellClick(cell)
        );

        binding.recyclerBoard.setLayoutManager(
                new GridLayoutManager(this, boardSize)
        );

        binding.recyclerBoard.setAdapter(adapter);
    }


    private void onCellClick(Cell cell) {

        boolean moveMade =
                gameController.playHumanMove(
                        cell.getRow(),
                        cell.getCol()
                );

        if (moveMade) {
            updateBoardAndStatus();
        }
    }



    private void updateBoardAndStatus() {

        adapter.notifyDataSetChanged();

        if (gameController.isGameOver()) {

            String message =
                    gameController.getWinner() != null
                            ? "Người chơi "
                            + gameController.getWinner()
                            + " thắng!"
                            : "Hòa!";

            showResult(message);

        } else {
            updateStatus();
        }
    }


    private void updateStatus() {
        binding.tvStatus.setText(
                "Lượt: " + gameController.getCurrentPlayer()
        );
    }


    private void showResult(String message) {
        new ResultDialog(
                this,
                message,
                this::resetGame,
                this::finish
        ).show();
    }

    private void resetGame() {
        gameController.reset();
        adapter.notifyDataSetChanged();
        updateStatus();
    }
}
