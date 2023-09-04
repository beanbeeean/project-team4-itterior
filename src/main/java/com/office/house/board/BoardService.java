package com.office.house.board;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class BoardService implements IBoardService {

    @Autowired
    IBoardDaoMapper iBoardDaoMapper;

    @Override
    public int boardWriteConfirm(BoardDto boardDto) {
        log.info("[BoardService] boardWriteConfirm()");

        int result = iBoardDaoMapper.insertNewBoard(boardDto);

        return result;
    }

    @Override
    public Map<String, Object> getBoardList() {
        log.info("[BoardService] getBoardList()");

        Map<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtos = iBoardDaoMapper.selectBoardList();

        log.info(boardDtos.size());
        map.put("boardDtos",boardDtos);

        return map;
    }

    @Override
    public BoardDto getBoard(int bNo) {
        log.info("[BoardService] getBoard()");

        iBoardDaoMapper.updateHit(bNo);
        return iBoardDaoMapper.getBoard(bNo);
    }

    public BoardDto boardModifyForm(int bNo) {
        log.info("[BoardService] boardModifyForm()");

        return iBoardDaoMapper.boardModifyForm(bNo);
    }

    public int boardmodifyConfirm(BoardDto boardDto, int b_no) {
        log.info("[BoardService] boardmodifyConfirm()");

        Map<String, Object> map = new HashMap<>();
        map.put("boardDto",boardDto);
        map.put("b_no", b_no);

        int result = iBoardDaoMapper.updateBoard(map);
        if(result > 0) {
            log.info("modify success");
        } else {
            log.info("modify fail");
        }

        return result;
    }

    public Map<String, Object> deleteBoard(Map<String, Object> boardmap) {
        log.info("[BoardService] deleteBoard()");

        Map<String, Object> map = new HashMap<>();
        int result = iBoardDaoMapper.deleteBoard(boardmap);
        if(result > 0) {
            log.info("[BoardService] delete success");
        } else {
            log.info("[BoardService] delete fail");
        }

        map.put("result",result);
        return map;
    }
}
