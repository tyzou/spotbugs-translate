package com.spotbugs.translate.utils;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.spotbugs.translate.constant.BaiduConstant;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BaiduTransUtil {

    public static Map<String,Object> paramMap = new HashMap<>();

    /**
     * 百度翻译 
     * @param text  
     * @author zack
     * @return String
     */
    public static String trans(String text){
        if(!StringUtils.hasText(text)){
           return "";
        }
        try {
            //随机数
            String salt = RandomUtil.randomNumbers(10);
            String sign  = Md5Util.MD5(BaiduConstant.TRANS_APPID +text+salt+BaiduConstant.TRANS_PASS);
            paramMap.put("q",text);
            paramMap.put("from",BaiduConstant.from);
            paramMap.put("to",BaiduConstant.to);
            paramMap.put("appid",BaiduConstant.TRANS_APPID);
            paramMap.put("salt",salt);
            paramMap.put("sign",sign);
            //链式构建请求
            String result = HttpRequest.post(BaiduConstant.URL)
                    .form(paramMap)//表单内容
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            result = UnicodeUtil.toString(result);
            JSONObject jsonObject = JSONUtil.parseObj(result);
            JSONArray transResult = jsonObject.getJSONArray("trans_result");
            JSONObject transItem = (JSONObject) transResult.get(0);
            String dst = transItem.getStr("dst");
            return dst;
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }
}
