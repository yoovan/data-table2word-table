package com.yostar.poi.dto;

import lombok.Data;

@Data
public class ConnectionReqDto {
    private String driverName;
    private String url;
    private String username;
    private String password;
}
