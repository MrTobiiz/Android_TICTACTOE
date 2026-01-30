package com.example.carogame.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carogame.R;
import com.example.carogame.ai.BotPlayer;
import com.example.carogame.ai.HeuristicBot;
import com.example.carogame.logic.GameManager;
import com.example.carogame.model.Cell;
import com.example.carogame.ui.adapter.BoardAdapter;
import com.example.carogame.ui.dialog.MoveHistoryDialog;
import com.example.carogame.ui.dialog.ResultDialog;
import com.example.carogame.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvStatus;

    private BoardAdapter adapter;

    private GameManager gameManager;
    private BotPlayer botPlayer;

    private boolean isBotMode;
    private int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardSize = getIntent().getIntExtra(Constants.KEY_BOARD_SIZE, 3);
        isBotMode = getIntent().getBooleanExtra(Constants.KEY_IS_BOT, false);

        recyclerView = findViewById(R.id.recyclerBoard);
        tvStatus = findViewById(R.id.tvStatus);

        initGame();
    }


    private void initGame() {
        gameManager = new GameManager(boardSize);

        if (isBotMode) {
            botPlayer = new HeuristicBot(); // Hoặc RandomBot
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Thoát trận đấu?")
                    .setMessage("Bạn có chắc muốn quay lại màn hình chính?")
                    .setPositiveButton("Có", (d, w) -> finish())
                    .setNegativeButton("Không", null)
                    .show();
        });

        Button btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(v -> {
            new MoveHistoryDialog(
                    this,
                    gameManager.getMoveHistory()
            ).show();
        });

        List<Cell> cellList = new ArrayList<>();
        for (Cell[] row : gameManager.getBoard()) {
            cellList.addAll(Arrays.asList(row));
        }

        adapter = new BoardAdapter(cellList, this::onCellClick);
        recyclerView.setLayoutManager(new GridLayoutManager(this, boardSize));
        recyclerView.setAdapter(adapter);

        updateStatus();
    }

    private void onCellClick(Cell cell) {
        // Nếu game đã kết thúc hoặc không phải lượt của người chơi, không làm gì cả
        if (gameManager.isGameOver() || (isBotMode && !gameManager.getCurrentPlayer().equals(Constants.PLAYER_X))) {
            return;
        }

        // 1. Thực hiện nước đi của người chơi
        boolean moveMade = gameManager.play(cell.getRow(), cell.getCol());

        if (moveMade) {
            // 2. Cập nhật giao diện sau nước đi của người chơi
            updateBoardAndStatus();

            // 3. Nếu game chưa kết thúc và đang ở chế độ Bot, kích hoạt Bot
            if (!gameManager.isGameOver() && isBotMode) {
                // Thêm một khoảng trễ nhỏ để người chơi thấy nước đi của mình
                // trước khi Bot đánh, tạo cảm giác tự nhiên hơn.
                new Handler(Looper.getMainLooper()).postDelayed(this::triggerBotMove, 500);
            }
        }
    }

    private void triggerBotMove() {
        // Đảm bảo Bot chỉ đánh khi đến lượt của nó
        if (gameManager.isGameOver() || !gameManager.getCurrentPlayer().equals(Constants.PLAYER_O)) {
            return;
        }

        // Lấy nước đi từ Bot
        Cell botMove = botPlayer.getMove(gameManager.getBoard(), boardSize);
        if (botMove != null) {
            // Thực hiện nước đi của Bot
            gameManager.play(botMove.getRow(), botMove.getCol());
            // Cập nhật lại giao diện sau nước đi của Bot
            updateBoardAndStatus();
        }
    }

    private void updateBoardAndStatus() {
        adapter.notifyDataSetChanged();

        if (gameManager.isGameOver()) {
            String message = gameManager.getWinner() != null
                    ? "Người chơi " + gameManager.getWinner() + " thắng!"
                    : "Hòa!";
            showResult(message);
        } else {
            updateStatus();
        }
    }

    private void updateStatus() {
        tvStatus.setText("Lượt: " + gameManager.getCurrentPlayer());
    }

    private void showResult(String message) {
        ResultDialog dialog = new ResultDialog(
                this,
                message,
                this::resetGame,
                this::finish
        );
        dialog.show();
    }

    private void resetGame() {
        gameManager.reset();
        adapter.notifyDataSetChanged();
        updateStatus();
    }
}
