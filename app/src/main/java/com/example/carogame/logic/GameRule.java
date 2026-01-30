package com.example.carogame.logic;

public class GameRule {

    private GameRule(){

    }
    // Kiểm tra kích thước hợp lệ
    public static boolean isValidBoardSize(int size){
        return size >= 3 && size <= 15;
    }
    /*
     * Số quân liên tiếp để thắng
     * - 3x3: 3 quân
     * - >3x3: 5 quân
     */

    public static int getWinCount(int boardSize){
        return boardSize == 3 ? 3 : 5;
    }
    /*
     * Có cho phép thắng hơn số quân quy định hay không
     * (Caro Việt Nam cho phép)
     */

    public static boolean allowOverLine(){
        return true;
    }
}
