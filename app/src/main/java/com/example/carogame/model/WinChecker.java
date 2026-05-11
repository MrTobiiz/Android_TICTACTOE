package com.example.carogame.model;

public class WinChecker {

    private final int boardSize;
    private final Cell[][] board;

    public WinChecker(int boardSize, Cell[][] board) {
        this.boardSize = boardSize;
        this.board = board;
    }

    public boolean checkWin(Cell lastCell) {
        int r = lastCell.getRow();
        int c = lastCell.getCol();
        String v = lastCell.getValue();

        return checkDirection(r, c, 1, 0, v)    // Dọc
                || checkDirection(r, c, 0, 1, v)    // Ngang
                || checkDirection(r, c, 1, 1, v)    // Chéo chính
                || checkDirection(r, c, 1, -1, v);  // Chéo phụ
    }

    private boolean checkDirection(int r, int c, int dr, int dc, String v) {
        int count = 1;


        count += countCells(r, c, dr, dc, v);   //Phần được dùng để đếm hướng xui
        count += countCells(r, c, -dr, -dc, v); //Phần được dùng để đếm hướng ngược

        int winCount = GameRule.getWinCount(boardSize);

        return GameRule.allowOverLine()
                ? count >= winCount
                : count == winCount;
    }

    private int countCells(int r, int c, int dr, int dc, String v) {
       //r =1; c=0; dr=0; dc=1
        int count = 0;
        int nextR = r + dr; // 1
        int nextC = c + dc; // 1

        while (isValid(nextR, nextC)) {
            Cell nextCell = board[nextR][nextC];
            if (v.equals(nextCell.getValue())) {
                count++;
                nextR += dr; // 1 + 0 = 1 => 1 + 0 = 1
                nextC += dc; // 1 + 1= 2 => 2 + 1 = 3
            } else {
                break;
            }
        }
        return count;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < boardSize && c >= 0 && c < boardSize;
    }
}
