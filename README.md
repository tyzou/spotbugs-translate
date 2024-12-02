

### 一、Ai翻译

通过调用本地ollama部署的qwen2.5大模型，进行Ai智能翻译



### 二、百度翻译

使用百度翻译进行机翻

#### 需要去配置百度翻译的应用ID

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





### 三、翻译后的处理

#### 1.找到message.xml

- 在idea官网下载spotbugs-idea-1.2.5.zip
- 用压缩软件打开spotbugs-idea-1.2.5.zip在lib目录下把spotbugs-4.4.2.jar复制出来
- 用压缩软件打开spotbugs-4.4.2.jar将message.xml复制出来

#### 2.运行com.spotbugs.translate.Translate#main



#### 3.重新打包

- 把翻译好的message.xml通过压缩软件放到spotbugs-4.4.2.jar并覆盖
- 把spotbugs-4.4.2.jar放到spotbugs-idea-1.2.5.zip的lib目录并覆盖
- 最后使用IDEA的Install Plugin from Disk选择zip文件再重新安装，并重启idea

