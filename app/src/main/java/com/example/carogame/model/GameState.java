package com.example.carogame.model;

import com.example.carogame.utils.Constants;

public class GameState {

    private final int boardSize;
    private final Cell[][] board;

    private String currentPlayer;
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
        currentPlayer = Constants.PLAYER_X;
        winner = null;
        gameOver = false;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchTurn() {
        currentPlayer = currentPlayer.equals(Constants.PLAYER_X)
                ? Constants.PLAYER_O
                : Constants.PLAYER_X;
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
