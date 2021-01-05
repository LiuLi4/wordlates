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

    public static List<Integer> readProper() throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.FILE_PROPER_PATH);
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

    public static Set<Map.Entry<Object, Object>> readProper(String filePath) throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(new InputStreamReader(in, "UTF-8"));
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        return entrySet;
    }

    /**
     * 从CSV文件中获得我们需要的数据
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> readCSV(String filePath) throws IOException {
        Map<String, List<String>> dataMap = new HashMap<>();
        Set<Map.Entry<Object, Object>> columnSet = readProper(Constants.FILE_PROPER_PATH);
        // 初始化dataMap
        for (Map.Entry<Object, Object> entry : columnSet) {
            dataMap.put((String) entry.getKey(), new LinkedList<String>());
        }
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(filePath)), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                String[] tmp = iterator.next();
                for (Map.Entry<Object, Object> entry : columnSet) {
                    String key = (String) entry.getKey();
                    Integer value = Integer.parseInt((String) entry.getValue()) - 1;
                    dataMap.get(key).add(tmp[value]);
                }
            }
        }
        return dataMap;
    }

    /**
     * 从CSV文件中获得我们需要的数据
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> readCSVRow(String filePath) throws IOException {
        Map<String, List<String>> dataMap = new HashMap<>();
        Set<Map.Entry<Object, Object>> columnSet = readProper(Constants.FILE_PROPER_PATH);
        // 初始化dataMap
        for (Map.Entry<Object, Object> entry : columnSet) {
            dataMap.put((String) entry.getKey(), new LinkedList<String>());
        }
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(filePath)), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                String[] tmp = iterator.next();
                for (Map.Entry<Object, Object> entry : columnSet) {
                    String key = (String) entry.getKey();
                    Integer value = Integer.parseInt((String) entry.getValue()) - 1;
                    dataMap.get(key).add(tmp[value]);
                }
            }
        }
        return dataMap;
    }

}
