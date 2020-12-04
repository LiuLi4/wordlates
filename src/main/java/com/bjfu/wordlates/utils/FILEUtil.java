package com.bjfu.wordlates.utils;

import com.bjfu.wordlates.constant.Constants;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.*;

public class FILEUtil {

    private static final String charset = "GBK";

    public static void write(String target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf, 0, len);
        }
        os.flush();
        os.close();
    }

    /**
     * 生成随机文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 读取配置文件中的属性
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<Integer> readProper(String filePath) throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(in);
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        List<Integer> column = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : entrySet) {
            column.add(Integer.parseInt((String) entry.getValue()) - 1);
        }
        Collections.sort(column);
        return column;
    }

    /**
     * 读取CSV数据
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Iterator<String[]> readCSV(String filePath) throws IOException {
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(filePath)), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            iterator.next();
            return iterator;
        }
    }
}
