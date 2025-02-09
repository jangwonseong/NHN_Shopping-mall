package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category/list.do")
public class CategoryListController implements BaseController {
    private final CategoryService categoryService;

    public CategoryListController() {
        this.categoryService = ServiceFactory.getInstance().getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 카테고리 목록 조회 (정렬순서 오름차순)
        List<Category> categories = categoryService.getAllCategories();

        // request에 데이터 설정
        req.setAttribute("categories", categories);

        return "admin/category/category_list";
    }
}
