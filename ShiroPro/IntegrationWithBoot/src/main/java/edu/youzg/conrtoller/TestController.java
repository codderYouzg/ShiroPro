package edu.youzg.conrtoller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-03 19:21
 * @Description: 带你深究Java的本质！
 */
@Controller
public class TestController {

    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello World");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 封装 用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 指定登录方法，如果没有异常就说明OK了
        try {
            subject.login(token);

            return "index";
        } catch (UnknownAccountException e) {   // 用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) { // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        model.addAttribute("msg", "您的账户未被授权，禁止访问该页面!");
        return "noAuth";
    }

}
