package edu.youzg.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-03 21:33
 * @Description: 带你深究Java的本质！
 */
@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置 安全管理器
        bean.setSecurityManager(securityManager);

        // 添加shiro的内置过滤器
        /*
            anon: 无需认证，可以访问
            authc: 必须认证，才能访问
            user: 必须拥有“记住我”功能才能用
            perms: 拥有对某个资源的权限，才能访问
            role: 拥有某个角色的权限，才能访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 授权，正常情况下，未授权会跳转到 “未授权页面”
        filterChainDefinitionMap.put("/user/add", "perms[user:add]");   // 只有拥有 user:add 权限的用户才能访问目标目录
        filterChainDefinitionMap.put("/user/update", "perms[user:update]");   // 只有拥有 user:update 权限的用户才能访问目标目录

        // 拦截
        //filterChainDefinitionMap.put("/user/add", "authc");
        //filterChainDefinitionMap.put("/user/update", "authc");
        filterChainDefinitionMap.put("/user/*", "authc");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 设置 登录页面
        bean.setLoginUrl("/toLogin");

        // 设置 未授权页面
        bean.setUnauthorizedUrl("/unauthorized");

        return bean;
    }

    // DefaultWebSecurityManager
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm, @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 UserRealm
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie);
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        /*这个参数是cookie的名称，对应前端页面的checkbox的name=remremberMe*/
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 设置“记住我”的寿命，默认有效时间为30天，单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    // 创建 realm对象，需要自定义
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    // 整合ShiroDialect(整合shiro-thymeleaf)
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
