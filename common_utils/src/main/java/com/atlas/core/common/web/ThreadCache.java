package com.atlas.core.common.web;

import java.util.TimeZone;

/**
 * ThreadLocal实现主要应用于web session中
 *
 *
 * FIXME ThreadLocal 详解byrenfei  哈哈哈
 *
 * 引用只需要定义static final ThreadLocal类型即可,每一个Thread都会保存ThreadLocal.ThreadLocalMap的副本,所以如此做也不会引起网上所说的内存泄露问题,
 * 因为这是一个WeakReference的Map(一个弱引用),而弱引用是会被GC销毁的,千万别被ThreadLocal static final所迷惑.
 *
 * 在调用get方法时是通过从ThreadLocalMap里面获取以当前Thread为key的map属性(set反推同理),看源码
 *
 *
 *
 *
 * ::::::::::::::::::分割线::::::::::::::::::::
 *
 *
 * @author renfei
 */
public class ThreadCache {
//    @SuppressWarnings("unused")
//    private static final Logger logger = LoggerFactory.getLogger(ThreadCache.class);

    private static final ThreadLocal<ThreadContext> cache = new ThreadLocal<ThreadContext>() {
        protected ThreadContext initialValue() {
            return new ThreadContext();
        }
    };


    public static void setUid(Integer uid) {
        cache.get().uid = uid;
    }

    public static void setURI(String URI) {
        cache.get().URI = URI;
    }


    public static void setLocale(String locale) {
        cache.get().locale = locale;
    }

    public static String getLocale() {
        return cache.get().locale;
    }


    private static class ThreadContext {
        long startTime;
        String locale;
        Integer uid;
        String URI;
        String ip;
        TimeZone timeZone;
    }


    public static Integer getUid() {
        return cache.get().uid;
    }


    public static String getURI() {
        return cache.get().URI;
    }


    /**
     * 设置monitor开始时间，用作耗时统计
     */
    public static void setStartTime() {
        cache.get().startTime = System.currentTimeMillis();
    }

    public static long getStartTime() {
        return cache.get().startTime;
    }

    public static void release() {
        cache.remove();
    }

    /**
     * ip设置
     *
     * @param ip
     */
    public static void setIp(String ip) {
        cache.get().ip = ip;
    }

    /**
     * ip获取
     *
     * @return
     */
    public static String getIp() {
        return cache.get().ip;
    }

    public static TimeZone getTimeZone() {
        return cache.get().timeZone;
    }

    public static void setTimeZone(TimeZone timeZone) {
        cache.get().timeZone = timeZone;
    }
}