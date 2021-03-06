package com.atlas.framework;

import com.atlas.framework.common.JsonSerializer;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-bean.xml")
public class VerifySignInterceptorTest extends TestCase {

    @org.junit.Test
    public void t1() throws Exception {

        User user = new User();
        user.setId(111);
        user.setName("哈哈");

        JsonSerializer<User> j = new JsonSerializer();
        String sss = j.serialize(user);
        System.out.println(sss);
        System.out.println(j.deserialize(sss, User.class).toString());


        System.out.println(Long.parseLong("55bb9b80", 16));//16转10

        System.out.println(Long.toHexString(1502359200));// 时间戳转16



        //http://127.0.0.1:10086/hello?d=oooo&a=1111&t=594b8008&sign=22


        String serverSign = new String(DigestUtils.md5Hex("/user/2t"+Long.toHexString(1502359200)));
        System.out.println(serverSign.toUpperCase());



        serverSign = new String(DigestUtils.md5Hex("/logint"+Long.toHexString(1502359200)));
        System.out.println(serverSign.toUpperCase());


        serverSign = new String(DigestUtils.md5Hex("/hellosKeyd97638447c074ebe9bf133274fe43b97t"+Long.toHexString(1502359200)));
        System.out.println(serverSign.toUpperCase());
    }


}
