package com.office.house.like;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ILikeDaoMapper {
    int insertLikeCount(String type, String no, String u_id);

    int deleteLikeCount(String type, String no, String u_id);

    int selectLikeCount(String type, String no);

    int selectCurrentUserLikeCount(String type, String no, String u_id);

    int selectUserLikeCount(String type, String u_id);
}
