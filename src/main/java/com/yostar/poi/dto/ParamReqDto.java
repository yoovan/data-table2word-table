package com.yostar.poi.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("入参请求体")
public class ParamReqDto {
    @ApiModelProperty("主机地址")
    private String host;
    @ApiModelProperty("数据库端口")
    private String port;
    @ApiModelProperty("数据库用户名")
    private String username;
    @ApiModelProperty("数据库密码")
    private String password;
    @ApiModelProperty("数据库名称")
    private String dataSource;
    @ApiModelProperty("生成文件的保存地址")
    private String path;
    @ApiModelProperty("生成的文件名")
    private String filename;
}
