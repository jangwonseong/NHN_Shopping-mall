package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/add.do")
public class AddCategoryController implements BaseController {
    private final CategoryService categoryService;

    public AddCategoryController() {
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String categoryName = req.getParameter("category_name");
        int orderSeq = Integer.parseInt(req.getParameter("order_seq"));

        Category category = new Category(categoryId, categoryName, orderSeq);
        categoryService.saveCategory(category);

        return "redirect:/admin/category/list.do";
    }
}