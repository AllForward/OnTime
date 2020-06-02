package com.sln.ontime.filter;

import com.sln.ontime.handler.XssHttpServletRequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description 预防xss攻击过滤器
 * @author guopei
 * @date 2020-06-01 09:27
 */
@Log4j2
@WebFilter(filterName = "XssFilter", urlPatterns = {"/group/*", "/personal_plan/*", "/setting/*"})
@Component
@ServletComponentScan
public class MyXssFilter implements Filter {

    FilterConfig filterConfig = null;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("xss过滤器初始化");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("正在对表单数据进行过滤");
        filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {
        log.info("xss过滤器销毁");
        this.filterConfig = null;
    }
}
