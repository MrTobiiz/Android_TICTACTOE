package com.example.carogame.ai;

import com.example.carogame.model.Cell;
import java.util.List;

public interface BotPlayer {

    //Em dùng interface vì BotPlayer chỉ định nghĩa hành vi chọn nước đi.
    //Việc này giúp em dễ mở rộng AI, thay đổi BOT mà không ảnh hưởng đến UI hay logic game.
    Cell getMove(Cell[][] board, int boardSize);
}
