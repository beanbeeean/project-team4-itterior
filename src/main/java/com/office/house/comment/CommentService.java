package com.office.house.comment;

import com.office.house.board.BoardDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CommentService {

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    public int registReplyConfirm(CommentDto commentDto) {
        log.info("[CommentService] registReplyConfirm()");

        int result = iCommentDaoMapper.insertNewReply(commentDto);

        return result;
    }

    public Map<String, Object> getCommentList(int b_no) {
        log.info("[CommentService] getCommentList()");
        log.info(b_no);

        Map<String, Object> map = new HashMap<>();
        List<CommentDto> commentDtos = iCommentDaoMapper.selectCommentList(b_no);

        log.info(commentDtos.size());
        map.put("commentDtos",commentDtos);

        return map;
    }
}
