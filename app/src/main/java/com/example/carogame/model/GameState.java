package com.example.carogame.model;

public class GameState {

    private boolean gameOver;
    private String winner;

    public GameState() {
        gameOver = false;
        winner = null;
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
