package com.example.carogame.ai;

import com.example.carogame.logic.WinChecker;
import com.example.carogame.model.Cell;
import com.example.carogame.utils.Constants;

import java.util.List;

public class HeuristicBot implements BotPlayer {

    @Override
    public Cell getMove(Cell[][] board, int boardSize) {

        WinChecker winChecker = new WinChecker(boardSize, board);

        // 1. Nước thắng cho Bot (O)
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Cell cell = board[r][c];
                if (cell.isEmpty()) {
                    cell.setValue(Constants.PLAYER_O);
                    if (winChecker.checkWin(cell)) {
                        cell.setValue(Constants.EMPTY);
                        return cell;
                    }
                    cell.setValue(Constants.EMPTY);
                }
            }
        }

        // 2. Chặn nước thắng của người chơi (X)
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Cell cell = board[r][c];
                if (cell.isEmpty()) {
                    cell.setValue(Constants.PLAYER_X);
                    if (winChecker.checkWin(cell)) {
                        cell.setValue(Constants.EMPTY);
                        return cell;
                    }
                    cell.setValue(Constants.EMPTY);
                }
            }
        }

        // 3. Nước đi mặc định
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c].isEmpty()) {
                    return board[r][c];
                }
            }
        }

        return null; // gần như không bao giờ tới
    }
}
