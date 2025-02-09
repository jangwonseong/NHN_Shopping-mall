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

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/edit.do")
public class AdminEditProductController implements BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminEditProductController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.productService = serviceFactory.getProductService();
        this.categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("id");
        Product product = productService.getProduct(productId);
        List<Category> categories = categoryService.getAllCategories();

        req.setAttribute("product", product);
        req.setAttribute("categories", categories);
        return "admin/product/product_edit";
    }
}
