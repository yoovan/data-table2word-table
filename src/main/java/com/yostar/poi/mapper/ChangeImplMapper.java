package com.yostar.poi.mapper;

import com.yostar.poi.dto.ConnectionReqDto;
import com.yostar.poi.dto.ParamReqDto;
import com.yostar.poi.dto.TableDetailReqDto;
import com.yostar.poi.pojo.TableDo;
import com.yostar.poi.utils.DBUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChangeImplMapper implements ChangeMapper {

    @Value("${spring.datasource.driver-class-name}")
    private String driverName;


    /**
     * 获取总数据表
     *
     * @param paramReqDto
     * @return
     */
    @Override
    public List<TableDo> getTables(ParamReqDto paramReqDto) {
        ConnectionReqDto connectionReqDto = new ConnectionReqDto();
        BeanUtils.copyProperties(paramReqDto, connectionReqDto);
        connectionReqDto.setUrl("jdbc:mysql://" + paramReqDto.getHost()
                + ":" + paramReqDto.getPort()
                + "/" + paramReqDto.getDataSource()
                + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        connectionReqDto.setDriverName(driverName);
        DBUtils.getConnection(connectionReqDto);
        List<TableDo> tables = new ArrayList<>();
        ResultSet rs = DBUtils.executeQuery("SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='" + paramReqDto.getDataSource().trim() + "';");
        try {
            if (rs != null) {
                while (rs.next()) {
                    TableDo tb = new TableDo();
                    tb.setName(rs.getString(1));
                    tb.setDescription(rs.getString(2));
                    tables.add(tb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection();
        }
        return tables;
    }

    @Override
    public List<Map<String, String>> getTableDetail(TableDetailReqDto tableDetailReqDto) {
        ConnectionReqDto connectionReqDto = new ConnectionReqDto();
        BeanUtils.copyProperties(tableDetailReqDto, connectionReqDto);
        connectionReqDto.setUrl("jdbc:mysql://" + tableDetailReqDto.getHost()
                + ":" + tableDetailReqDto.getPort()
                + "/" + tableDetailReqDto.getDataSource()
                + "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        connectionReqDto.setDriverName(driverName);
        DBUtils.getConnection(connectionReqDto);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COLUMN_NAME,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_TYPE,COLUMN_KEY,COLUMN_COMMENT ")
                .append("	FROM information_schema.`COLUMNS` ")
                .append("	WHERE TABLE_SCHEMA ='").append(tableDetailReqDto.getDataSource()).append("' ")
                .append("   AND TABLE_NAME = '").append(tableDetailReqDto.getTableName()).append("' ");
        ResultSet rs = DBUtils.executeQuery(sql.toString());
        List<Map<String, String>> columns = new ArrayList<>();
        try {
            if (rs != null) {
                while (rs.next()) {
                    Map<String, String> column = new HashMap<>();
                    column.put("name", rs.getString(1));
                    column.put("defaultValue", rs.getString(2));
                    column.put("canNull", "YES".equalsIgnoreCase(rs.getString(3)) ? "是" : "否");
                    column.put("type", rs.getString(4));
                    column.put("key", rs.getString(5));
                    column.put("comment", rs.getString(6));
                    columns.add(column);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection();
        }
        return columns;
    }
}
