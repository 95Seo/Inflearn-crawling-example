package com.org.crawling.jinhakapply;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// type2 크롤링
@Slf4j
public class JinhaksaCrawling {

    private static final String URL = "https://ratio.uwayapply.com/Sl5Kclc6JldhYGJKZkNDYUxKcGZUZg";
    private static final Pattern PATTERN1 = Pattern.compile("^.*[0-9].*$");
    private static final Pattern PATTERN2 = Pattern.compile("^\\D");

    private static Map<String, Map> admissionsTypeMap = new HashMap<>();
    private static Map<String, List> departmentTypeMap;
    private static List<List> departmentList;

    // 과의 현재 정보를 담는 리스트
    // 0 - 모집단위 (ex. 기계공학과)
    // 1 - 모집 인원 (ex. 41)
    // 2 - 지원 인원 (ex. 171)
    // 3 - 경쟁룰 (ex. 4.17 : 1)
    private static List<String> departmentInfoList;

    private static String temp = "";

    public static void main(String[] args) {
        log.info("크롤링을 시작합니다.");
        Connection conn = Jsoup.connect(URL);
        try {
            Document document = conn.get();

//            log.info(document.text());

            Element tableElement = document.getElementById("table");

            Elements listElement = tableElement.select("form div.DivType");

//            log.info("{}", listElement);

            extractAdmissionsType(listElement);

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

//            String str1 = "4.25: 1";
//            String str2 = "컴퓨터게임과(3년제)";
//            String str3 = "";
//            Matcher matcher1 = PATTERN1.matcher(str3);
//            Matcher matcher2 = PATTERN2.matcher(str3);
//            log.info("{}", matcher1.find());

//            log.info("{}", isEmpty(temp));

//            printMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("크롤링이 끝났습니다.");
    }

    private static void printMap() {
        for (String s : admissionsTypeMap.keySet()) {
            departmentTypeMap = admissionsTypeMap.get(s);
            log.info("{}", s);
            for (String st : departmentTypeMap.keySet()) {
                log.info("{}=>{}", s, st);
                departmentList = departmentTypeMap.get(st);
                for (List list : departmentList) {
                    String str = toString(list);
                    log.info("{}=>{}=>{}",s, st, str);
                }
            }
        }
    }

    private static void printCount() {
        log.info("총 전형 갯수 = {}", admissionsTypeMap.keySet().size());
        for (String s : admissionsTypeMap.keySet()) {
            departmentTypeMap = admissionsTypeMap.get(s);
            log.info("{} 전형에 소속된 계열 갯수 = {}",s , departmentTypeMap.keySet().size());
            for (String st : departmentTypeMap.keySet()) {
                departmentList = departmentTypeMap.get(st);
                log.info("{} {}계열에 소속된 학과 갯수 = {}",s , st, departmentList.size());
            }
        }
    }

    private static String toString(List<String> list) {
        String result = "";
        for (String str : list) {
            result += str + " ";
        }

        return result;
    }

    // 정원내 일반고(학생부) 경쟁률 현황 => 이거(입학 유형, AdmissionsType) 추출 후 다음 for 문
    private static void extractAdmissionsType(Elements listElements) {
        // 숫자가 포함되어 있으면 truem 아니면 false
//        log.info("전형 경쟁률 추출 시작");

        // table -> tbody -> tr.trFieldValue 안의 것들을 가져 와야 함
        for (Element e : listElements) {
//            log.info("{}", e);

            Elements type = e.select("div h3");
            Elements departments = e.getElementsByClass("trFieldValue");

            if (admissionsTypeMap.containsKey(type.text()))
                departmentTypeMap = admissionsTypeMap.get(type.text());
            else
                departmentTypeMap = new HashMap<>();

            admissionsTypeMap.put(type.text(), departmentTypeMap);

//            log.info("{} 추출 시작", type.text());
            // td.txtFieldValue 로 다음 for문
            classifyDepartment(departments);
//            log.info("{} 추출 종료", type.text());
        }

    }

    // 넘겨 받은 표를 학과별로 한 줄씩 나눈다.
    private static void classifyDepartment(Elements departments) {
        for (Element at : departments) {
            Elements departmentUnitRatio = at.getElementsByClass("txtFieldValue");

            departmentInfoList = new ArrayList<>();

            extractDepartmentUnitRatio(departmentUnitRatio);

            departmentList.add(departmentInfoList);
        }
    }

    private static void extractDepartmentUnitRatio(Elements departmentUnitRatio) {
        departmentList = new ArrayList<>();
        temp = "";
        int count = 0;
        for (Element txt : departmentUnitRatio) {
            String align = txt.attr("align");
            String department = txt.text();
            // 숫자들어있는가?
            Matcher matcher0 = PATTERN1.matcher(temp);

            // 한글 혹은 영어로 시작하나?
            Matcher matcher1 = PATTERN2.matcher(temp);

            // 한글 혹은 영어로 시작하나?
            Matcher matcher2 = PATTERN2.matcher(department);

            if (departmentUnitRatio.size() != 4) {
                if (matcher2.find()) {
                    if (matcher1.find()) {
                        count++;
//                    log.info("[대학/ 계열/ 학부] {}", department);
//                    departmentInfoList.clear();
//                    departmentInfoList.add(department);
                    }
//                    else if (matcher0.find()){
//                        count++;
////                    log.info("[모집인원/ 지원인원/ 경쟁률] {}", department);
////                    departmentInfoList.add(department);
//                    } else {
//                        count = 0;
//                    }
                    // department가 숫자로 시작하는 경우
                } else {
                    count++;
//                log.info("[안바뀜] {}", department);
//                departmentInfoList.add(department);
                }
            } else
                count++;


//            log.info("listSize : {}", departmentInfoList.size());

            switch (count) {
                case 1 :
                    log.info("[모집단위] {}", department);
                    departmentInfoList.add(department);
                    break;
                case 2 :
                    log.info("[모집인원] {}", department);
                    departmentInfoList.add(department);
                    break;
                case 3 :
                    log.info("[지원인원] {}", department);
                    departmentInfoList.add(department);
                    break;
                case 4 :
                    log.info("[경쟁률] {}", department);
                    departmentInfoList.add(department);
                    break;
            }

//            if (row != 0 || column != 0) {
//                log.info("??? {}, {} : {}", txt.text(), row, column);
//                String department = txt.text();
//                departmentInfoList.add(department);
//            }

//            if (matcher1.find() || matcher2.find()) {
//                String department = txt.text();
//                departmentInfoList.add(department);
//            }

//            if (!matcher1.find() && align.equals("center")) {
//                String departmentType = txt.text();
//
//                if (departmentTypeMap.containsKey(departmentType)) {
//                    log.info("오류네요");
//                    break;
//                }
//                departmentList = new ArrayList<>();
//                departmentTypeMap.put(departmentType, departmentList);
//
////                        log.info("계열 : {}", departmentType);
//            }
//            else {
//                String department = txt.text();
//                departmentInfoList.add(department);
////                        log.info("나머지 : {}", department);
//            }

            temp = txt.text();
        }
    }

    // null 이거나 비어있으면 true
    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
