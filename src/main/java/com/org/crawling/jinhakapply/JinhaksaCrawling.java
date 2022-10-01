package com.org.crawling.jinhakapply;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class JinhaksaCrawling {

    private static final String jinhaksaURrl = "http://ratio.uwayapply.com/Sl5KMCZXJTlKZkNDYUxKcGZUZg==";

    public static void main(String[] args) {
        log.info("크롤링을 시작합니다.");
        Connection conn = Jsoup.connect(jinhaksaURrl);
        try {
            Document document = conn.get();

            log.info(document.text());

            Element tableElement = document.getElementById("table");

            log.info("{}", tableElement.select("form div.DivType"));

            Elements listElement = tableElement.select("form div.DivType");

            extractRatio(listElement);

//            for (Element e : listElement) {
//                extractRatio(e);
//            }

//            Elements columnElements = listElement.select("a");

//            for (Element e : listElement) {
//                Elements el = e.select(".info");
//
//                log.info("{}", el.size());
//                log.info("2");
////                Element applyElements = e.selectFirst("a.apply");
////                final String text = applyElements.text();
////                final String url = applyElements.attr("abs:href");
////                log.info(text);
////                log.info(url);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("크롤링이 끝났습니다.");
    }

    private static void extractRatio(Element listElements) {
        log.info("전형 경쟁률 추출 시작");

        log.info("{}", listElements.text());

        log.info("전형 경쟁률 추출 끝");
    }

    private static void extractRatio(Elements listElements) {
        log.info("전형 경쟁률 추출 시작");

        for (Element e : listElements) {
            log.info("{}", e.text());

            Elements type = e.select("div h3");

            log.info(type.text());
        }

        log.info("전형 경쟁률 추출 끝");
    }

}
