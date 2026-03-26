package com.example.carogame.model;

import com.example.carogame.utils.Constants;

public class Cell {

    // tạo ô cờ
    private final int row;
    private final int col;
    private String value;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = Constants.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = (value == null) ? Constants.EMPTY : value;
    }

    public boolean isEmpty() {
        return Constants.EMPTY.equals(value);
    }
}
