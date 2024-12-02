package com.spotbugs.translate;

import com.spotbugs.translate.constant.TransMethodType;
import com.spotbugs.translate.utils.TransUtil;
import com.spotbugs.translate.vo.MyElement;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class TranslateRun {

    /**
     * 运行 
     * @param args  
     * @author zack
     * @date 2024/12/02 16:37
     * @return void
     */
    public static void main(String[] args) throws IOException, JDOMException {
        String messagePath = "C:\\MyTest\\spotbugs\\1.2.8\\messages.xml";
        String newMessagePath = "C:\\MyTest\\spotbugs\\1.2.8\\messages_zh.xml";
        runStart(messagePath,newMessagePath, TransMethodType.MODEL_QWEN);
    }

    /**
     * 开始运行
     * @param messagePath
     * @param newMessagePath
     * @param translateMethod
     * @throws IOException
     * @throws JDOMException
     */
    public static void runStart(String messagePath, String newMessagePath,String translateMethod) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();

        Document document = builder.build(new File(messagePath));
        Element root = document.getRootElement();
        //最终需要处理翻译的节点
        List<MyElement> elementResultList = new ArrayList<>();

        List<String[]> tags = new ArrayList<>();
        tags.add(new String[]{"PluginComponent","Description"});
        tags.add(new String[]{"PluginComponent","Details"});
        tags.add(new String[]{"BugCategory","Description"});
        tags.add(new String[]{"BugCategory","Details"});
        tags.add(new String[]{"FindBugsMain","Description"});
        tags.add(new String[]{"Detector","Details"});
        tags.add(new String[]{"BugPattern","Details"});
        tags.add(new String[]{"BugPattern","ShortDescription"});
        tags.add(new String[]{"BugPattern","LongDescription"});
        tags.add(new String[]{"BugCode"});

        //筛选
        for (String[] tagArray : tags) {
            List<Element> elementResults = new ArrayList<>();
            TransUtil.filterElement(elementResults,tagArray,root,0);

            MyElement myElement = new MyElement();
            myElement.setElements(elementResults);
            myElement.setTags(tagArray);
            elementResultList.add(myElement);
        }
        //第三步：翻译
        TransUtil.translateElement(elementResultList, translateMethod);
        //第四步：将上述内容写入XML
        XMLOutputter out = new XMLOutputter();
        out.setFormat(out.getFormat().setEncoding("UTF-8"));
        try {
            out.output(document, new FileOutputStream(new File(newMessagePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("<><><><><><><><><><><><><><><><><>><<<<<<<<<<<<<<<<<<<");
        }

    }

    

}