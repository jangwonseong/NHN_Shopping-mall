package com.nhnacademy.shoppingmall.point.repository;

import com.nhnacademy.shoppingmall.point.domain.Point;
import java.util.List;

public interface PointRepository {
    void save(Point point);
    List<Point> findByUserId(String userId);
    List<Point> findByUserIdWithPaging(String userId, int offset, int limit);
    int getTotalPoints(String userId);
}
