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

    public int registRereplyConfirm(CommentDto commentDto) {
        log.info("[CommentService] registRereplyConfirm()");

        int result = iCommentDaoMapper.insertRereply(commentDto);

        return result;
    }

    public Map<String, Object> deleteComment(Map<String, Object> commentmap) {
        log.info("[CommentService] deleteComment()");

        Map<String, Object> map = new HashMap<>();
        int result = iCommentDaoMapper.deleteComment(commentmap);
        if(result > 0) {
            log.info("[CommentService] delete success");
        } else {
            log.info("[CommentService] delete fail");
        }

        map.put("result",result);
        return map;
    }

    public int modifyCommentConfirm(CommentDto commentDto, int c_no) {
        log.info("[CommentService] modifyCommentConfirm()");

        Map<String, Object> map = new HashMap<>();
        map.put("commentDto",commentDto);
        map.put("c_no", c_no);

        int result = iCommentDaoMapper.updateComment(map);
        if(result > 0) {
            log.info("modify success");
        } else {
            log.info("modify fail");
        }

        return result;
    }
}
