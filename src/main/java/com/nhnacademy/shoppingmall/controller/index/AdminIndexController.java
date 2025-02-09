package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/index.do")
public class AdminIndexController implements BaseController {
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    public AdminIndexController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.productService = serviceFactory.getProductService();
        this.userService = serviceFactory.getUserService();
        this.categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 통계 정보 설정
        req.setAttribute("totalProducts", productService.getAllProducts().size());
        req.setAttribute("totalUsers", userService.getAllUsers().size());
        req.setAttribute("totalCategories", categoryService.getAllCategories().size());

        // 최근 등록된 상품 목록
        List<Product> recentProducts = productService.getAllProducts().stream()
                .limit(5)
                .collect(Collectors.toList());
        req.setAttribute("recentProducts", recentProducts);

        // 카테고리 맵 생성
        Map<String, String> categoryMap = new HashMap<>();
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            categoryMap.put(category.getCategoryId(), category.getCategoryName());
        }
        req.setAttribute("categoryMap", categoryMap);

        return "admin/index";
    }
}
