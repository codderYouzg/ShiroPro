package edu.youzg.config;

import edu.youzg.model.User;
import edu.youzg.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-03 21:35
 * @Description: 带你深究Java的本质！
 */

// 自定义的 UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了“授权”功能=>doGetAuthorizationInfo");

        // 获取到 当前登录的 用户对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();   // 获取到当前User对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置当前用户的权限
        //info.addStringPermission("user:add");
        //info.addStringPermission(currentUser.getPerms());
        String perms = currentUser.getPerms();
        String[] split = perms.split(";");
        HashSet<String> set = new HashSet<>(Arrays.asList(split));
        info.setStringPermissions(set);

        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了“认证”功能=>doGetAuthenticationInfo");

        // 用户名、密码 (正常情况下 从数据库中取)
        //String name = "youzg";
        //String password = "123456";

        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        //if (!userToken.getUsername().equals(name)) {
        //    return null;    // 抛出UnknownAccountException异常
        //}

        // 连接真实的数据库
        User user = userService.queryUserByName(userToken.getUsername());

        if (user == null) {
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser", user);

        // 密码加密：md5加密(已过时，不安全，简单密码可以被破解) md5盐值加密(会在md5加密的基础上增加一些东西，使得无法破解)
        // 密码认证，shiro内部自行完成
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }

}
