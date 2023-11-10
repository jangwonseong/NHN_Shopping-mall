package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "welcomePageFilter",urlPatterns = "/", initParams = @WebInitParam(name = "welcomePage", value = "/index.do"))
public class WelcomePageFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo /요청이 오면 welcome page인 index.do redirect 합니다.

        log.debug("welcomepage  filter : {}", req.getRequestURI());

        if(req.getServletPath().equals("/")){
            res.sendRedirect(getInitParameter("welcomePage"));
            return;
        }
        chain.doFilter(req,res);
    }
}
