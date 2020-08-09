package com.yostar.poi.mapper;

import com.yostar.poi.dto.ParamReqDto;
import com.yostar.poi.dto.TableDetailReqDto;
import com.yostar.poi.pojo.TableDo;

import java.util.List;
import java.util.Map;

public interface ChangeMapper {

    List<TableDo> getTables(ParamReqDto paramReqDto);

    List<Map<String, String>> getTableDetail(TableDetailReqDto tableDetailReqDto);
}
