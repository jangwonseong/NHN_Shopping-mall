//package com.nhnacademy.shoppingmall.controller.order;
//
//import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
//import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
//import com.nhnacademy.shoppingmall.order.domain.Order;
//import com.nhnacademy.shoppingmall.order.service.OrderService;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.util.List;
//
//@RequestMapping(method = RequestMapping.Method.GET, value = "/orderHistory.do")
//public class OrderHistoryController implements BaseController {
//    private final OrderService orderService;
//
//    public OrderHistoryController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @Override
//    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        String userId = (String) req.getSession().getAttribute("userId"); // 세션에서 사용자 ID 가져오기
//        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1"); // 페이지 번호
//        int size = 10; // 한 페이지에 표시할 주문의 개수
//
//        List<Order> orders = orderService.getOrdersByUserIdWithPagination(userId, page, size);
//
//        req.setAttribute("orders", orders);
//
//        // 페이징 정보를 뷰에 전달
//        req.setAttribute("currentPage", page);
//        req.setAttribute("pageSize", size);
//        req.setAttribute("totalOrders", orders.size()); // 전체 주문 수 (여기서는 예시로 size 사용)
//
//        return "shop/mypage/order_history"; // 주문 내역 페이지로 포워드
//    }
//}
