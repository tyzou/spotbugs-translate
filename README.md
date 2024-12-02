

### 一、下载插件

**在idea官网下载idea对于版本的插件**

官网地址：https://plugins.jetbrains.com/plugin/14014-spotbugs/versions/stable



把message.xml从 `spotbugs-idea-1.2.8.zip` > `lib` > `spotbugs-4.8.6` > `message.xml` 复制出来



### 二、翻译配置

> 有两种方式可以自由选择

#### 2.1、Ai翻译

通过调用本地通过ollama部署的qwen2.5大模型，进行Ai智能翻译



#### 2.2、百度翻译

使用百度翻译进行机翻

##### 需要去配置百度翻译的应用ID

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

### 三、执行翻译

> 修改TranslateRun的main方法的message.xml的路径，并运行main方法

```java
public static void main(String[] args) throws IOException, JDOMException {
        String messagePath = "C:\\MyTest\\spotbugs\\1.2.8\\messages.xml";
        String newMessagePath = "C:\\MyTest\\spotbugs\\1.2.8\\messages_zh.xml";
        runStart(messagePath,newMessagePath,TransModel.MODEL_QWEN);
}
```



### 四、安装

#### 1.找到message.xml

- 在idea官网下载spotbugs-idea-1.2.5.zip
- 用压缩软件打开spotbugs-idea-1.2.5.zip在lib目录下把spotbugs-4.4.2.jar复制出来
- 用压缩软件打开spotbugs-4.4.2.jar将message.xml复制出来

#### 2.运行com.spotbugs.translate.Translate#main



#### 3.重新打包

- 把翻译好的message.xml通过压缩软件放到spotbugs-4.4.2.jar并覆盖
- 把spotbugs-4.4.2.jar放到spotbugs-idea-1.2.5.zip的lib目录并覆盖
- 最后使用IDEA的Install Plugin from Disk选择zip文件再重新安装，并重启idea

