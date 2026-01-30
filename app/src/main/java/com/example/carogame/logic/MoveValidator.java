package com.example.carogame.logic;

import com.example.carogame.model.Cell;

public class MoveValidator {

    public static boolean isValidMove(Cell cell, boolean isGameOver) {

        if (isGameOver) return false;
        if (cell == null) return false;

        return cell.isEmpty();
    }
}
