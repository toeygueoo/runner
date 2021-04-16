package com.ckx.runner.admin.controller;

import com.ckx.runner.admin.form.LoginForm;
import com.ckx.runner.core.domain.Customer;
import com.ckx.runner.core.domain.Manager;
import com.ckx.runner.core.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 公开的控制器，所有不需要身份认证的方法都会放到这个控制器中
 */
@Controller
@RequestMapping("/publiz")
public class PublizController {

    @Autowired
    public ManagerService managerService;

    @GetMapping("/login")
    public String login(){
        return "publiz/login";
    }

    //执行登录操作
    @PostMapping("/login")
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult,
                        Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMsg", getErrorMessage(bindingResult));
            return "publiz/login";
        }
        Manager manager = managerService.login(loginForm.getUsername(),loginForm.getPassword());
        if (manager == null) {
            //登录失败，重新跳转到登录界面，并给出相关提示
            model.addAttribute("errorMsg", "用户名或密码不正确，请重新输入");
            return "publiz/login";
        } else {
            //将用户信息存放到session
            session.setAttribute("manager", manager);

            //重定向到订单的首页
            return "redirect:/order/index";
        }
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("manager");
        //重定向到订单的首页
        return "redirect:/publiz/login";
    }

    private String getErrorMessage(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        int i = 0;
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (i != 0) {
                errorMsg.append("<br/>");
            }
            errorMsg.append(error.getDefaultMessage());
            i++;
        }
        return errorMsg.toString();
    }

}