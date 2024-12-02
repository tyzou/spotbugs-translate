package com.spotbugs.translate.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.spotbugs.translate.constant.TransModel;
import com.spotbugs.translate.vo.MyElement;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Element;

import java.util.Arrays;
import java.util.List;

/**
 * @author zouJianJun
 * @date 2024/12/2
 * @desc
 */
public class TransUtil {

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
    public static void translateElement(List<MyElement> elements,String model) {
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
            for (int itemIndex = 0; itemIndex < myElement.getElements().size(); itemIndex++) {
                try{
                    Element element = myElement.getElements().get(itemIndex);

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
                    if(model.equals(TransModel.MODEL_QWEN)){
                        transText = Qwen2TransUtil.trans(oldText);
                    }else if(model.equals(TransModel.MODEL_BAIDU_API)){
                        transText = BaiduTransUtil.trans(oldText);
                    }else{
                        throw new RuntimeException("不支持的翻译");
                    }
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
