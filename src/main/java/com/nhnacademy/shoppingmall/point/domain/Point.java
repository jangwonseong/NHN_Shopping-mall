package com.nhnacademy.shoppingmall.point.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Point {
    private String pointId;      // UUID
    private String userId;       // 사용자 ID
    private int amount;          // 포인트 금액
    private PointType type;      // 적립/사용 구분
    private String orderId;      // 관련 주문 ID
    private LocalDateTime createdAt;  // 생성일시
    private String description;  // 설명

    public enum PointType {
        EARN("적립"),
        USE("사용");

        private final String description;

        PointType(String description) {
            this.description = description;
        }
    }

    public Point(String userId, int amount, PointType type, String orderId, String description) {
        this.pointId = UUID.randomUUID().toString();
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.orderId = orderId;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}