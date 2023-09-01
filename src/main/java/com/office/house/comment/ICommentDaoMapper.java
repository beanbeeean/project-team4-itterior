package com.office.house.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentDaoMapper {
    int insertNewReply(CommentDto commentDto);

    List<CommentDto> selectCommentList(int b_no);

    int insertRereply(CommentDto commentDto);
}
