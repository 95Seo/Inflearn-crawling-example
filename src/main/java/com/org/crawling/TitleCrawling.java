package com.org.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TitleCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

        try {
            Document document = conn.get();

            Elements titleElements = document.select("div.card-content > div.course_title");
            for(int j = 0; j < titleElements.size(); j++) {
                final String title = titleElements.get(j).text();
                System.out.println("강의 제목 : " + title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
