package com.nhnacademy.shoppingmall.common.mvc.servlet;

import com.nhnacademy.shoppingmall.common.exception.HttpExceiption;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ControllerFactory;


import com.nhnacademy.shoppingmall.controller.auth.LoginPostController;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserRegisterServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = {"*.do"})
public class FrontServlet extends HttpServlet {
    private ControllerFactory controllerFactory;
    private ViewResolver viewResolver;

    @Override
    public void init() throws ServletException {
        //todo#7-1 controllerFactory를 초기화 합니다.
        controllerFactory = (ControllerFactory) getServletContext().getAttribute("CONTEXT_CONTROLLER_FACTORY");

        //todo#7-2 viewResolver를 초기화 합니다.
        viewResolver = new ViewResolver("/WEB-INF/views/", ".jsp");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewName = null;
        try {
            DbConnectionThreadLocal.initialize();
            BaseController baseController = (BaseController) controllerFactory.getController(req);
            viewName = baseController.execute(req, resp);
            processView(req, resp, viewName);
        } catch (Throwable e) {
            log.error("error:{}", e);
            handleException(req, resp, (Exception) e);
            if (e instanceof HttpExceiption httpExceiption) {
                httpExceiption.getStatusCode();
            }
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }


    private void processView(HttpServletRequest req, HttpServletResponse resp, String viewName) throws ServletException, IOException {
        if (viewName != null && viewResolver.isRedirect(viewName)) {
            String redirectUrl = viewResolver.getRedirectUrl(viewName);
            log.debug("redirectUrl:{}", redirectUrl);
            resp.sendRedirect(redirectUrl);
        } else if (viewName != null) {
            String layout = viewResolver.getLayOut(viewName);
            log.debug("viewName:{}", viewResolver.getPath(viewName));
            req.setAttribute(ViewResolver.LAYOUT_CONTENT_HOLDER, viewResolver.getPath(viewName));
            RequestDispatcher rd = req.getRequestDispatcher(layout);
            rd.include(req, resp);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        DbConnectionThreadLocal.setSqlError(true);
        //todo#7-5 예외가 발생하면 해당 예외에 대해서 적절한 처리를 합니다.
        // 예: 오류 페이지로 forward, 오류 로그 기록 등
        req.setAttribute("errorMessage", e.getMessage()); // 예시
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/error/error.jsp"); // 예시
        rd.forward(req, resp);
    }
}
