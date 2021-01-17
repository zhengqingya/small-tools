package com.zhengqing.tool.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.util.MyFileUtil;
import com.zhengqing.tool.db.model.bo.StDbTableColumnBO;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 创建word文档
 * </p>
 *
 * @author : zhengqing
 * @description : 步骤: 1、建立文档 2、创建一个书写器 3、打开文档 4、向文档中写入数据 5、关闭文档
 * @date : 2019/11/8 15:45
 */
@Slf4j
public class TableToWordUtil {

    /**
     * 生成word文档
     *
     * @param tableInfoList：该数据库下所有表+字段信息
     * @param filePath：生成文件地址
     * @param title:文件内容标题
     * @return: void
     */
    public static void toWord(List<StDbTableColumnBO> tableInfoList, String filePath, String title) {
        log.debug("生成word文档地址：【{}】", filePath);
        Document document = new Document(PageSize.A4);
        try {
            // 创建文件
            MyFileUtil.touch(filePath);

            // 写入文件信息
            RtfWriter2.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Paragraph ph = new Paragraph();
            Font f = new Font();
            Paragraph p = new Paragraph(title, new Font(Font.NORMAL, 24, Font.BOLDITALIC, new Color(0, 0, 0)));
            p.setAlignment(1);
            document.add(p);
            ph.setFont(f);
            for (int i = 0; i < tableInfoList.size(); i++) {
                StDbTableColumnBO tableInfo = tableInfoList.get(i);
                String table_name = tableInfoList.get(i).getTableName();
                String table_comment = tableInfo.getTableComment();
                List<StDbTableColumnBO.ColumnInfo> fileds = tableInfo.getColumnInfoList();
                String all = "" + (i + 1) + " 表名称:" + table_name + "（" + table_comment + "）";
                Table table = new Table(6);

                document.add(new Paragraph(""));

                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);

                // 添加表头的元素，并设置表头背景的颜色
                Color chade = new Color(176, 196, 222);

                Cell cell = new Cell("编号");
                addCell(table, cell, chade);
                cell = new Cell("字段名");
                addCell(table, cell, chade);
                cell = new Cell("类型");
                addCell(table, cell, chade);
                cell = new Cell("是否非空");
                addCell(table, cell, chade);
                cell = new Cell("是否主键");
                addCell(table, cell, chade);
                cell = new Cell("注释");
                addCell(table, cell, chade);

                table.endHeaders();

                // 表格的主体
                for (int k = 0; k < fileds.size(); k++) {
                    addContent(table, cell, (k + 1) + "");
                    addContent(table, cell, fileds.get(k).getColumnName());
                    addContent(table, cell, fileds.get(k).getColumnType());
                    addContent(table, cell, fileds.get(k).isIfNullAble() ? "否" : "是");
                    addContent(table, cell, fileds.get(k).isIfPrimaryKey() ? "是" : "否");
                    addContent(table, cell, fileds.get(k).getColumnComment());
                }
                Paragraph pheae = new Paragraph(all);
                // 写入表说明
                document.add(pheae);
                // 生成表格
                document.add(table);
            }
            document.close();
        } catch (Exception e) {
            throw new MyException("生成word异常：" + e.getMessage());
        }
    }

    /**
     * 添加表头到表格
     *
     * @param table
     * @param cell
     * @param chade
     */
    private static void addCell(Table table, Cell cell, Color chade) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(chade);
        table.addCell(cell);
    }

    /**
     * 添加内容到表格
     *
     * @param table
     * @param content
     */
    private static void addContent(Table table, Cell cell, String content) {
        cell = new Cell(content);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

}
