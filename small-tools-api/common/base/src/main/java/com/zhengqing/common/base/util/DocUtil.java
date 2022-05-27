package com.zhengqing.common.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * <p>
 * 文档工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/2/25 15:56
 */
public class DocUtil {

    /**
     * 替换word文档内容
     *
     * @param wordFileBytes:
     *            word文件字节码
     * @param templateDataMap:
     *            模板数据模型
     * @return 替换后新的word字节码
     * @author zhengqingya
     * @date 2021/2/25 15:57
     */
    public static byte[] replaceWordContent(byte[] wordFileBytes, Map<String, String> templateDataMap) {
        try {
            InputStream wordIs = new ByteArrayInputStream(wordFileBytes);
            XWPFDocument document = new XWPFDocument(wordIs);
            // 替换word内容
            handleWord(document, templateDataMap);
            // 二进制OutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 文档写入流
            document.write(baos);
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[1];
    }

    /**
     * 替换word文档内容
     *
     * @param srcPath:
     *            word路径
     * @param destPath:
     *            替换后的word生成路径
     * @param templateDataMap:
     *            模板数据模型
     * @return void
     * @author zhengqingya
     * @date 2021/2/25 15:57
     */
    public static void replaceWordContent(String srcPath, String destPath, Map<String, String> templateDataMap) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            // 替换word内容
            handleWord(document, templateDataMap);
            // 写入指定文件
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换word内容
     *
     * @param document:
     *            文档
     * @param templateDataMap:
     *            模板数据模型
     * @return void
     * @author zhengqingya
     * @date 2021/2/25 16:24
     */
    private static void handleWord(XWPFDocument document, Map<String, String> templateDataMap) {
        // 替换段落中的指定内容
        handleText(document, templateDataMap);
        // 替换表格中的指定内容
        handleTable(document, templateDataMap);
    }

    /**
     * 替换段落中的指定内容
     *
     * @param document:
     *            文档
     * @param templateDataMap:
     *            模板数据模型
     * @return void
     * @author zhengqingya
     * @date 2021/2/25 16:24
     */
    private static void handleText(XWPFDocument document, Map<String, String> templateDataMap) {
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = (XWPFParagraph)itPara.next();
            Set<String> set = templateDataMap.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                List<XWPFRun> run = paragraph.getRuns();
                for (int i = 0; i < run.size(); i++) {
                    if (run.get(i).getText(run.get(i).getTextPosition()) != null
                        && run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
                        /**
                         * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始 就可以把原来的文字全部替换掉了
                         */
                        run.get(i).setText(templateDataMap.get(key), 0);
                    }
                }
            }
        }
    }

    /**
     * 替换表格中的指定内容
     *
     * @param document:
     *            文档
     * @param templateDataMap:
     *            模板数据模型
     * @return void
     * @author zhengqingya
     * @date 2021/2/25 16:24
     */
    private static void handleTable(XWPFDocument document, Map<String, String> templateDataMap) {
        Iterator<XWPFTable> itTable = document.getTablesIterator();
        while (itTable.hasNext()) {
            XWPFTable table = (XWPFTable)itTable.next();
            int count = table.getNumberOfRows();
            for (int i = 0; i < count; i++) {
                XWPFTableRow row = table.getRow(i);
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    for (Entry<String, String> e : templateDataMap.entrySet()) {
                        if (cell.getText().equals(e.getKey())) {
                            cell.removeParagraph(0);
                            cell.setText(e.getValue());
                        }
                    }
                }
            }
        }
    }

}
