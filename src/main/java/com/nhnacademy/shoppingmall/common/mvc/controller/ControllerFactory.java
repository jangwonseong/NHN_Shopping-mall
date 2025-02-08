package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.exception.ControllerNotFoundException;
import com.nhnacademy.shoppingmall.controller.auth.LoginPostController;
import com.nhnacademy.shoppingmall.controller.auth.SignUpPostController;
import com.nhnacademy.shoppingmall.controller.user.UserUpdateController;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserRegisterServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ControllerFactory {
    public static final String CONTEXT_CONTROLLER_FACTORY_NAME = "CONTEXT_CONTROLLER_FACTORY";
    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public ControllerFactory() {
    }

    public void initialize(Set<Class<?>> c, ServletContext ctx) {
        if (Objects.isNull(c)) {
            log.info("Controller not found");
            return;
        }

        // 모든 컨트롤러 클래스를 순회하면서 인스턴스를 생성하여 beanMap에 저장
        for (Class<?> controllerClass : c) {
            RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                RequestMapping.Method method = requestMapping.method();
                String[] values = requestMapping.value();

                for (String value : values) {
                    String key = getKey(method.name().toUpperCase(), value);
                    try {
                        Object controllerInstance = createControllerInstance(controllerClass);
                        beanMap.put(key, controllerInstance);
                        log.debug("Mapped {} to {}", key, controllerClass.getName());
                    } catch (Exception e) {
                        log.error("Controller instantiation failed: {}", controllerClass.getName(), e);
                    }
                }
            }
        }

        // ControllerFactory를 ServletContext에 저장
        ctx.setAttribute(CONTEXT_CONTROLLER_FACTORY_NAME, this);
    }

    private Object createControllerInstance(Class<?> controllerClass) throws Exception {
        // 기본적으로 인자가 없는 생성자를 사용하여 객체 생성
        return controllerClass.getDeclaredConstructor().newInstance();
    }

    private Object getBean(String key) {
        return beanMap.get(key);
    }

    public Object getController(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String servletPath = request.getServletPath();
        String key = getKey(method, servletPath);
        Object controller = getBean(key);
        if (controller == null) {
            throw new ControllerNotFoundException("Controller not found");
        }
        return controller;
    }

    public Object getController(String method, String path) {
        String key = method + "-" + path;
        Object controller = beanMap.get(key);
        if (controller == null) {
            throw new ControllerNotFoundException("Controller not found");
        }
        return controller;
    }

    private String getKey(String method, String path) {
        return String.format("%s-%s", method.toUpperCase(), path);
    }
}
