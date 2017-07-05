package com.atlas.core.common.web;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 *
 *
 */
public class HttpUtils {
    /**
     * 获取真实来源IP
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = head(request, "X-Forwarded-For");
        if (ip != null) {
            int index = ip.indexOf(',');
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            return (index != -1) ? ip.substring(0, index) : ip;
        }

        ip = head(request, "X-Real-IP");
        if (ip != null && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "unkown" : ip;
    }

    private static String head(HttpServletRequest req, String s) {
        return req.getHeader(s);
    }

}
