package com.spotbugs.translate.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author zouJianJun
 * @date 2024/12/2
 * @desc 调用ollama的qwen2.5大模型
 * 中文文档地址：https://github.com/datawhalechina/handy-ollama/blob/main/docs/C4/1.%20Ollama%20API%20%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97.md
 * https://www.53ai.com/news/qianyanjishu/737.html
 */
public class Qwen2TransUtil {
    
    private static String API_URL = "http://localhost:11434/api/generate";

    private static HttpRequest REQUEST = HttpRequest.post(API_URL).header("Content-Type", "application/json");

    public static String trans(String text){
        JSONObject data = new JSONObject();
        data.set("model", "qwen2.5");
        data.set("prompt", text);
        //data.set("system", "Translate the following into chinese and only show me the translated");
        data.set("system", "You are a translation tool. Please translate the description of the Java code style checking tool into Chinese and display only the translated content.");
        data.set("stream", false);

        // Send the POST request and get the response
        String response = getResponse(data);
        return response;
    }


    /**
     * 获取请求结果 
     * @param data  
     * @author zack
     * @date 2024/12/02 16:06
     * @return String
     */
    public static String getResponse(JSONObject data) {
        String response = REQUEST.body(data.toString())
                .execute()
                .body();
        JSONObject responseDict = JSONUtil.parseObj(response);
        return responseDict.getStr("response");
    }
    
    
}
