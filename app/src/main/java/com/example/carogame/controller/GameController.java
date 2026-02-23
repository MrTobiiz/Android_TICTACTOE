package com.example.carogame.controller;

import com.example.carogame.model.Cell;
import com.example.carogame.model.GameState;
import com.example.carogame.model.Move;
import com.example.carogame.model.WinChecker;
import com.example.carogame.model.ai.BotPlayer;
import com.example.carogame.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameController {

    private final GameState gameState;
    private final WinChecker winChecker;
    private final Stack<Move> moveHistory = new Stack<>();

    private BotPlayer botPlayer;
    private boolean isBotMode;

    public GameController(int boardSize, boolean isBotMode, BotPlayer botPlayer) {
        this.gameState = new GameState(boardSize);
        this.winChecker = new WinChecker(boardSize, gameState.getBoard());
        this.isBotMode = isBotMode;
        this.botPlayer = botPlayer;
    }

    // =============================
    // PUBLIC API cho View
    // =============================

    // Người chơi click vào ô
    public boolean playHumanMove(int row, int col) {

        if (!isPlayerTurn()) return false;

        boolean played = playInternal(row, col);

        if (played && isBotMode && !isGameOver()) {
            playBotMove();
        }

        return played;
    }

    //Trạng thái kết thúc
    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public String getCurrentPlayer() {
        return gameState.getCurrentPlayer();
    }

    public String getWinner() {
        return gameState.getWinner();
    }


    public List<Cell> getFlatBoard() {
        List<Cell> list = new ArrayList<>();
        for (Cell[] row : gameState.getBoard()) {
            for (Cell cell : row) {
                list.add(cell);
            }
        }
        return list;
    }

    public List<Move> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }

    public void reset() {
        moveHistory.clear();
        gameState.reset();
    }

    // =============================
    // PRIVATE LOGIC
    // =============================


    private boolean playInternal(int row, int col) {

        if (gameState.isGameOver()) return false;

        // Kiểm tra xem ô đã được chọn chưa
        Cell cell = gameState.getBoard()[row][col];
        if (!cell.isEmpty()) return false;

        String currentPlayer = gameState.getCurrentPlayer();

        cell.setValue(currentPlayer);
        moveHistory.push(new Move(row, col, currentPlayer));

        if (winChecker.checkWin(cell)) {
            gameState.setWinner(currentPlayer);
            gameState.setGameOver(true);
            return true;
        }

        if (isDraw()) {
            gameState.setGameOver(true);
            return true;
        }

        gameState.switchTurn();
        return true;
    }

    private void playBotMove() {

        if (botPlayer == null) return;

        Cell botMove = botPlayer.getMove(
                gameState.getBoard(),
                gameState.getBoard().length
        );

        if (botMove != null) {
            playInternal(botMove.getRow(), botMove.getCol());
        }
    }

    private boolean isPlayerTurn() {
        return !gameState.isGameOver() &&
                (!isBotMode ||
                        gameState.getCurrentPlayer().equals(Constants.PLAYER_X));
    }

    private boolean isDraw() {
        for (Cell[] row : gameState.getBoard()) {
            for (Cell cell : row) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }
}
