package com.bjfu.wordlates.utils;

import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.controller.UpdateWordController;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBUtil {

    private static final String charset = "GBK";

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateWordController.class);

    /**
     * 拼接处所有数据的插入语句
     * @param tableName
     * @return
     * @throws IOException
     */
    public static String[] insertTable(String tableName) throws IOException {
        List<Integer> columnList = FILEUtil.readProper();
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(Constants.FILE_CSV_PATH)), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            iterator.next();
            List<String> sqlList = new ArrayList<>();
            while (iterator.hasNext()) {
                String[] tmp = iterator.next();
                String sql = "INSERT INTO " + tableName + "(cydd2, sheng, diqu, xiang, qylx, " +
                        "ypmc, spdl, spyl, spcyl, spxl," +
                        "ypxt, scrq, jyxm, jgpd, cydd1" +
                        ") VALUES (";
                for (Integer index : columnList) {
                    sql = sql + "'" + tmp[index] + "'" + ",";
                }
                sql = sql.substring(0, sql.length() - 1) + ")";
                sqlList.add(sql);
                LOGGER.info("SQL:[{}]", sql);
            }
            String[] sqlArray = sqlList.toArray(new String[0]);
            return sqlArray;
        }
    }
}
