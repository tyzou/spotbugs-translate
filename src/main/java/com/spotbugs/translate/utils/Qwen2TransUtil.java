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

    public static String trans(String text){
        /*text = "We are releasing the base model weights and network architecture of Grok-1, our large language model. "
                + "Grok-1 is a 314 billion parameter Mixture-of-Experts model trained from scratch by xAI.\n"
                + "\n"
                + "This is the raw base model checkpoint from the Grok-1 pre-training phase, which concluded in October 2023. "
                + "This means that the model is not fine-tuned for any specific application, such as dialogue.\n"
                + "\n"
                + "We are releasing the weights and the architecture under the Apache 2.0 license.\n"
                + "\n"
                + "To get started with using the model, follow the instructions at github.com/xai-org/grok.\n"
                + "\n"
                + "Model Details\n"
                + "Base model trained on a large amount of text data, not fine-tuned for any particular task.\n"
                + "314B parameter Mixture-of-Experts model with 25% of the weights active on a given token.\n"
                + "Trained from scratch by xAI using a custom training stack on top of JAX and Rust in October 2023.\n"
                + "The cover image was generated using Midjourney based on the following prompt proposed by Grok: "
                + "A 3D illustration of a neural network, with transparent nodes and glowing connections, showcasing the varying weights "
                + "as different thicknesses and colors of the connecting lines.";*/


        // Prepare the data
        JSONObject data = new JSONObject();
        data.set("model", "qwen2.5");
        data.set("prompt", text);
        data.set("system", "Translate the following into chinese and only show me the translated");
        data.set("stream", false);

        // Send the POST request and get the response
        String response = getResponse(data);
        System.out.println(response);
        return text;
    }


    /**
     * 获取请求结果 
     * @param data  
     * @author zack
     * @date 2024/12/02 16:06
     * @return String
     */
    public static String getResponse(JSONObject data) {
        // Send the POST request using Hutool
        String response = HttpRequest.post(API_URL)
                .header("Content-Type", "application/json")
                .body(data.toString())
                .execute()
                .body();

        // Parse the response to extract the desired content
        JSONObject responseDict = JSONUtil.parseObj(response);
        return responseDict.getStr("response");
    }
    
    
}
