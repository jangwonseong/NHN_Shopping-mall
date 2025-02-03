package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import lombok.extern.slf4j.Slf4j;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;


@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final User user;
    private final int point;

    public PointChannelRequest(User user, int point) {
        this.user = user;
        this.point = point;
    }

    @Override
    public void execute() {
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        try {
            DbConnectionThreadLocal.initialize();

            // 포인트 적립 로직
            UserRepository userRepository = new UserRepositoryImpl();
            User currentUser = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 포인트 업데이트
            currentUser.setUserPoint(currentUser.getUserPoint() + point);
            userRepository.update(currentUser);

            log.debug("Point accumulated: userId={}, point={}", user.getUserId(), point);
        } catch (Exception e) {
            log.error("Point accumulation failed", e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }
}
