package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.GET, value = "/index.do")
public class IndexController implements BaseController {
    private static final int ITEMS_PER_PAGE = 8;  // 한 페이지당 상품 수
    private final ProductService productService;
    private final CategoryService categoryService;

    public IndexController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.productService = serviceFactory.getProductService();
        this.categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 검색어와 페이지 파라미터 받기
        String search = req.getParameter("search");
        int currentPage = getCurrentPage(req.getParameter("page"));

        // 상품 목록 조회
        List<Product> allProducts;
        if (search != null && !search.trim().isEmpty()) {
            allProducts = productService.getProductsBySearch(search);
        } else {
            allProducts = productService.getAllProducts();
        }

        // 페이징 처리
        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
        int start = (currentPage - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, totalProducts);

        List<Product> pagedProducts = allProducts.subList(start, end);

        // 카테고리 정보 설정
        Map<String, String> categoryMap = new HashMap<>();
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            categoryMap.put(category.getCategoryId(), category.getCategoryName());
        }

        // request에 데이터 설정
        req.setAttribute("products", pagedProducts);
        req.setAttribute("categoryMap", categoryMap);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        return "shop/product/product_list";
    }

    private int getCurrentPage(String pageParam) {
        try {
            int page = Integer.parseInt(pageParam);
            return page > 0 ? page : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
