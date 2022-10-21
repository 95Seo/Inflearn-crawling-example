package com.org.crawling.inflean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.add(1, 5);

        for (int i = 0; i < list.size(); i++)
            System.out.print(list.get(i) + " ");

        list.remove(1);

        for (int i = 0; i < list.size(); i++)
            System.out.print(list.get(i) + " ");

        for (int i = 0; i < 0; i++) {
            System.out.println("1");
        }
//        try {
//            Document document = conn.get();
//
//            Elements titleElements = document.select("div.card-content > div.course_title");
//            for(int j = 0; j < titleElements.size(); j++) {
//                final String title = titleElements.get(j).text();
//                System.out.println("강의 제목 : " + title);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
