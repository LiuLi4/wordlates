package com.bjfu.wordlates.service.impl;

import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.DATAUtil;
import com.bjfu.wordlates.utils.FILEUtil;
import com.bjfu.wordlates.utils.JSONUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Service("testUserService")
public class ReportServiceImpl implements ReportService {


    @Override
    public String creatWordByTemplate(HttpServletResponse res) throws Exception {
        String fileName = Constants.FILE_OUT_NAME;
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = res.getOutputStream();

        // 数据
        Map<String, List<String>> dataMap = FILEUtil.readCSV(Constants.FILE_CSV_PATH);

        /************************生成自定义标签的值****************/
        Map<String, Object> params =new HashMap<String, Object>() {{
            put("Cyear","2019");
            put("Cfood","xx食品");
            put("Cloc","xx省xx单位");
            put("Clocs","北京林业大学");
            put("Cp","XXX");
            put("Cps","xxx,yyy");
            put("Cdata",DATAUtil.newDate());
        }};

        /******************自定义复用变量*******************/
        //不合格商品的详细情况
        Map unquality=new HashMap();
        unquality=DATAUtil.unqualifiedMap(dataMap.get("样品名称*"),dataMap.get("检验项目*"),dataMap.get("结果判定*"));
        //大类不合格商品的详细情况
        Map<String,List<Integer>> mapSort= new HashMap();
        mapSort=DATAUtil.tableSort(dataMap.get("食品大类"),dataMap.get("结果判定*"));
        //采样地点_2 的详细情况
        Map<String,Integer> mapSampling= new HashMap();
        mapSampling=DATAUtil.chartSampling(dataMap.get("抽样地点_2*"));
        //采样地区的详细情况
        Map<String,Integer> mapLocation=new HashMap();
        mapLocation=DATAUtil.tableSort(dataMap.get("地区(市、州、盟)"),dataMap.get("结果判定*"));
        //样本数目
        int foodNum=DATAUtil.newSize(dataMap.get("样品名称*"));
        //不合格样本数目
        int unqualityNum=DATAUtil.newSize(DATAUtil.returnKey(unquality));
        //食品大类
        List sortList=DATAUtil.newList(dataMap.get("食品大类"));

        /***********************分析数据的值****************/
        params.put("Aloc",DATAUtil.newString(dataMap.get("地区(市、州、盟)")));
        params.put("Alocn",DATAUtil.newSize(dataMap.get("地区(市、州、盟)")));
        params.put("Asort",DATAUtil.newString(dataMap.get("食品大类")));
        params.put("Asortn",DATAUtil.newSize(dataMap.get("食品大类")));
        params.put("Afood",DATAUtil.newString(dataMap.get("样品名称*")));
        params.put("Ameth",DATAUtil.newString(dataMap.get("检验项目*")));
        params.put("Amethn",DATAUtil.newSize(dataMap.get("检验项目*")));
        params.put("Afoodn",foodNum);
        params.put("Adan",DATAUtil.newString(DATAUtil.returnKey(unquality)));
        params.put("Adann",unqualityNum);
        params.put("Arate",DATAUtil.culQuality(foodNum,unqualityNum));

        /**************************表格****************/
        //根据食品品种的分析表格
        RowRenderData headerSort = RowRenderData.build(new TextRenderData("ff0000","食品品种"),
                new TextRenderData("ff0000","抽查批次"),new TextRenderData("ff0000","不合格批次"),
                new TextRenderData("ff0000","合格率"));
        Set<Map.Entry<String,List<Integer>>> eSet  =  mapSort.entrySet();
        Iterator<Map.Entry<String,List<Integer>>> it = eSet.iterator();
        List<RowRenderData> listSort=new LinkedList<RowRenderData>();
        while(it.hasNext()) {
            Map.Entry<String, List<Integer>> i = it.next();
            int i0=i.getValue().get(0);
            int i1=i.getValue().get(1);
            RowRenderData l=RowRenderData.build(i.getKey(),i0+"",i1+"",DATAUtil.culQuality(i0,i1));
            listSort.add(l);
        }
        params.put("table",new MiniTableRenderData(headerSort,listSort));

        /********************图表*********************/
        //饼图，采样地点_2的分布
        ChartSingleSeriesRenderData pieSampling = new ChartSingleSeriesRenderData();
        pieSampling.setChartTitle("采样地点比例图");
        pieSampling.setCategories(DATAUtil.returnKey(mapSampling).toArray(new String[0]));
        pieSampling.setSeriesData(new SeriesRenderData("采样地点", (Number[]) DATAUtil.returnValue(mapSampling).toArray(new Number[0])));
        params.put("pieSampling",pieSampling);

        //条形图，采样地区不合格分布
        ChartMultiSeriesRenderData barLocation = new ChartMultiSeriesRenderData();
        barLocation.setChartTitle("地区合格率情况图");
        barLocation.setCategories(DATAUtil.returnKey(mapLocation).toArray(new String[0]));
        List<SeriesRenderData> seriesRenderData = new ArrayList<>();
        seriesRenderData.add(new SeriesRenderData("抽检批次",(Number[]) DATAUtil.returnValues(mapLocation,0).toArray(new Number[0])));
        seriesRenderData.add(new SeriesRenderData("不合格批次",(Number[]) DATAUtil.returnValues(mapLocation,1).toArray(new Number[0])));
        barLocation.setSeriesDatas(seriesRenderData);
        params.put("barLocation",barLocation);

        // 模板word文件相对路径
        String wordSrcPath = Constants.FILE_TEMPLATE_PATH;
        //POIUtil.templateWrite(wordSrcPath, os, params);

        //word生成核心代码
        XWPFTemplate template=XWPFTemplate.compile(wordSrcPath).render(params);
        template.write(os);
        os.close();
        template.close();

        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }
}
