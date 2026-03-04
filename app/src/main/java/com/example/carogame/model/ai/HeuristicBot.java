package com.example.carogame.model.ai;

import com.example.carogame.model.Player;
import com.example.carogame.model.WinChecker;
import com.example.carogame.model.Cell;
import com.example.carogame.utils.Constants;

public class HeuristicBot implements BotPlayer {

    private Player botPlayer;
    private Player opponentPlayer;

    public HeuristicBot() {
        // Mặc định khởi tạo (có thể thay đổi sau bằng setPlayers)
        this.botPlayer = new Player(Constants.PLAYER_O);
        this.opponentPlayer = new Player(Constants.PLAYER_X);
    }

    // Thiết lập đối tượng Player cho Bot và Đối thủ
    public void setPlayers(Player botPlayer, Player opponentPlayer) {
        this.botPlayer = botPlayer;
        this.opponentPlayer = opponentPlayer;
    }

    @Override
    public Cell getMove(Cell[][] board, int boardSize) {
        if (botPlayer == null || opponentPlayer == null) return null;

        String botSymbol = botPlayer.getSymbol();
        String opponentSymbol = opponentPlayer.getSymbol();
        
        WinChecker winChecker = new WinChecker(boardSize, board);

        // 1. Tìm nước đi để Bot thắng ngay lập tức
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Cell cell = board[r][c];
                if (cell.isEmpty()) {
                    cell.setValue(botSymbol);
                    if (winChecker.checkWin(cell)) {
                        cell.setValue(Constants.EMPTY);
                        return cell;
                    }
                    cell.setValue(Constants.EMPTY);
                }
            }
        }

        // 2. Chặn nước đi mà đối thủ có thể thắng
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Cell cell = board[r][c];
                if (cell.isEmpty()) {
                    cell.setValue(opponentSymbol);
                    if (winChecker.checkWin(cell)) {
                        cell.setValue(Constants.EMPTY);
                        return cell;
                    }
                    cell.setValue(Constants.EMPTY);
                }
            }
        }

        // 3. Ưu tiên chiếm vị trí trung tâm nếu bàn cờ còn trống
        int center = boardSize / 2;
        if (board[center][center].isEmpty()) {
            return board[center][center];
        }

        // 4. Nước đi mặc định: Tìm ô trống đầu tiên
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c].isEmpty()) {
                    return board[r][c];
                }
            }
        }

        return null;
    }
}
