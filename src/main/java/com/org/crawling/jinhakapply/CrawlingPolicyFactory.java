package com.org.crawling.jinhakapply;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;

@Slf4j
public class CrawlingPolicyFactory {

    private CrawlingPolicyFactory() {}

    // DepartmentTemplate을 받아서 DepartmentTemplate을 다시 return하는 구조 수정해야 함
    public static void columnPolicyExecute(Elements columns) {
        if (columns.size() == 4)
            fourColumnPolicy(columns);
        fiveColumnPolicy(columns);
    }

    private static void fourColumnPolicy(Elements elements) {
        log.info("{} -- {} -- {} -- {}", elements.get(0).text(), elements.get(1).text(), elements.get(2).text(), elements.get(3).text());
    }

    private static void fiveColumnPolicy(Elements elements) {
        log.info("{} -- {} -- {} -- {}", elements.get(1).text(), elements.get(2).text(), elements.get(3).text(), elements.get(4).text());
    }
    
    // 형 변환과 문자열 처리에 사용되는 메모리 체크 해보자
    private static String exclusionMark(String str) {
        return str.replaceAll(",", "");
    }

    private static String slideRatio(String ratio) {
        return exclusionMark(ratio).replace(" : 1", "");
    }

    private static Integer toInteger(String str) {
        return Integer.valueOf(exclusionMark(str));
    }

    private static Float toFloat(String str) {
        return Float.valueOf(exclusionMark(str));
    }
}
