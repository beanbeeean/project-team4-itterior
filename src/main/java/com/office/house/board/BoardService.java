package com.office.house.board;

import com.office.house.like.LikeDto;
import com.office.house.util.page.Criteria;
import com.office.house.util.page.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public BoardDto getBoard(int bNo) {
        log.info("[BoardService] getBoard()");

        iBoardDaoMapper.updateHit(bNo);
        return iBoardDaoMapper.getBoard(bNo);
    }

    @Override
    public Map<String, Object> getBoardList(int sort, int pageNum, int amount, String keyword) {
        log.info("[BoardService] getBoardList()");

        Map<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria(pageNum, amount);
        List<BoardDto> boardDtos = iBoardDaoMapper.selectBoardList(sort, keyword, criteria.getSkip(), criteria.getAmount());
        int totalCnt = iBoardDaoMapper.selectBoardListCnt(sort, keyword, criteria.getSkip(), criteria.getAmount());
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        List<Integer> likeList = new ArrayList<>();
        for(BoardDto dto : boardDtos){
            if(dto.getB_like() > 0){
                likeList.add(dto.getB_no());
            }
        }
        List<LikeDto> isLikedDtos = new ArrayList<>();
        if(likeList.size() > 0){
            isLikedDtos = iBoardDaoMapper.selectLikedBoard(likeList);
        }

        map.put("totalCnt", totalCnt);
        map.put("isLikedDtos", isLikedDtos);
        map.put("boardDtos", boardDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public BoardDto boardModifyForm(int bNo) {
        log.info("[BoardService] boardModifyForm()");

        return iBoardDaoMapper.boardModifyForm(bNo);
    }

    @Override
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

    @Override
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

    @Override
    public int boardLikeUpdate(Map<String, String> msgMap) {
        log.info("productLikeUpdate");

        iBoardDaoMapper.increaseLike(msgMap.get("no"));
        iBoardDaoMapper.insertBoardLike(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));

        return iBoardDaoMapper.searchLike(msgMap.get("no"));
    }

    @Override
    public int boardLikeDelete(Map<String, String> msgMap) {
        log.info("productLikeUpdate");

        iBoardDaoMapper.decreaseLike(msgMap.get("no"));
        iBoardDaoMapper.deleteBoardLike(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));

        return iBoardDaoMapper.searchLike(msgMap.get("no"));
    }


}
