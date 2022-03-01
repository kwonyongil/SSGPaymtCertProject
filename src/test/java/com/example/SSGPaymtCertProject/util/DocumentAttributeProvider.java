package com.example.SSGPaymtCertProject.util;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public interface DocumentAttributeProvider {

    static Attributes.Attribute getDateFormat() {
        // attributes에 key와 values를 각각 넣을수 있지만
        //반복적으로 사용되는 것들은 static 메서드로 사용하는것을 추천합니다.
        return key("format").value("yyyy-MM-dd");
    }

    static Attributes.Attribute getChnlExample() {
        return key("chnlExample").value("mo, pc");
    }
}