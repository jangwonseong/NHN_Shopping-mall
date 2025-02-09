package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/update.do")
public class AdminUpdateProductController implements BaseController {
    private final ProductService productService;

    public AdminUpdateProductController() {
        this.productService = ServiceFactory.getInstance().getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String productId = req.getParameter("product_id");
            String name = req.getParameter("name");
            String categoryId = req.getParameter("category_id");
            String description = req.getParameter("description");
            int price = Integer.parseInt(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));

            Product product = new Product(
                    productId,
                    categoryId,
                    name,
                    description,
                    price,
                    stock
            );

            productService.updateProduct(product);
            return "redirect:/admin/product/list.do";  // 수정 성공시 목록으로 이동
        } catch (Exception e) {
            log.error("Update failed", e);  // 에러 로깅 추가
            return "redirect:/admin/product/edit.do?id=" + req.getParameter("product_id");
        }
    }
}
