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
public class CommentService implements ICommentService{

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    @Override
    public int registReplyConfirm(CommentDto commentDto) {
        log.info("[CommentService] registReplyConfirm()");

        int result = iCommentDaoMapper.insertNewReply(commentDto);
        int c_no = iCommentDaoMapper.getMaxCno();
        log.info("max_c_no : " + c_no);
        iCommentDaoMapper.updateTargetCno(c_no);

        return result;
    }

    @Override
    public Map<String, Object> getCommentList(int b_no) {
        log.info("[CommentService] getCommentList()");
        log.info(b_no);

        Map<String, Object> map = new HashMap<>();
        List<CommentDto> commentDtos = iCommentDaoMapper.selectCommentList(b_no);

        map.put("commentDtos",commentDtos);

        return map;
    }

    @Override
    public int registRereplyConfirm(CommentDto commentDto) {
        log.info("[CommentService] registRereplyConfirm()");

        int result = iCommentDaoMapper.insertRereply(commentDto);

        return result;
    }

    @Override
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

    @Override
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
