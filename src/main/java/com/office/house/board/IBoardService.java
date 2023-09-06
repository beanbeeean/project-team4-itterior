package com.office.house.board;

import java.util.Map;

public interface IBoardService {

    public int boardWriteConfirm(BoardDto boardDto);

    public Map<String, Object> getBoardList(int sort, int pageNum, int amount, String keyword);

    public BoardDto getBoard(int bNo);

    public BoardDto boardModifyForm(int bNo);

    public int boardmodifyConfirm(BoardDto boardDto, int b_no);

    public Map<String, Object> deleteBoard(Map<String, Object> boardmap);

    public Map<String, Object> getMainBoardList();

}
