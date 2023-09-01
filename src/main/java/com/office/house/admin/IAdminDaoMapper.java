package com.office.house.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDaoMapper {
    AdminDto selectAdminForLogin(AdminDto adminDto);
}
