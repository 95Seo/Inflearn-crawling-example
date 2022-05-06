package com.org.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PriceCrawling {
    public static void main(String[] args) {
        final String URL = "https://www.inflearn.com/courses/it-programming";
        Connection conn = Jsoup.connect(URL);

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
