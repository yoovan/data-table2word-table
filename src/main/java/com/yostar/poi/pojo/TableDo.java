package com.yostar.poi.pojo;

import lombok.Data;

import java.util.List;

@Data
public class TableDo {

    private String name;
    private String description;
    private List<ColumnDo> columnDoList;

}
