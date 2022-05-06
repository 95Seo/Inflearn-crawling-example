package com.org.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class InflearnCrawling {
    // LAST_PAGE_INDEX 유동적으로 변경
    private static final Logger log = LoggerFactory.getLogger(InflearnCrawling.class);
    private static final int FIRST_PAGE_INDEX = 1;
    private static final int LAST_PAGE_INDEX = 44;
    private static final String PLATFORM = "Inflearn";

    private static int idx = 1;

    public static void main(String[] args) {
        try {
            // 개발 강의 모든 페이징 순회
            for(int i = FIRST_PAGE_INDEX; i <= LAST_PAGE_INDEX; i++) {
                final String infleanURrl = "https://www.inflearn.com/courses/it-programming?order=seq&page=" + i;
                Connection conn = Jsoup.connect(infleanURrl);

                Document document = conn.get();

                Elements columnElements = document.getElementsByClass("course_card_item");

                for(Element e : columnElements) {
                    System.out.println("강의번호 : " + idx++);
                    // 크롤링 항목 필요 리스트
                    // - 썸네일 링크, 강의 제목, 가격(할인가격), 평점, 강의자, 강의 링크, 수강자 수, 플랫폼, 강의 세션 개수 + 시간
                    Elements imageUrlElements = isThumbnail(e);
                    Element titleElements = e.selectFirst("div.card-content > div.course_title");
                    Element priceElements = e.selectFirst("div.card-content > div.price");
                    Element instructorElements = e.selectFirst("div.card-content > div.instructor");
                    Element linkElements = e.selectFirst("a.course_card_front");
                    Element descriptionElements = e.selectFirst("p.course_description");
                    Element skillElements = e.selectFirst("div.course_skills > span");

                    final String thumbNail = imageUrlElements.attr("abs:src");
                    final String title = titleElements.text();
//                    final int realIntPrice = toInt(removeNotNumeric(realPrice));
//                    final int saleIntPrice = toInt(removeNotNumeric(salePrice));
                    final String instructor = instructorElements.text();
                    final String url = linkElements.attr("abs:href");
                    final String description = descriptionElements.text();
                    final String skills = removeWhiteSpace(skillElements.text());
                    final String price = priceElements.text();
                    final String realPrice;
                    final String salePrice;
                    final String currency;

                    // 무료 처리(좀더 좋은 방법을 찾아 보자)
                    if(!price.equals("무료")) {
                        realPrice = removeNotNumeric(getRealPrice(price));
                        salePrice = removeNotNumeric(getSalePrice(price));
                        currency = String.valueOf(price.charAt(0));
                    } else {
                        realPrice = "무료";
                        salePrice = "무료";
                        currency = Currency.getInstance(Locale.KOREA).getSymbol();
                    }

                    System.out.println("썸네일: " + thumbNail);
                    System.out.println("강의 제목: " + title);
                    System.out.println("가격: " + realPrice);
                    System.out.println("할인 가격: " + salePrice);
                    System.out.println("원화: " + currency);
                    System.out.println("강의자: " + instructor);
                    System.out.println("강의 링크: " + url);
                    System.out.println("강의 설명: " + description);
                    System.out.println("기술 스택: " + skills);

                    /* 강의 링크 내부 */
                    Connection innerConn = Jsoup.connect(url);
                    Document innerDocument = innerConn.get();

                    /* 평점 */
                    Element ratingElement = innerDocument.selectFirst("div.dashboard-star__num");
                    final float rating = Objects.isNull(ratingElement)
                            ? toFloat("0")
                            : toFloat(ratingElement.text());
                    System.out.println("평점: " + rating);

                    /* 수강자 수 */
                    Element listenerElement = innerDocument.selectFirst("div.cd-header__info-cover");
                    final String listener = Objects.isNull(listenerElement)
                            ? innerDocument.selectFirst("span > strong").text()
                            : innerDocument.select("div.cd-header__info-cover > span > strong").get(1).text();
                    System.out.println("수강자 수: " + removeNotNumeric(listener));
                    final int viewCount = Integer.parseInt(removeNotNumeric(listener));

                    /* 강의 세션 개수 */
                    final String course = innerDocument.selectFirst("span.cd-curriculum__sub-title").text();
                    System.out.println("강의 세션 개수: " + getSessionCount(course));
                    final int sessionCount = Integer.parseInt(getSessionCount(course));
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRealPrice(final String price) {
        final String[] pricesArray = price.split(" ");
        return pricesArray[0];
    }

    private static String getSalePrice(final String price) {
        final String[] pricesArray = price.split(" ");
        return (pricesArray.length == 1) ? price : pricesArray[1];
    }

    private static Elements isThumbnail(final Element element) {
        Elements urlElements = element.select("figure.is_thumbnail > img.swiper-lazy");
        return urlElements.isEmpty() ? element.select("section.ac-gif > video.ac-gif__video > source.ac-gif__source") : urlElements;
    }

    // html 태그 제거
    private static String stripHtml(final String html) {
        return Jsoup.clean(html, Whitelist.none());
    }

    // 맨 앞, 맨 뒤 소괄호 제거
    private static String removeBracket(final String str) {
        return str.replaceAll("^[(]|[)]$", "");
    }

    private static String getSessionCount(final String course) {
        return removeNotNumeric(course.substring(0, course.indexOf("개")));
    }

    private static String removeNotNumeric(final String str) {
        return str.replaceAll("\\W", "");
    }

    private static String removeWhiteSpace(final String str) {
        return str.replaceAll("\\s", "");
    }

    private static int toInt(final String str) {
        return Integer.parseInt(str);
    }

    private static float toFloat(final String str) {
        return Float.parseFloat(str);
    }
}
