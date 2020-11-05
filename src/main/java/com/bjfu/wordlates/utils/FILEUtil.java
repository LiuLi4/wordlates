package com.bjfu.wordlates.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FILEUtil {

    public static void write(String target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf,0,len);
        }
        os.flush();
        os.close();
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
