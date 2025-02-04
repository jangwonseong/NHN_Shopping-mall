package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(()->false);

    public static void initialize(){
        //todo#3 - connection pool에서 connectionThreadLocal에 connection을 할당합니다.
        //connectiond의 Isolation level을 READ_COMMITED를 설정 합니다.
        //auto commit 을 false로 설정합니다.

        try {
            Connection connection = DbUtils.getDataSource().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            connectionThreadLocal.set(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError){
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError(){
        return sqlErrorThreadLocal.get();
    }

    public static void reset(){

        /* todo#4 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
           getSqlError() 에러가 존재하면 rollback 합니다.
           getSqlError() 에러가 존재하지 않다면 commit 합니다.
           다음 요청에서 현제 사용하고 있는 connection을 재 사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
        */

        try {
            Connection connection = getConnection();
            if(Objects.nonNull(connection)) {
                if (getSqlError()) {
                    connection.rollback();
                }else{
                    connection.commit();
                }
            }
            connection.close();
        } catch (SQLException e) {
            log.error("connection close error");
        }finally {
            connectionThreadLocal.remove();
        }

    }
}
