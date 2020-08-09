package com.yostar.poi.dto;

import lombok.Data;

@Data
public class TableDetailReqDto {
    private String host;
    private String port;
    private String username;
    private String password;
    private String dataSource;
    private String tableName;

}
