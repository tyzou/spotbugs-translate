package com.spotbugs.translate;

import cn.hutool.core.collection.CollectionUtil;
import com.spotbugs.translate.utils.TransUtil;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Translate {

    //https://www.roseindia.net/tutorial/xml/jdom/AddCDATA.html
    //https://kodejava.org/how-do-i-build-xml-cdata-sections/
    public static void main(String[] args) throws IOException, JDOMException {
        String messagePath = "D:\\spotbugs\\messages_old.xml";
        String newMessagePath = "D:\\spotbugs\\messages.xml";
        testJdom1(messagePath,newMessagePath);
    }

    public static void testJdom1(String messagePath, String newMessagePath) throws IOException, JDOMException {
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
            filterElement(elementResults,tagArray,root,0);

            MyElement myElement = new MyElement();
            myElement.setElements(elementResults);
            myElement.setTags(tagArray);
            elementResultList.add(myElement);
        }
        //第三步：翻译
        translateElement(elementResultList);
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

    public static void filterElement(List<Element> result, String[] tags, Element parent, int tagIndex){
        if(tagIndex >= tags.length){
            return;
        }
        //当前节点的名称
        String tag = tags[tagIndex];
        //是否是最后一个节点
        boolean isLast = tags.length-1 == tagIndex ;
        List<Element> children = parent.getChildren(tag);
        if(CollectionUtil.isEmpty(children)){
            return;
        }
        if(isLast){
            result.addAll(children);
        }else{
            tagIndex++;
            for (Element child : children) {
                filterElement(result, tags,child,tagIndex);
            }
        }
    }

    /**
     * 翻译
     * @param elements
     */
    private static void translateElement(List<MyElement> elements) {
        //原始文本
        String oldText = "";
        //翻译文本
        String transText = "";
        //最终文本
        String lastText = "";

        String blank = "       ";
        int total = 0;
        for (MyElement element : elements) {
            total+=element.getElements().size();
        }
        for (MyElement myElement : elements) {
            String tagstr = Arrays.toString(myElement.getTags());
            for (int itemIndex = 0; itemIndex < myElement.elements.size(); itemIndex++) {
                try{
                    Element element = myElement.elements.get(itemIndex);

                    int cdataIndex = -1;
                    for (int i = 0; i < element.getContent().size(); i++) {
                        if(element.getContent().get(i).getCType().equals(Content.CType.CDATA)){
                            cdataIndex = i;
                        }
                    }
                    //原始文本
                    oldText = element.getTextTrim();
                    // \\s* 可以匹配空格、制表符、换页符等空白字符的其中任意一个。
                    oldText = oldText.replaceAll("\\s|\r|\n|\t", " ");
                    while (oldText.indexOf(blank)>-1){
                        oldText = oldText.replace(blank," ");
                    }
                    //翻译文本
                    transText = TransUtil.trans(oldText);
                    //最终文本
                    lastText = transText+"  "+oldText;
                    //处理不需要拼接原文的节点
                    if(myElement.getTags()[0].equals("BugPattern")){
                        if(element.getName().equals("LongDescription") || element.getName().equals("Details")){
                            lastText = transText;
                        }
                    }
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+ tagstr+">>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("原始文本："+oldText + "   翻译文本："+transText);
                    System.out.println("最终文本："+lastText);
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<剩余"+(total)+"条数据>>>>>>>>>>>>>>>>>\n\n");

                    if(cdataIndex > -1){
                        //翻译
                        element.setContent(cdataIndex,new CDATA(lastText));
                    }else{
                        element.setText(lastText);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                total--;
            }
        }


    }

}
class MyElement{
    String[] tags;

    List<Element> elements;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
