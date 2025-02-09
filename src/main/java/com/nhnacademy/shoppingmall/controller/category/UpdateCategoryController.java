package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/update.do")
public class UpdateCategoryController implements BaseController {
    private final CategoryService categoryService;

    public UpdateCategoryController() {
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String categoryName = req.getParameter("category_name");

        try {
            Category category = new Category(categoryId, categoryName);
            categoryService.updateCategory(category);
            return "redirect:/admin/category/list.do";
        } catch (Exception e) {
            req.setAttribute("error", "카테고리 수정 실패: " + e.getMessage());
            return "redirect:/admin/category/list.do";
        }
    }
}
