package com.sln.ontime.shiro;

import com.sln.ontime.shiro.fliter.CrosFilter;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description shiro的配置
 * @author guopei
 * @date 2020-04-30 23:43
 */
@Configuration
@Log4j2
public class ShiroConfig {

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    @Bean
    public WebSecurityManager securityManager(@Qualifier("sessionManager") SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean("sessionManager")
    public SessionManager sessionManager(){
        CustomDefaultWebSessionManager manager = new CustomDefaultWebSessionManager();
        manager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new LinkedHashMap<>();
        log.info("shiro过滤器配置");
        map.put("/","anon");
//        map.put("/api/admin/login","anon");
//        map.put("/api/control/**", "anon");
//        map.put("/api/customer/user/loginByAccount","anon");
//        map.put("/api/customer/user/loginByWechat","anon");
        map.put("/wx/login","anon");
        map.put("/css/**","anon");
        map.put("/js/**","anon");
        map.put("/fonts/**","anon");
        map.put("/sockjs-node/**","anon");
        map.put("/img/**","anon");
//        map.put("/login/**","anon");
//        map.put("/#/login/**","anon");
        map.put("/**","authc");

        shiroFilterFactoryBean.getFilters().put("authc",new CrosFilter());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}
