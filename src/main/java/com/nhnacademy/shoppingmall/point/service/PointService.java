package com.nhnacademy.shoppingmall.point.service;

import com.nhnacademy.shoppingmall.point.domain.Point;
import java.util.List;

public interface PointService {
    // 포인트 적립 (주문 금액의 10%)
    void earnPoints(String userId, int orderAmount, String orderId);

    // 포인트 사용
    void usePoints(String userId, int amount, String orderId);

    // 포인트 내역 조회 (페이징)
    List<Point> getPointHistory(String userId, int page, int size);

    // 사용 가능한 포인트 조회
    int getUserPoints(String userId);
}
