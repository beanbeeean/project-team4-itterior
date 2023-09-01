package com.office.house.admin;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping({"/admin_login_form","","/"})
    public String adminLoginForm(){
        log.info("[AdminController] adminLoginForm()");

        String nextPage = "admin/admin_login_form";

        return nextPage;
    }

    @PostMapping({"/admin_login_confirm"})
    public String adminLoginConfirm(AdminDto adminDto, HttpSession session){
        log.info("[AdminController] adminLoginConfirm()");

        String nextPage = "redirect:/admin/admin_login_form";

        AdminDto loginedAdminDto = adminService.adminLoginConfirm(adminDto);

        if(loginedAdminDto != null) {
            session.setAttribute("loginedAdminDto", loginedAdminDto);
            session.setMaxInactiveInterval(60 * 30);
            nextPage = "redirect:/admin/admin_myPage";
        }

        return nextPage;
    }

    @GetMapping("/create_account_form")
    public String createAccountForm() {
        log.info("[AdminController] createAccountForm()");

        String nextPage = "admin/create_account_form";

        return nextPage;
    }

    @PostMapping("/create_account_confirm")
    public String createAccountConfirm(AdminDto adminDto){
        log.info("[AdminController] createAccountConfirm()");

        int result = -1;
        result = adminService.createAccountConfirm(adminDto);

        if(result>0)
            return "redirect:/admin";
        else
            return "redirect:/admin/create_account_form";
    }

    @GetMapping("/admin_myPage")
    public String adminMyPage() {
        log.info("[AdminController] adminMyPage()");

        String nextPage = "admin/admin_myPage";

        return nextPage;
    }

    @GetMapping("/admin_modify_form")
    public String adminModifyForm() {
        log.info("[AdminController] adminModifyForm()");

        String nextPage = "admin/admin_modify_form";

        return nextPage;
    }

    @PostMapping("/admin_modify_confirm")
    public String adminModifyConfirm(AdminDto adminDto){
        log.info("[AdminController] adminModifyConfirm()");

        int result = -1;
        result = adminService.adminModifyConfirm(adminDto);

        if(result>0)
            return "redirect:/admin/admin_myPage";
        else
            return "redirect:/admin/admin_modify_form";
    }
}
