package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserRegisterServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDateTime;
import java.util.UUID;

@WebListener
@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl(), new UserRegisterServiceImpl(new UserRepositoryImpl()));
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DbConnectionThreadLocal.initialize();
            // 사용자 계정이 없으면 등록
            if (userService.getUser("user") == null) {
                userService.saveUser(new User("user", "user", "12345", "98-11-03", User.Auth.ROLE_USER, 1000000, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
                userService.saveUser(new User("admin", "admin", "12345", "98-11-03", User.Auth.ROLE_ADMIN, 1000000, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            }

            // 상품과 카테고리 추가
            addProduct();

        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            log.error("Error initializing application: ", e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    private void addProduct() {
        // 상품 추가 (데이터 존재 여부 확인)
        addProduct("Nike Air Jordan 1", "한정판 스니커즈", 250000, 10, "1");
        addProduct("Adidas Yeezy Boost", "편안한 디자인의 스니커즈", 300000, 15, "2");
        addProduct("New Balance 990v5", "클래식한 감성을 지닌 운동화", 180000, 20, "3");
    }

    private void addProduct(String name, String description, int price, int stock, String categoryId) {
        // 상품 이름으로 데이터베이스에 상품이 존재하는지 확인 (getAllProducts() 사용)
        boolean exists = productService.getAllProducts().stream()
                .anyMatch(product -> product.getProductName().equals(name));

        // 상품이 존재하지 않는 경우에만 추가
        if (!exists) {
            productService.saveProduct(new Product(
                    UUID.randomUUID().toString(),
                    categoryId,
                    name,
                    description,
                    price,
                    stock
            ));
        }
    }
}
