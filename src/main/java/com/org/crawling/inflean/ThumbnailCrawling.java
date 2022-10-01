package com.org.crawling.inflean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ThumbnailCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

        try {
            Document document = conn.get();
            // document의 class가 swiper-lazy인 것들을 모두 get
            Elements imageUrlElements = document.getElementsByClass("swiper-lazy");

            for(Element element : imageUrlElements) {
                System.out.println(element.attr("abs:src"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
