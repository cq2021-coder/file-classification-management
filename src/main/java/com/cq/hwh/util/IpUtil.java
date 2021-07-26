package com.cq.hwh.util;


import java.io.Serializable;

/**
 * IP工具包
 *
 * @author 程崎
 * @date 2021/06/23
 */
public class IpUtil implements Serializable {

    /**
     * 远程addr
     */
    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    /**
     * 获取远程addr
     *
     * @return {@link String}
     */
    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    /**
     * 设置远程addr
     *
     * @param remoteAddr 远程addr
     */
    public static void setRemoteAddr(String remoteAddr) {
        IpUtil.remoteAddr.set(remoteAddr);
    }

}
