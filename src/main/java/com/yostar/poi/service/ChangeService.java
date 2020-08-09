package com.yostar.poi.service;

import com.yostar.poi.dto.ParamReqDto;

import java.io.IOException;

public interface ChangeService {

    void drawTable(ParamReqDto paramReqDto) throws IOException;

}
