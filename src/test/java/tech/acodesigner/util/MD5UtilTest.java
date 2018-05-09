package tech.acodesigner.util;

import org.junit.Test;

import javax.annotation.Resource;

public class MD5UtilTest {




    @Test
    public void encoderPassword() throws Exception{

        String password = MD5Util.encoderPassword("123456");
        System.out.println(password);
        //123456 的MD5 ：4QrcOUm6Wau+VuBX8g+IPg==

    }
}