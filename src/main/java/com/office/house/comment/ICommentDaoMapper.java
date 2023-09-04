package com.office.house.comment;

import com.office.house.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ICommentDaoMapper {
    int insertNewReply(CommentDto commentDto);

    List<CommentDto> selectCommentList(int b_no);

    int insertRereply(CommentDto commentDto);

    int deleteComment(Map<String, Object> commentmap);

    int updateComment(Map<String, Object> map);

    int getMaxCno();

    void updateTargetCno(int cNo);

    int updateUserImage(UserDto userDto);
}
