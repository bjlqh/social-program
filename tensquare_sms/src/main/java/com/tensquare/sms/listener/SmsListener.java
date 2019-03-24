package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;
    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @RabbitHandler
    public void sendSms(Map<String, String> map) throws ClientException {
        System.out.println("mobile:" + map.get("mobile"));
        System.out.println("code" + map.get("code"));
        String mobile = map.get("mobile");
        String code = map.get("code");
        smsUtil.sendSms(mobile, templateCode, signName, "{\"code\":\"" + code + "\"}");
    }
}
