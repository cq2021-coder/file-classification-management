package com.cq.hwh.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 路径工具包
 *
 * @author 程崎
 * @date 2021/06/26
 */
@Configuration
public class PathUtil {
    //获取系统文件分隔符
    public static String seperator = System.getProperty("file.separator");
    private static String winPath;
    private static String linuxPath;
    private static String winTTCPath;
    private static String linuxTTCPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }


    @Value("${win.ttc.path}")
    public void setWinTTCPath(String winTTCPath) {
        PathUtil.winTTCPath = winTTCPath;
    }

    @Value("${linux.ttc.path}")
    public void setLinuxTTCPath(String linuxTTCPath) {
        PathUtil.linuxTTCPath = linuxTTCPath;
    }

    //basePath路径处理(返回图片根路径)
    public static String getResultPath() {
        //获取系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            //windows系统路径
            basePath = winPath;
        } else {
            //其他系统路径
            basePath = linuxPath;
        }
        //将分隔符替换成该系统的分隔符
        basePath = basePath.replace("/", seperator);
        return basePath;
    }


    public static void main(String[] args) {
        System.out.println(getResultPath());
    }
}
