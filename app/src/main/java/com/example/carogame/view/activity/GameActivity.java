package com.example.carogame.view.activity;
import com.example.carogame.model.data.HistoryDAO;
import com.example.carogame.model.history.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Intent;
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
    private String PLAYER1_NAME, PLAYER2_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();

        PLAYER1_NAME = intent.getStringExtra("PLAYER1_NAME");
        PLAYER2_NAME = intent.getStringExtra("PLAYER2_NAME");

        if (PLAYER1_NAME == null) PLAYER1_NAME = "PLAYER1_NAME";
        if (PLAYER2_NAME == null) PLAYER2_NAME = "PLAYER2_NAME";

        isBotMode = intent.getBooleanExtra("IS_BOT_MODE", false);
        boardSize = intent.getIntExtra("BOARD_SIZE", 15);

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

    @Override
    protected void onDestroy() {
        stopTimer(); // Dừng hẳn timer khi đóng ứng dụng
        super.onDestroy();
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
        // Nếu trong lúc chờ delay mà game đã kết thúc thì dừng
        if (gameController.isGameOver()) return;

        // AI tính toán và thực hiện nước đi trong Controller
        gameController.playBotMove();

        // QUAN TRỌNG: Cập nhật lại giao diện để hiển thị quân 'O' và kiểm tra người thắng
        updateBoardAndStatus();
    }

    // ================= UPDATE =================
    private void updateBoardAndStatus() {
        // Làm mới danh sách quân cờ trên RecyclerView
        adapter.notifyDataSetChanged();

        if (gameController.isGameOver()) {
            stopTimer();

            String winner = gameController.getWinner();
            String message;

            if (winner != null) {
                // Ánh xạ X -> Player1, O -> Player2
                String winnerName = winner.equals(Constants.PLAYER_X) ? PLAYER1_NAME : PLAYER2_NAME;
                message = winnerName + " đã dành chiến thắng! 🎉";
            } else {
                message = "Trận đấu hòa!";
            }

            saveGameToHistory();
            showResult(message);
        } else {
            // Nếu game chưa kết thúc, cập nhật text hiển thị "Lượt: ..."
            updateStatus();
        }
    }

    private void updateStatus() {
        // Logic trong GameActivity
        String current = gameController.getCurrentPlayer(); // Trả về "X" hoặc "O"

        if (current.equals("X")) {
            binding.tvCurrentPlayer.setText("Lượt: " + PLAYER1_NAME + " (X)");
        } else {
            binding.tvCurrentPlayer.setText("Lượt: " + PLAYER2_NAME + " (O)");
        }
    }

    // ================= GAME INFO =================
    private void updateGameInfo() {

        String mode = isBotMode ? "PVE (BOT)" : "PVP";

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
        String mode = isBotMode ? "PVE" : "PVP";
        String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Lấy tên người thắng thực tế thay vì hiện "Player X"
        String winnerSymbol = gameController.getWinner();
        String result;
        if (winnerSymbol != null) {
            String winnerName = winnerSymbol.equals("X") ? PLAYER1_NAME : PLAYER2_NAME;
            result = winnerName + " thắng";
        } else {
            result = "Hòa";
        }

        String moves = gameController.getMoveHistory().size() + " nước";
        String duration = seconds + " giây";

        HistoryItem item = new HistoryItem(
                boardSize + "x" + boardSize,
                mode,
                dateTime,
                PLAYER1_NAME, // Sửa từ "Player X" -> player1
                PLAYER2_NAME, // Sửa từ "Player O" -> player2
                result,
                moves,
                duration
        );
        historyDAO.insert(item);
    }
    private void stopTimer() {
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
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
        stopTimer(); // BẮT BUỘC: Dừng timer cũ trước khi reset
        gameController.reset();
        seconds = 0; // Reset giây về 0
        adapter.notifyDataSetChanged();
        updateStatus();
        startTimer(); // Bắt đầu bộ đếm mới
    }
}