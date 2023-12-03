package com.example.epidemic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @ClassName DruidTest
 * @Description
 * @Author chenxu
 * @Date 2023/12/3 1:15
 **/
@SpringBootTest
public class DruidTest {
    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * 验证数据库连接
     * @throws Exception
     */
    @Test
    public void testConnection() throws Exception {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
