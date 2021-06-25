package com.cq.hwh.util;


import java.io.Serializable;

public class IpUtil implements Serializable {

    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        IpUtil.remoteAddr.set(remoteAddr);
    }

}
