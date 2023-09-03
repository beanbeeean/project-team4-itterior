package com.office.house.admin;

import com.office.house.user.UserDto;
import com.office.house.util.PageDefine;
import com.office.house.util.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
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

    @GetMapping("/admin_delete_confirm")
    public String adminDeleteConfirm(AdminDto adminDto, HttpSession session){

        log.info("[AdminController] adminDeleteConfirm()");

        adminDto = (AdminDto) session.getAttribute("loginedAdminDto");

        int result = -1;
        result = adminService.adminDeleteConfirm(adminDto);

        if(result>0)
            return "redirect:/admin";
        else
            return "redirect:/admin/admin_myPage";
    }

    @GetMapping("/admin_list")
    public String adminList(HttpSession session, Model model,
                          @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                          @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                          @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_MEMBER_AMOUNT) int amount) {

        log.info("[AdminController] adminList()");

        String nextPage = "admin/admin_list";

        Map<String, Object> map = adminService.adminList(keyWord, pageNum, amount);

        List<AdminDto> AdminDtos = (List<AdminDto>) map.get("AdminDtos");

        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        model.addAttribute("AdminDtos", AdminDtos);
        model.addAttribute("pageMakerDto", pageMakerDto);
        model.addAttribute("keyWord", keyWord);

        return nextPage;
    }

    @GetMapping("/admin_list_detail")
    public String adminListDetail(@RequestParam("no") int a_no, Model model) {

        log.info("[AdminController] adminListDetail()");

        String nextPage = "admin/admin_list_detail";

        AdminDto adminDto = (AdminDto) adminService.adminListDetail(a_no);

        model.addAttribute("adminDto", adminDto);

        return nextPage;
    }

    @PostMapping("/admin_list_modify_confirm")
    public String adminListModifyConfirm(AdminDto adminDto){

        log.info("[AdminController] adminModifyConfirm()");

        System.out.println(adminDto);
        int result = -1;
        result = adminService.adminListModifyConfirm(adminDto);

        return "redirect:/admin/admin_list";
    }



}
