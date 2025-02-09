package com.nhnacademy.shoppingmall.controller.category;


import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category/delete.do")
public class DeleteCategoryController implements BaseController {
    private final CategoryService categoryService;

    public DeleteCategoryController() {
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("id");  // "category_id"가 아닌 "id"로 변경

        try {
            categoryService.deleteCategory(categoryId);
            return "redirect:/admin/category/list.do";
        } catch (Exception e) {
            req.setAttribute("error", "카테고리 삭제 실패: " + e.getMessage());
            return "redirect:/admin/category/list.do";
        }
    }
}
