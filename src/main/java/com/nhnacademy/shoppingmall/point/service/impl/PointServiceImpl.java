package com.nhnacademy.shoppingmall.point.service.impl;

import com.nhnacademy.shoppingmall.point.domain.Point;
import com.nhnacademy.shoppingmall.point.repository.PointRepository;
import com.nhnacademy.shoppingmall.point.service.PointService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final UserService userService;

    public PointServiceImpl(PointRepository pointRepository, UserService userService) {
        this.pointRepository = pointRepository;
        this.userService = userService;
    }

    @Override
    public void earnPoints(String userId, int orderAmount, String orderId) {
        try {
            int points = (int)(orderAmount * 0.1);  // 10% 적립
            Point point = new Point(
                    userId,
                    points,
                    Point.PointType.EARN,
                    orderId,
                    "주문 적립 포인트"
            );
            pointRepository.save(point);

            // 사용자 포인트 업데이트
            User user = userService.getUser(userId);
            user.setUserPoint(user.getUserPoint() + points);
            userService.updateUser(user);

        } catch (Exception e) {
            log.error("포인트 적립 실패 - 사용자: {}, 주문번호: {}, 에러: {}",
                    userId, orderId, e.getMessage());
            // 포인트 적립 실패는 주문 처리에 영향을 주지 않음
        }
    }

    @Override
    public void usePoints(String userId, int amount, String orderId) {
        int currentPoints = getUserPoints(userId);
        if (currentPoints < amount) {
            throw new IllegalStateException("사용 가능한 포인트가 부족합니다.");
        }

        Point point = new Point(
                userId,
                amount,
                Point.PointType.USE,
                orderId,
                "주문 사용 포인트"
        );
        pointRepository.save(point);

        // 사용자 포인트 업데이트
        User user = userService.getUser(userId);
        user.setUserPoint(user.getUserPoint() - amount);
        userService.updateUser(user);
    }

    @Override
    public List<Point> getPointHistory(String userId, int page, int size) {
        return pointRepository.findByUserIdWithPaging(userId, (page-1)*size, size);
    }

    @Override
    public int getUserPoints(String userId) {
        return pointRepository.getTotalPoints(userId);
    }
}
