package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 상품과 카테고리 목록 가져오기
        List<Product> productList = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();

        // 카테고리 ID -> 카테고리 이름 매핑
        Map<String, String> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getCategoryId(), category.getCategoryName());
        }

        // 로그 추가 (디버깅용)
        System.out.println("상품 개수: " + productList.size());
        System.out.println("카테고리 개수: " + categories.size());
        System.out.println("categoryMap: " + categoryMap);

        // JSP에서 사용할 속성 설정
        req.setAttribute("products", productList);
        req.setAttribute("categoryMap", categoryMap);

        return "/shop/main/index"; // JSP 파일 경로
    }
}
