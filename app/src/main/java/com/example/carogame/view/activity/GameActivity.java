package com.example.carogame.view.activity;
import com.example.carogame.model.data.HistoryDAO;
import com.example.carogame.model.history.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;

    private HistoryDAO historyDAO;
    private BoardAdapter adapter;
    private GameController gameController;
    private BotPlayer botPlayer;

    private boolean isBotMode;
    private int boardSize;

    // ⏱️ TIMER
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private int seconds = 0;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        boardSize = getIntent().getIntExtra(Constants.KEY_BOARD_SIZE, 3);
        isBotMode = getIntent().getBooleanExtra(Constants.KEY_IS_BOT, false);


        initGame();
    }

    private void initGame() {

        botPlayer = isBotMode ? new HeuristicBot() : null;

        gameController = new GameController(boardSize, isBotMode, botPlayer);
        historyDAO = new HistoryDAO(this);

        setupButtons();
        setupBoard();
        updateGameInfo();   // ⭐ HIỂN THỊ MODE + SIZE
        updateStatus();

        startTimer(); // ⏱️ bắt đầu đếm giờ
    }

    // ================= BUTTON =================
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

    // ================= BOARD =================
    private void setupBoard() {

        adapter = new BoardAdapter(
                gameController.getFlatBoard(),
                this::onCellClick
        );

        binding.recyclerBoard.setLayoutManager(
                new GridLayoutManager(this, boardSize)
        );

        binding.recyclerBoard.setAdapter(adapter);
    }

    // ================= CLICK =================
    private void onCellClick(Cell cell) {

        if (gameController.isGameOver()
                || (isBotMode && gameController.getCurrentPlayer().equals(Constants.PLAYER_O))) {
            return;
        }

        boolean moveMade = gameController.playHumanMove(
                cell.getRow(),
                cell.getCol()
        );

        if (moveMade) {
            updateBoardAndStatus();

            if (isBotMode && !gameController.isGameOver()) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(this::triggerBotMove, 600);
            }
        }
    }

    private void triggerBotMove() {
        if (gameController.isGameOver()) return;

        gameController.playBotMove();
        updateBoardAndStatus();
    }

    // ================= UPDATE =================
    private void updateBoardAndStatus() {

        adapter.notifyDataSetChanged();

        if (gameController.isGameOver()) {

            stopTimer(); // ⏱️ dừng khi kết thúc

            String message = gameController.getWinner() != null
                    ? "Người chơi " + gameController.getWinner() + " thắng! 🎉"
                    : "Hòa!";

            saveGameToHistory();
            showResult(message);

        } else {
            updateStatus();
        }
    }

    private void updateStatus() {
        binding.tvCurrentPlayer.setText(
                "Lượt: " + gameController.getCurrentPlayer()
        );
    }

    // ================= GAME INFO =================
    private void updateGameInfo() {

        String mode = isBotMode ? "PVE (AI)" : "PVP";

        binding.tvGameInfo.setText(
                "Bàn cờ " + boardSize + "x" + boardSize + " - " + mode
        );
    }

    // ================= TIMER =================
    private void startTimer() {

        seconds = 0;

        timerRunnable = new Runnable() {
            @Override
            public void run() {

                int minutes = seconds / 60;
                int sec = seconds % 60;

                String time = String.format("%02d:%02d", minutes, sec);

                binding.tvTimer.setText(time);

                seconds++;

                timerHandler.postDelayed(this, 1000);
            }
        };

        timerHandler.post(timerRunnable);
    }

    private void saveGameToHistory() {
        android.util.Log.d("TEST", "ĐÃ LƯU SQLITE");

        String mode = isBotMode ? "PVE" : "PVP";

        String dateTime = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        ).format(new Date());

        String result = gameController.getWinner() != null
                ? "Player " + gameController.getWinner() + " thắng"
                : "Hòa";

        String moves = gameController.getMoveHistory().size() + " nước";

        String duration = seconds + " giây";

        HistoryItem item = new HistoryItem(
                boardSize + "x" + boardSize,
                mode,
                dateTime,
                "Player X",
                "Player O",
                result,
                moves,
                duration
        );

        historyDAO.insert(item);
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    // ================= RESULT =================
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
        startTimer(); // reset timer
    }
}