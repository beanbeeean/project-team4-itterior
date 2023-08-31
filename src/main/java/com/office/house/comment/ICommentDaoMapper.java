package com.office.house.comment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentDaoMapper {
    int insertNewReply(CommentDto commentDto);
}
