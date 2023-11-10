package com.nhnacademy.shoppingmall.common.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//https://www.java-success.com/unit-testing-servlet-filter-with-mockito/ 참고

class AdminCheckFilterTest {
    AdminCheckFilter adminCheckFilter = new AdminCheckFilter();
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain filterChain;
    FilterConfig filterConfig;

    @BeforeEach
    void setUp(){
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
        filterConfig = Mockito.mock(FilterConfig.class);
    }

    @Test
    void admin_check_filter() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/users/list.do");
        adminCheckFilter.init(filterConfig);
        adminCheckFilter.doFilter(request,response,filterChain);
        adminCheckFilter.destroy();
    }

}