package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(displayName = "jspAccessDeniedFilter",urlPatterns ={"*.jsp","*.JSP"} )
public class JspAccessDeniedFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //.jsp 로 오는 모든 요청은 404 페이지로 보냄니다.
        log.debug("filter-jsp access denied : {}" + req.getRequestURI());

    }
}
