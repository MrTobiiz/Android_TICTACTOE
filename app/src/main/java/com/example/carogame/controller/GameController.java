package com.example.carogame.controller;

import com.example.carogame.model.Cell;
import com.example.carogame.model.GameState;
import com.example.carogame.model.Move;
import com.example.carogame.model.WinChecker;
import com.example.carogame.utils.Constants;

import java.util.Stack;

public class GameController {

    private final GameState gameState;
    private final WinChecker winChecker;
    private final Stack<Move> moveHistory = new Stack<>();

    public GameController(int boardSize) {
        this.gameState = new GameState(boardSize);
        this.winChecker = new WinChecker(boardSize, gameState.getBoard());
    }

    public boolean play(int row, int col) {

        if (gameState.isGameOver()) return false;

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

    public boolean undo() {
        if (moveHistory.isEmpty()) return false;

        Move lastMove = moveHistory.pop();
        gameState.getBoard()[lastMove.getRow()][lastMove.getCol()]
                .setValue(Constants.EMPTY);

        gameState.setGameOver(false);
        gameState.setWinner(null);

        return true;
    }

    private boolean isDraw() {
        for (Cell[] row : gameState.getBoard()) {
            for (Cell cell : row) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }

    public void reset() {
        moveHistory.clear();
        gameState.reset();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Stack<Move> getMoveHistory() {
        return moveHistory;
    }
}
