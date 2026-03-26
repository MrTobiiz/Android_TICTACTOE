package com.example.carogame.model;

public class GameRule {

    private GameRule(){

    }

    /*
     * Số quân liên tiếp để thắng
     * - 3x3: 3 quân
     * - >3x3: 5 quân
     */
    public static int getWinCount(int boardSize){
        return boardSize <= 4 ? 3 : 5;
    }

    //cho phép thắng nhiều hơn số quân yêu cầu
    public static boolean allowOverLine(){
        return true;
    }
}
