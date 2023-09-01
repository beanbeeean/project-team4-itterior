package com.office.house.board;

import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        return iBoardDaoMapper.getBoard(bNo);
    }
}
