package com.office.house.board;

import java.util.Map;

public interface IBoardService {

    public int boardWriteConfirm(BoardDto boardDto);

    public Map<String, Object> getBoardList();

    public BoardDto getBoard(int bNo);
}
