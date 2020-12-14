package com.bjfu.wordlates.utils;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(POIUtil.class);

    public static void templateWrite(String filePath, String outFilePath, Map<String, Object> params) throws Exception {
        InputStream is = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        replaceInPara(doc, params);
        //替换表格里面的变量
        replaceInTable(doc, params);
        OutputStream os = new FileOutputStream(outFilePath);
        doc.write(os);
        LOGGER.info("word文件成功生成！");
        LOGGER.error("发生错误");
        close(os);
        close(is);
    }

    public static void templateWrite(String filePath, OutputStream os, Map<String, Object> params) throws Exception {
        InputStream is = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        replaceInPara(doc, params);
        //替换表格里面的变量
        replaceInTable(doc, params);
        doc.write(os);
        LOGGER.info("word文件成功生成！");
        close(os);
        close(is);
    }

    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        String runText = "";
        int fontSize = 0;//字体大小
        String fontFamily = "";//字体
        //UnderlinePatterns underlinePatterns = null;//下划线
        String color = "";
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            if (runs.size() > 0) {
                int j = runs.size();
                for (int i = 0; i < j; i++) {
                    XWPFRun run = runs.get(0);
                    fontSize = run.getFontSize();
                    fontFamily = run.getFontFamily();
                    color = run.getColor();
                    String i1 = run.toString();
                    runText += i1;
                    para.removeRun(0);
                }
            }
            matcher = matcher(runText);
            if (matcher.find()) {
                while ((matcher = matcher(runText)).find()) {
                    runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                }
                //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                //para.insertNewRun(0).setText(runText);//新增的没有样式
                XWPFRun run = para.createRun();
                run.setText(runText, 0);
                run.setFontSize(fontSize);
                run.setFontFamily(fontFamily);//字体
                run.setColor(color);
                //run.setFontSize(16);//字体大小
                //run.setBold(true); //加粗
                //run.setColor("FF0000");
                //默认：宋体（wps）/等线（office2016） 5号 两端对齐 单倍间距
                //run.setBold(false);//加粗
                //run.setCapitalized(false);//我也不知道这个属性做啥的
                //run.setCharacterSpacing(5);//这个属性报错
                //run.setColor("BED4F1");//设置颜色--十六进制
                //run.setDoubleStrikethrough(false);//双删除线
                //run.setEmbossed(false);//浮雕字体----效果和印记（悬浮阴影）类似
                //run.setFontFamily("宋体");//字体
                //run.setFontFamily("华文新魏", FontCharRange.cs);//字体，范围----效果不详
                //run.setFontSize(14);//字体大小
                //run.setImprinted(false);//印迹（悬浮阴影）---效果和浮雕类似
                //run.setItalic(false);//斜体（字体倾斜）
                //run.setKerning(1);//字距调整----这个好像没有效果
                //run.setShadow(true);//阴影---稍微有点效果（阴影不明显）
                //run.setSmallCaps(true);//小型股------效果不清楚
                //run.setStrike(true);//单删除线（废弃）
                //run.setStrikeThrough(false);//单删除线（新的替换Strike）
                //run.setSubscript(VerticalAlign.SUBSCRIPT);//下标(吧当前这个run变成下标)---枚举
                //run.setTextPosition(20);//设置两行之间的行间距
                //run.setUnderline(UnderlinePatterns.DASH_LONG);//各种类型的下划线（枚举）
                //run0.addBreak();//类似换行的操作（html的  br标签）
                //run0.addTab();//tab键
                //run0.addCarriageReturn();//回车键
                //注意：addTab()和addCarriageReturn() 对setText()的使用先后顺序有关：比如先执行addTab,再写Text这是对当前这个Text的Table，反之是对下一个run的Text的Tab效果
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setBarData(XWPFChart chart, String chartTitle, String[] series, String[] categories, Double[] values1, Double[] values2) {
        final List<XDDFChartData> data = chart.getChartSeries();
        final XDDFBarChartData bar = (XDDFBarChartData) data.get(0);

        final int numOfPoints = categories.length;
        final String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        final String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
        final String valuesDataRange2 = chart.formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
        final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 0);
        final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory.fromArray(values1, valuesDataRange, 1);
        values1[6] = 16.0; // if you ever want to change the underlying data
        final XDDFNumericalDataSource<? extends Number> valuesData2 = XDDFDataSourcesFactory.fromArray(values2, valuesDataRange2, 2);

        XDDFChartData.Series series1 = bar.getSeries(0);
        series1.replaceData(categoriesData, valuesData);
        series1.setTitle(series[0], chart.setSheetTitle(series[0], 0));
        XDDFChartData.Series series2 = bar.addSeries(categoriesData, valuesData2);
        series2.setTitle(series[1], chart.setSheetTitle(series[1], 1));

        chart.plot(bar);
        chart.setTitleText(chartTitle); // https://stackoverflow.com/questions/30532612
        chart.setTitleOverlay(false);
    }

    private static void setColumnData(XWPFChart chart, String chartTitle) {
        // Series Text
        List<XDDFChartData> series = chart.getChartSeries();
        XDDFBarChartData bar = (XDDFBarChartData) series.get(0);

        // in order to transform a bar chart into a column chart, you just need to change the bar direction
        bar.setBarDirection(BarDirection.COL);

        // looking for "Stacked Bar Chart"? uncomment the following line
        // bar.setBarGrouping(BarGrouping.STACKED);

        // additionally, you can adjust the axes
        bar.getCategoryAxis().setOrientation(AxisOrientation.MAX_MIN);
        bar.getValueAxes().get(0).setPosition(AxisPosition.TOP);
    }

}
