package com.atlas.framework.web;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 提供用于验证url有效性的连接器,url防盗链
 *
 *
 * 签名算法如下：
 * 1.对访问路径和所有请求参数进行字典升序排列；
 * 2.t 参与sign的计算
 * 3. 将以上排序后的参数表进行字符串连接，如: 访问路径+key1value1key2value2key3value3...keyNvalueN；
 *    http://atlas.com/user/22222?a=11111&c=22222     =====>      /user/22222a11111c22222
 *    因为是restful 所以增加对访问路径的计算
 *
 * 4. 对该字符串进行MD5计算；
 *  FIXME 这里的加密方法可以使用RSA进行非对称加密,这样更加保险,MD5方便测试(注意加密后长度URL是有长度限制的),也可以使用sha-1
 * 5. 转换为全大写形式后即获得签名串
 *
 * Created by renfei on 17/6/22.
 */
public class VerifySignInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(VerifySignInterceptor.class);

    private long urlExpire = DateUtils.MILLIS_PER_MINUTE * 5;//url有效时间
    private String signKey = "sign"; //url签名

    private String timeKey = "t";//url从客户端生成的url访问有效截止时间,按 unix_time 的 16进制小写形式表示

    public long getUrlExpire() {
        return urlExpire;
    }

    public void setUrlExpire(long urlExpire) {
        this.urlExpire = urlExpire;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String appSign = request.getParameter(signKey);
        String t = request.getParameter(timeKey);


        // FIXME 这里也可以根据客户端异常返回码针对sign为null的情况提示错误信息给客户端
        Assert.notNull(appSign, "--sign is null.");

        // 获取request对象中的所有参数
        Map<String, String[]> temp = request.getParameterMap();
        if (temp.size() > 30) {
            // 根据我们的业务设计，参数的个数一般不会超过30个，如果超过，除非是攻击，或者恶意访问，直接拒绝
            // FIXME 这里也可以根据客户端异常返回码针对sign为null的情况提示错误信息给客户端
            return false;
        } else {
            //先验证url是否已经失效
            long time = Long.parseLong(t, 16);
            long now = System.currentTimeMillis() / DateUtils.MILLIS_PER_SECOND;
            if(now  > time){//已经超时失效
                // FIXME 这里也可以根据客户端异常返回码针对sign为null的情况提示错误信息给客户端
                return false;
            }else{

                String path = request.getRequestURI();

                // 对参数名进行字典排序
                String[] keyArray = temp.keySet().toArray(new String[0]);
                Arrays.sort(keyArray);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(path);
                for (String key : keyArray) {
                    // 去掉sign参数本身，因为sign参数不参与签名运算
                    if(key.equals(signKey)){
                        continue;
                    }

                    stringBuilder.append(key).append(temp.get(key)[0]);

                    logger.debug("测试日志：" + key + ":" + temp.get(key));
                }

                String serverSign = DigestUtils.md5Hex(stringBuilder.toString());//使用apache common标准类库中的md5方法

                logger.debug("测试日志：正确的sign：" + serverSign);

                if (appSign.equalsIgnoreCase(serverSign)) {
                    // 太棒了，签名验证成功，去下一个逻辑
                    // FIXME 此处还可以再次验证参数的合法性，然后把参数全部弄好，传递到request setAttribute

                    logger.debug("牛逼牛逼,签名验证成功!");
                } else {

                    // FIXME 这里也可以根据客户端异常返回码针对sign为null的情况提示错误信息给客户端

                    logger.debug("--invalid sign");
                    return false;
                }
            }


        }

        return true;
    }
}
