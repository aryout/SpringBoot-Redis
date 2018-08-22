package com.faceyee.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by 97390 on 8/21/2018.
 */
@Component // 直接扫描application文件
public class MyProperties {
    @Value("${com.faceyee.title}")
    private String title;
    @Value("${com.faceyee.description}")
    private String description;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
