package com.office.house.comment;

import java.util.Map;

public interface ICommentService {

    public int registReplyConfirm(CommentDto commentDto);

    public Map<String, Object> getCommentList(int b_no);

    public int registRereplyConfirm(CommentDto commentDto);

    public Map<String, Object> deleteComment(Map<String, Object> commentmap);

    public int modifyCommentConfirm(CommentDto commentDto, int c_no);
}
