package com.spotbugs.translate;

import cn.hutool.core.collection.CollectionUtil;
import com.spotbugs.translate.constant.TransModel;
import com.spotbugs.translate.utils.BaiduTransUtil;
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

    //https://www.roseindia.net/tutorial/xml/jdom/AddCDATA.html
    //https://kodejava.org/how-do-i-build-xml-cdata-sections/
    public static void main(String[] args) throws IOException, JDOMException {
        String messagePath = "D:\\spotbugs\\messages_old.xml";
        String newMessagePath = "D:\\spotbugs\\messages.xml";
        runStart(messagePath,newMessagePath);
    }

    /**
     * 开始运行 
     * @param messagePath
     * @param newMessagePath  
     * @author zack
     * @date 2024/12/02 15:35
     * @return void
     */
    public static void runStart(String messagePath, String newMessagePath) throws IOException, JDOMException {
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
        TransUtil.translateElement(elementResultList, TransModel.MODEL_QWEN);
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