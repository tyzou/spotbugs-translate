# spotbugs-translate
spotbugs汉化，目前使用百度翻译进行机翻



### 1.需要去配置百度翻译的应用ID

```java
public class BaiduConstant {
    /**
     * 应用ID
     */
    public static final String TRANS_APPID = "xxxx";
    /**
     * 秘钥
     */
    public static final String TRANS_PASS = "xxxx";
   
}
```



### 2.找到message.xml

- 在idea官网下载spotbugs-idea-1.2.5.zip
- 用压缩软件打开spotbugs-idea-1.2.5.zip在lib目录下把spotbugs-4.4.2.jar复制出来
- 用压缩软件打开spotbugs-4.4.2.jar将message.xml复制出来



### 3.运行com.spotbugs.translate.Translate#main



### 4.重新打包

- 把翻译好的message.xml通过压缩软件放到spotbugs-4.4.2.jar并覆盖
- 把spotbugs-4.4.2.jar放到spotbugs-idea-1.2.5.zip的lib目录并覆盖
- 最后使用IDEA的Install Plugin from Disk选择zip文件再重新安装，并重启idea

