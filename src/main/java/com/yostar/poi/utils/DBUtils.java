package com.yostar.poi.utils;

import com.yostar.poi.dto.ConnectionReqDto;
import org.springframework.util.StringUtils;

import java.sql.*;

public class DBUtils {

    public static Connection connection;

    public static Connection getConnection(ConnectionReqDto connectionReqDto) {
        try {
            if (StringUtils.isEmpty(connectionReqDto.getDriverName())) {
                throw new RuntimeException("数据库驱动为空！");
            }
            Class.forName(connectionReqDto.getDriverName());
            connection = DriverManager.getConnection(connectionReqDto.getUrl(), connectionReqDto.getUsername(), connectionReqDto.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
