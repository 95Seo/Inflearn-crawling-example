package com.org.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Search {
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

        try {
            Document document = conn.get();

            Elements priceElements = document.getElementsByClass("price");
            for(int j = 0; j < priceElements.size(); j++) {
                final String price = priceElements.get(j).text();
                final String realPrice = getRealPrice(price);
                final String salePrice = getSalePrice(price);

                final int realIntPrice = toInt(removeNotNumeric(realPrice));
                final int saleIntPrice = toInt(removeNotNumeric(salePrice));

                System.out.println("가격 : " + realIntPrice);
                System.out.println("할인 가격 : " + saleIntPrice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSalePrice(String price) {
        final String[] pricesArray = price.split(" ");
        return pricesArray[0];
    }

    private static String getRealPrice(String price) {
        final String[] pricesArray = price.split(" ");
        return (pricesArray.length == 1) ? price : pricesArray[1];
    }

    private static String removeNotNumeric(final String str) {
        return str.replaceAll("\\W", "");
    }

    private static int toInt(final String str) {
        return Integer.parseInt(str);
    }
}
