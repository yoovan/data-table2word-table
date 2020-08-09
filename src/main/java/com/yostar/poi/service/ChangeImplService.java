package com.yostar.poi.service;

import com.yostar.poi.dto.ParamReqDto;
import com.yostar.poi.dto.TableDetailReqDto;
import com.yostar.poi.mapper.ChangeMapper;
import com.yostar.poi.pojo.TableDo;
import com.yostar.poi.utils.TableUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChangeImplService implements ChangeService {

    @Autowired
    private ChangeMapper changeMapper;

    @Override
    public void drawTable(ParamReqDto paramReqDto) throws IOException {
        List<TableDo> tables = changeMapper.getTables(paramReqDto);
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("共生成了" + tables.size() + "张表");
        run.setFontSize(14);
        run.addBreak();
        System.out.println(">>>>>>>>>>>>>建立表>>>>>>>>>>>>>");
        XWPFTable table = TableUtils.createTable(document, tables.size() + 1, 2, "表名", "备注");
        TableUtils.fillData(table, tables, "name", "comment");
        document.createParagraph().createRun().addBreak(BreakType.PAGE);
        for (TableDo tbl : tables) {
            XWPFParagraph p = document.createParagraph();
            XWPFRun r = p.createRun();
            r.setText("表名：" + tbl.getName());
            r.addBreak();
            r.setText("描述：" + tbl.getDescription());
            r.addBreak();
            TableDetailReqDto detailReqDto = new TableDetailReqDto();
            BeanUtils.copyProperties(paramReqDto, detailReqDto);
            detailReqDto.setTableName(tbl.getName());
            List<Map<String, String>> detail = changeMapper.getTableDetail(detailReqDto);
            r.setText("共" + detail.size() + "个字段");
            r.setFontSize(14);
            r.addBreak();
            XWPFTable table1 = TableUtils.createTable(document,
                    detail.size() + 1, detail.get(0).size(),
                    "字段名", "主键", "类型", "默认值", "是否为空", "备注");
            TableUtils.fillTableDetail(table1, detail, "name", "key", "type", "defaultValue", "canNull", "comment");
        }
        System.out.println(">>>>>>>>>>>>>正在生成表>>>>>>>>>>>>>");
        if (StringUtils.isEmpty(paramReqDto.getFilename())) {
            paramReqDto.setFilename("trace.docx");
        }
        FileOutputStream outputStream = new FileOutputStream(paramReqDto.getPath() + paramReqDto.getFilename() + ".docx");
        document.write(outputStream);
        System.out.println(">>>>>>>>>>>>>生成成功>>>>>>>>>>>>>");
        outputStream.close();
    }

}
