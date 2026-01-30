package com.example.carogame.ai;

import com.example.carogame.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBot implements BotPlayer {

    private final Random random = new Random();
    @Override
    public Cell getMove(Cell[][] board, int boardSize) {
        List<Cell> emptyCells = new ArrayList<>();
        int size = boardSize;

        // Gom ô trống
        for(int r = 0 ; r < size ; r ++){
            for(int c = 0 ; c < size ; c ++){
                if(board[r][c].isEmpty()){
                    emptyCells.add(board[r][c]);
                }
            }
        }

        //không còn ô trống
        if(emptyCells.isEmpty()) return null;

        // Chọn ngẫu nhiên 1 ô
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
}
