package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/add.do")
public class AdminAddProductController implements BaseController {
    private final ProductService productService;

    public AdminAddProductController() {
        this.productService = ServiceFactory.getInstance().getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        String category = req.getParameter("category");
        String description = req.getParameter("description");
        int stock = Integer.parseInt(req.getParameter("stock"));

        Product product = new Product(
                UUID.randomUUID().toString(),
                name,
                category,
                description,
                price,
                stock
        );

        productService.saveProduct(product);
        return "redirect:/admin/product/list.do";
    }
}