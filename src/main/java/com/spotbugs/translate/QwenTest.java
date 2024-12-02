package com.spotbugs.translate;

import com.spotbugs.translate.utils.Qwen2TransUtil;

public class QwenTest {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String english = Qwen2TransUtil.trans("<p> The equals and hashCode method on <code>java.net.URL</code> resolve the domain name. As a result, these operations can be very expensive, and this detector looks for places where those methods might be invoked. </p>   翻译文本：`java.net.URL` 的 `equals` 和 `hashCode` 方法会解析域名。因此，这些操作可能会非常昂贵，这个检测器会寻找那些可能调用这些方法的地方。");
            System.out.println(english);
        }

    }
}
