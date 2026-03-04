package com.example.carogame.model;

import com.example.carogame.utils.Constants;

public class GameState {

    private final int boardSize;
    private final Cell[][] board;

    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private String winner;
    private boolean gameOver;

    public GameState(int boardSize) {
        this.boardSize = boardSize;
        this.board = new Cell[boardSize][boardSize];
        initBoard();
        reset();
    }

    private void initBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                board[r][c] = new Cell(r, c);
            }
        }
    }

    public void reset() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                cell.setValue(Constants.EMPTY);
            }
        }
        playerX = new Player(Constants.PLAYER_X);
        playerO = new Player(Constants.PLAYER_O);
        currentPlayer = playerX;
        winner = null;
        gameOver = false;
    }

    public Cell[][] getBoard() {
        return board;
    }

    // Trả về tên hoặc ký hiệu của người chơi hiện tại (String)
    public String getCurrentPlayer() {
        return currentPlayer.getSymbol();
    }

    // Logic đổi lượt chính xác giữa 2 đối tượng Player
    public void switchTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
