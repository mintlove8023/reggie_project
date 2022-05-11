package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 小空
 * @create 2022-05-11 20:02
 * @description 登录拦截
 * 如果用户需要浏览到某些页面或是页面中的数据,则需要进行登录后才可以进行操作
 * 这里通过拦截来限制用户对某些特定资源数据进行访问
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //需要放行的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
        };

        //获取请求路径
        String requestURI = request.getRequestURI();
        log.info(requestURI);

        boolean isFilterChain = pathMatcher(urls, requestURI);
        //放行请求路径
        if (isFilterChain) {
            log.info("拦截uri路径 -> 放行");
            filterChain.doFilter(request, response);
            return;
        }

        //判断是否为登录状态
        if (request.getSession().getAttribute("employee") != null) {
            log.info("登录拦截 -> 放行");
            filterChain.doFilter(request, response);
            return;
        }

        //未登录,则不放行
        log.info("不放行");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配
     * 判断Request URI是否是urls中的路径
     *
     * @param urls 用来存放需要放行的请求路径
     * @param uri  请求路径
     * @return [true 匹配成功, false 匹配失败]
     */
    protected boolean pathMatcher(String[] urls, String uri) {
        for (String url : urls) {
            if (pathMatcher.match(url, uri)) {
                return true;
            }
        }
        return false;
    }
}
