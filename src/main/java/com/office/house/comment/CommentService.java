package com.office.house.comment;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
