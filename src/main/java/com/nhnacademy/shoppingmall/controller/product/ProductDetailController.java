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

import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/product/detail.do")
public class ProductDetailController implements BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductDetailController() {
        this.productService = ServiceFactory.getInstance().getProductService();
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("id");

        Optional<Product> product = Optional.ofNullable(productService.getProduct(productId));
        if (product.isPresent()) {
            req.setAttribute("product", product.get());

            // 카테고리 정보도 함께 전달
            Optional<Category> category = Optional.ofNullable(categoryService.getCategory(product.get().getCategoryId()));
            category.ifPresent(value -> req.setAttribute("category", value));

            return "shop/product/product_detail";
        }

        // 상품을 찾을 수 없는 경우
        return "redirect:/index.do";
    }
}
