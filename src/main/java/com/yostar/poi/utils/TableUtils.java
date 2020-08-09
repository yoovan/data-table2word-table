package com.yostar.poi.utils;

import com.yostar.poi.pojo.TableDo;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class TableUtils {

    /**
     * 创建表格，将表格第一行设置为标题
     * @param doc
     * @param rows
     * @param cols
     * @param titles
     * @return
     */
    public static XWPFTable createTable(XWPFDocument doc, int rows, int cols, String... titles) {
        XWPFTable table = doc.createTable(rows, cols);
        // 设置标题
        setHeaderTitle(table, titles);
        return table;
    }

    /**
     * 设置标题和标题单元格的背景颜色
     * @param table
     * @param titles
     */
    public static void setHeaderTitle(XWPFTable table, String... titles) {
        XWPFTableRow row = table.getRow(0);
        for (int i = 0; i < titles.length; i++) {
            XWPFTableCell cell = row.getCell(i) == null ? row.createCell() : row.getCell(i);
            TableUtils.setBackgroundColor(cell, "9CC2FB");// 设置背景颜色
            setValue(cell, titles[i], true);
        }
    }

    /**
     * 使用XWPFTableCell设置列宽
     *
     * @param cell
     * @param width
     */
    public static void setColumnsWidth(XWPFTableCell cell, int width) {
        CTTcPr ctTcPr = cell.getCTTc().addNewTcPr();
        ctTcPr.addNewTcW().setW(new BigInteger(String.valueOf(width))); // 设置列宽
    }

    /**
     * 填充数据表目录的值
     * @param table
     * @param tables
     * @param keys
     */
    public static void fillData(XWPFTable table, List<TableDo> tables, String... keys) {
        for (int i = 0; i < tables.size(); i++) {
            XWPFTableRow row = table.getRow(i + 1) == null ? table.createRow() : table.getRow(i + 1);
            XWPFTableCell cell = row.getCell(0) == null ? row.createCell() : row.getCell(0);
            setValue(cell, tables.get(i).getName(), false);
            XWPFTableCell cell2 = row.getCell(1) == null ? row.createCell() : row.getCell(1);
            setValue(cell2, tables.get(i).getDescription(), false);
        }
    }

    /**
     * 填充每个数据表的值
     * @param table
     * @param columns
     * @param keys
     */
    public static void fillTableDetail(XWPFTable table, List<Map<String, String>> columns, String... keys) {
        for (int i = 0; i < columns.size(); i++) {
            XWPFTableRow row = table.getRow(i + 1) == null ? table.createRow() : table.getRow(i + 1);
            int index = 0;
            for (String key : keys) {
                XWPFTableCell cell = row.getCell(index) == null ? row.createCell() : row.getCell(index);
                TableUtils.setValue(cell, columns.get(i).get(key), false);
                index++;
            }
        }
    }

    /**
     * 为单元格赋值 设置列宽、居中显示
     * @param cell 单元格
     * @param text 单元格值
     * @param isBold 是否加粗
     */
    public static void setValue(XWPFTableCell cell, String text, boolean isBold) {
        TableUtils.setColumnsWidth(cell, 2000); // 设置列宽
        XWPFParagraph p = cell.getParagraphs().get(0);
        p.setAlignment(ParagraphAlignment.CENTER); // 居中显示
        XWPFRun run = p.createRun();
        run.setText(text);
        run.setBold(isBold);
        // run.setColor("C71585"); // 设置字体颜色
    }

    /**
     * 设置单元格的背景颜色
     *
     * @param cell
     * @param color
     */
    public static void setBackgroundColor(XWPFTableCell cell, String color) {
        CTTcPr ctTcPr = cell.getCTTc().addNewTcPr();
        ctTcPr.addNewShd().setFill(color); // 设置背景颜色
    }

    /**
     * 设置CTTblGridCol列宽
     *
     * @param table
     * @param widths
     */
    private void setColumnsWidth(XWPFTable table, int... widths) {
        CTTbl ctTbl = table.getCTTbl();
        CTTblGrid ctTblGrid = ctTbl.getTblGrid() == null ? ctTbl.addNewTblGrid() : ctTbl.getTblGrid();
        for (int width : widths) {
            CTTblGridCol ctTblGridCol = ctTblGrid.addNewGridCol();
            ctTblGridCol.setW(new BigInteger(String.valueOf(width)));
        }
    }

}
