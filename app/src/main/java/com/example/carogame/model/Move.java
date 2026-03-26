package com.example.carogame.model;

public class Move {
    private final int row;
    private final int col;
    private final String player;

    public Move(int row, int col, String player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getPlayer() {
        return player;
    }

    public String toDisplayString(){
        return player + " → (" + row + ", " + col + ")";
    }
}
