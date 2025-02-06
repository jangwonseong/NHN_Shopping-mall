package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@RequestMapping(method = RequestMapping.Method.POST, value = "/addProduct.do")
public class AddProductController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        String category = req.getParameter("category");
        String description = req.getParameter("description"); // 🛠 설명 필드 추가
        int stock = Integer.parseInt(req.getParameter("stock"));

        Product product = new Product(
                UUID.randomUUID().toString(),  // productId 자동 생성
                name,
                category,
                description,
                price,
                stock
        );

        productService.saveProduct(product);
        return "redirect:/productList.do";
    }
}
