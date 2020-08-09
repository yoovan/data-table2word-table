package com.yostar.poi.controller;

import com.yostar.poi.dto.ParamReqDto;
import com.yostar.poi.service.ChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@Api("数据表转换接口管理")
public class ChangeController {

    @Autowired
    private ChangeService changeService;

    @PostMapping("/generate")
    @ResponseBody
    @ApiOperation(
            value = "数据表转换",
            notes = "根据参数，将制定数据库的全部数据表转换为Word文档表格",
            httpMethod = "POST")
    public String generateWord(ParamReqDto paramReqDto) throws IOException {
        changeService.drawTable(paramReqDto);
        return "文档生成成功！保存路径为：" + paramReqDto.getPath() + paramReqDto.getFilename() + ".docx";
    }

}
