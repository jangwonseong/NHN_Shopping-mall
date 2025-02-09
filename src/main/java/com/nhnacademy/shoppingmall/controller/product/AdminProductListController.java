package com.nhnacademy.shoppingmall.controller.product;

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

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/list.do")
public class AdminProductListController implements BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductListController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.productService = serviceFactory.getProductService();
        this.categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();

        Map<String, String> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getCategoryId(), category.getCategoryName());
        }

        req.setAttribute("products", products);
        req.setAttribute("categoryMap", categoryMap);

        return "admin/product/product_list";
    }
}