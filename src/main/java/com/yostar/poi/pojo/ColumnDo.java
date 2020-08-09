package com.yostar.poi.pojo;

import lombok.Data;

@Data
public class ColumnDo {
    private String name; // 列名
    private String key; // 是否主键
    private String type; // 类型
    private String extra; // 自增
    private String defaultValue; // 默认值
    private Boolean canNull; // 可以为空
    private String comment; // 备注

}
