package com.example.carogame.model.history;

public class HistoryItem {

    public String boardSize;
    public String gameMode;
    public String dateTime;
    public String playerX;
    public String playerO;
    public String result;
    public String moves;
    public String duration;

    public HistoryItem(String boardSize, String gameMode, String dateTime,
                       String playerX, String playerO,
                       String result, String moves, String duration) {

        this.boardSize = boardSize;
        this.gameMode = gameMode;
        this.dateTime = dateTime;
        this.playerX = playerX;
        this.playerO = playerO;
        this.result = result;
        this.moves = moves;
        this.duration = duration;
    }
}