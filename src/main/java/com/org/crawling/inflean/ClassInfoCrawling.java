package com.org.crawling.inflean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class
ClassInfoCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

        try {
            Document document = conn.get();
            Elements instructorElements = document.getElementsByClass("instructor");
            Elements descriptionElements = document.select("p.course_description");
            Elements skillElements = document.select("div.course_skills > span");

            for(int j = 0; j < instructorElements.size(); j++) {
                final String instructor = instructorElements.get(j).text();
                final String description = descriptionElements.get(j).text();
                final String skill = removeWhiteSpace(skillElements.get(j).text());

                System.out.println("강의자 : " + instructor);
                System.out.println("강의 부가설명 : " + description);
                System.out.println("기술 스택 : " + skill);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removeWhiteSpace(final String str) {
        return str.replaceAll("\\s", "");
    }
}
