package com.nhnacademy.shoppingmall.controller.category;


import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/deleteCategory.do")
public class DeleteCategoryController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        categoryService.deleteCategory(categoryId);
        return "redirect:/categories.do";
    }
}
