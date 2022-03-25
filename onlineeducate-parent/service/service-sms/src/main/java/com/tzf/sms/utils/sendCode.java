package com.tzf.sms.utils;


import com.tzf.commonutil.HttpUtils;
import com.tzf.commonutil.RandomUtil;
import com.tzf.commonutil.Resouce;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class sendCode {


    public static Resouce sendMessage(
            String host, String path, String method, String appcode, String mobile,
            String smsSignId, String templateId) {
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        String code = RandomUtil.getSixBitRandom();
        String param = "**code**:" + code + ",**minute**:5";
        querys.put("param", param);
        querys.put("smsSignId", smsSignId);// 2e65b1bb3d054466b82f0c9d125465e2
        querys.put("templateId", templateId);// 908e94ccf08b4476ba6c876d13f084ad
        Map<String, String> bodys = new HashMap<String, String>();

        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {
                return Resouce.ok().data("code", code);
            }
            return Resouce.error().message(reasonPhrase);

        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }
}
