package com.office.house.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ICommentDaoMapper {
    int insertNewReply(CommentDto commentDto);

    List<CommentDto> selectCommentList(int b_no);

    int insertRereply(CommentDto commentDto);

    int deleteComment(Map<String, Object> commentmap);
}
