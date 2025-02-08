//package com.nhnacademy.shoppingmall.controller.point;
//
//import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
//import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@RequestMapping(method = RequestMapping.Method.GET, value = "/pointHistory.do")
//public class PointHistoryController implements BaseController {
//    private PointService pointService;
//
//    public PointHistoryController(PointService pointService) {
//        this.pointService = pointService;
//    }
//
//    @Override
//    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        // 로그인된 사용자의 ID 가져오기
//        User user = (User) req.getSession().getAttribute("user");
//
//        if (user == null) {
//            req.setAttribute("error", "로그인 후 접근할 수 있습니다.");
//            return "redirect:/login.do";
//        }
//
//        // 포인트 사용 내역 페이징 처리
//        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
//        List<PointHistory> pointHistories = pointService.getPointHistory(user.getUserId(), page, 10); // 10개씩 페이징
//
//        req.setAttribute("pointHistories", pointHistories);
//        return "shop/mypage/pointHistory.jsp";
//    }
//}
