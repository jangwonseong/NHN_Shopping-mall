package com.nhnacademy.shoppingmall.common.util;


import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        //todo#1-1 {ip},{database},{username},{password} 설정
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/shoppingmall_db");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("P@ssw0rd");

        DATASOURCE = basicDataSource;
        //todo#1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        //todo#1-3 Validation Query를 설정하세요
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        //todo#1-4 적절히 변경하세요

    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
