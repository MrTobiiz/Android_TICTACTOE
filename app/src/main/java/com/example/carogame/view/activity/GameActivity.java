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
        gameController = new GameController(boardSize);

        if (isBotMode) {
            botPlayer = new HeuristicBot();
        }

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

        List<Cell> cellList = new ArrayList<>();
        for (Cell[] row : gameController.getGameState().getBoard()) {
            cellList.addAll(Arrays.asList(row));
        }

        adapter = new BoardAdapter(cellList, this::onCellClick);

        binding.recyclerBoard.setLayoutManager(
                new GridLayoutManager(this, boardSize)
        );

        binding.recyclerBoard.setAdapter(adapter);
    }

    private void onCellClick(Cell cell) {

        if (gameController.getGameState().isGameOver()
                || (isBotMode &&
                !gameController.getGameState()
                        .getCurrentPlayer()
                        .equals(Constants.PLAYER_X))) {
            return;
        }

        boolean moveMade = gameController.play(cell.getRow(), cell.getCol());

        if (moveMade) {
            updateBoardAndStatus();

            if (!gameController.getGameState().isGameOver() && isBotMode) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(this::triggerBotMove, 500);
            }
        }
    }

    private void triggerBotMove() {

        if (gameController.getGameState().isGameOver()
                || !gameController.getGameState()
                .getCurrentPlayer()
                .equals(Constants.PLAYER_O)) {
            return;
        }

        Cell botMove = botPlayer.getMove(
                gameController.getGameState().getBoard(),
                boardSize
        );

        if (botMove != null) {
            gameController.play(botMove.getRow(), botMove.getCol());
            updateBoardAndStatus();
        }
    }

    private void updateBoardAndStatus() {

        adapter.notifyDataSetChanged();

        if (gameController.getGameState().isGameOver()) {

            String message =
                    gameController.getGameState().getWinner() != null
                            ? "Người chơi "
                            + gameController.getGameState().getWinner()
                            + " thắng!"
                            : "Hòa!";

            showResult(message);

        } else {
            updateStatus();
        }
    }

    private void updateStatus() {
        binding.tvStatus.setText(
                "Lượt: " + gameController.getGameState().getCurrentPlayer()
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
