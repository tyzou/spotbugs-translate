package com.spotbugs.translate.vo;

import org.jdom2.Element;

import java.util.List;

/**
 * @author zouJianJun
 * @date 2024/12/2
 * @desc
 */
public class MyElement {

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
