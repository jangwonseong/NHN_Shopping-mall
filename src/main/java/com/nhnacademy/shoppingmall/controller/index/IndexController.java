package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.GET, value = "/index.do")
public class IndexController implements BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public IndexController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.productService = serviceFactory.getProductService();
        this.categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 파라미터 받기
        String categoryId = req.getParameter("categoryId");
        String search = req.getParameter("search");
        String pageParam = req.getParameter("page");

        // 상품 목록 조회
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productService.getProductsBySearch(search);
        } else if (categoryId != null && !categoryId.trim().isEmpty()) {
            products = productService.getProductsByCategory(categoryId);
        } else {
            products = productService.getAllProducts();
        }

        // 카테고리 정보 조회
        List<Category> categories = categoryService.getAllCategories();

        // request에 데이터 설정
        req.setAttribute("products", products);
        req.setAttribute("categories", categories);

        return "shop/product/product_list";
    }
}
