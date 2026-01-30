package com.example.carogame.logic;

import com.example.carogame.model.Cell;
import com.example.carogame.utils.Constants;

import com.example.carogame.model.Move;
import java.util.Stack;
public class GameManager {

    private final int boardSize;
    private final Cell[][] board;
    private final WinChecker winChecker;

    private String currentPlayer = Constants.PLAYER_X;
    private String winner = null;
    private boolean gameOver = false;
    private Stack<Move> moveHistory = new Stack<>();

    public GameManager(int boardSize) {
        this.boardSize = boardSize;
        this.board = new Cell[boardSize][boardSize];
        initBoard();
        this.winChecker = new WinChecker(boardSize, board);
    }

    private void initBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                board[r][c] = new Cell(r, c);
            }
        }
    }

    public boolean play(int row, int col) {
        if (gameOver) return false;

        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return false;
        }

        Cell cell = board[row][col];
        if (!cell.isEmpty()) return false;

        // Đánh cờ
        cell.setValue(currentPlayer);

        // Lưu lịch sử
        moveHistory.push(new Move(row, col, currentPlayer));

        // Kiểm tra thắng
        if (winChecker.checkWin(cell)) {
            winner = currentPlayer;
            gameOver = true;
            return true;
        }

        // Hòa
        if (isDraw()) {
            gameOver = true;
            return true;
        }

        // Đổi lượt
        switchTurn();
        return true;
    }



    public boolean undo() {
        if (moveHistory.isEmpty()) return false;

        Move lastMove = moveHistory.pop();
        board[lastMove.getRow()][lastMove.getCol()]
                .setValue(Constants.EMPTY);

        currentPlayer = lastMove.getPlayer();
        gameOver = false;
        winner = null;

        return true;
    }

    private void switchTurn() {
        currentPlayer = currentPlayer.equals(Constants.PLAYER_X)
                ? Constants.PLAYER_O
                : Constants.PLAYER_X;
    }

    private boolean isDraw() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void reset() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                cell.setValue(Constants.EMPTY);
            }
        }
        moveHistory.clear();
        currentPlayer = Constants.PLAYER_X;
        winner = null;
        gameOver = false;
    }

    public Stack<Move> getMoveHistory() {
        return moveHistory;
    }

}
