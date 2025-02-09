package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category/edit.do")
public class EditCategoryController implements BaseController {
    private final CategoryService categoryService;

    public EditCategoryController() {
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("id");
        Category category = categoryService.getCategory(categoryId);
        req.setAttribute("category", category);
        return "admin/category/category_edit";
    }
}
