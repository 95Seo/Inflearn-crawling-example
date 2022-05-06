package com.org.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class LinkCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

        try {
            Document document = conn.get();

            Elements linkElements = document.select("a.course_card_front");

            for (int j = 0; j < linkElements.size(); j++) {
                final String url = linkElements.get(j).attr("abs:href");
                System.out.println("강의 링크 : " + url);
                getGrade(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 평점을 알기 위해선 url을 통해 한번더 들어가야 할 필요가 있음
    private static void getGrade(final String url) throws IOException {
        Connection innerConn = Jsoup.connect(url);
        Document innerDocument = innerConn.get();

        // 평점이 없는경우 isNull을 통해 0점 처리
        Element gradeElement = innerDocument.selectFirst("div.dashboard-star__num");
        final float grade = Objects.isNull(gradeElement)
                ? toFloat("0")
                : toFloat(gradeElement.text());

        System.out.println("평점 : " + grade);
    }

    private static Float toFloat(final String str) {
        return Float.parseFloat(str);
    }

}
