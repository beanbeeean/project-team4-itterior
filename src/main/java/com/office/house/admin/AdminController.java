package com.office.house.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/createAccountForm")
    public String createAccountForm() {
        System.out.println("[AdminMemberController] createAccountForm()");

        String nextPage = "admin/create_account_form";

        return nextPage;
    }
}
