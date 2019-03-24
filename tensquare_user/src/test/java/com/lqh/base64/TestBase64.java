package com.lqh.base64;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class TestBase64 {
    @Test
    public void test() throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode("HS256".getBytes());
        System.out.println(encode);
        //a->YQ== ,aa->YWE=,aaa->YWFh,aaaa->YWFhYQ==
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(encode);
        System.out.println(new String(bytes, 0, bytes.length));
    }
}
