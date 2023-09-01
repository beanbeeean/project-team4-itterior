package com.office.house.admin;

public interface IAdminService {


    AdminDto adminLoginConfirm(AdminDto adminDto);

    int createAccountConfirm(AdminDto adminDto);

    int adminModifyConfirm(AdminDto adminDto);

    int adminDeleteConfirm(AdminDto adminDto);
}
