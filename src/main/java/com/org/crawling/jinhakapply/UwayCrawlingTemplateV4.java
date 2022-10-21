package com.org.crawling.jinhakapply;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Component
public class UwayCrawlingTemplateV4 {

    // final 적용해도 될듯
    private static Map<String, String> categoryConvertMap = new HashMap<>();

    private static Map<String, CategoryInfo> categoryMap = new HashMap<>();

    private static List<CategoryInfo> categoryInfos = new LinkedList<>();

    private static DepartmentTemplate departmentTemplate;

    private static Queue<CategoryInfo> categoryRemoveQueue = new LinkedList<>();

    private static int excludeDeptCnt;

    // 한글 혹은 영어로 시작하나?
    private static final Pattern PATTERN2 = Pattern.compile("^\\D");

    public static void setCategoryConvertMap() {
        categoryConvertMap.put("캠퍼스","campus");
        categoryConvertMap.put("대학","university");
        categoryConvertMap.put("전형","admissionType");
        categoryConvertMap.put("모집단위","departmentName");
        categoryConvertMap.put("모집인원","recruitmentCount");
        categoryConvertMap.put("지원인원","applicantsCount");
        categoryConvertMap.put("경쟁률","competitionRatio");
        categoryConvertMap.put("성별", "sex");
        categoryConvertMap.put("종목  ", "event");
        // 뭔 ㅄ같은 ㅡㅡ
        // 학과 홍보
        // 학과 홈페이지
    }

    public static void main(String[] args) throws IOException {

        // 서울여자대학교
//        String URL = "http://ratio.uwayapply.com/Sl5KOk4mSmYlJjomSnBmVGY=";
//        String universityName = "서율여자대학교";

        // 동의대학교
//        String URL = "http://ratio.uwayapply.com/Sl5KOmBWSmYlJjomSnBmVGY=";
//        String universityName = "동의대학교";

        // 칼럼 5개
        // 서울과학대학교 <- 임마는 뭐고 추천인원이 모집인원으로 나온다
//        String URL = "http://ratio.uwayapply.com/Sl5KMDpXJkpmJSY6JkpwZlRm";
//        String universityName = "서울과학대학교";

        // 선문대학교
        // 학과 홍보라는 칼럼이 있음, 경쟁률 칼럼 없음
//        String URL = "http://ratio.uwayapply.com/Sl5KV2FhTVc6JkpmJSY6JkpwZlRm";
//        String universityName = "선문대학교";

        // 서울신학대학교
        // 모집인원, 경쟁률 칼럼 없음
        String URL = "http://ratio.uwayapply.com/Sl5KOjAmSmYlJjomSnBmVGY=";
        String universityName = "서울신학대학교";

        // 강원대학교
//        String URL = "http://ratio.uwayapply.com/Sl5KV2FOclc4OUpmJSY6JkpwZlRm";

        // 성별/종목이 있음
        // 가톨릭관동대학교
//        String URL = "http://ratio.uwayapply.com/Sl5KcldhVlc4TjlKZiUmOiZKcGZUZg==";

        departmentTemplate = new DepartmentTemplate(universityName);

        startCrawling(URL);
    }

    public static void startCrawling(String URL) throws IOException {
        log.info("크롤링을 시작합니다.");

        Document document = Jsoup.connect(URL).get();

        // 경쟁률을 보여주는 테이블(div.DivType)을 Element로 복수개인 Elements를 만들어서 가져온다.
        Elements tables = document.select("form div.DivType");

        tableParsing(tables);

        log.info("크롤링이 끝났습니다.");
    }

    // 정원내 일반고(학생부) 경쟁률 현황 => 이거(입학 유형, AdmissionsType) 추출 후 다음 for 문
    private static void tableParsing(Elements tables) {
        // tableElements 안에 있는 table 들을 하나 씩 꺼내옴
        for (Element table : tables) {
            Elements tableCategory = table.select("thead tr th");

            log.info("{}", tableCategory);
            if (!checkCategoryValid(tableCategory)) {
                log.info("{}", table.select("div h3").text().replace("경쟁률 현황", "") + "수집 X");
                continue;
            }

            // 일반고전형 경쟁률 현황(div h3)을 가져옴 (단일)
            log.info("{}", table.select("div h3").text().replace("경쟁률 현황", "") + "수집 O");
//            log.info("{}", e);

//            log.info("{}", tableCategory.text());

            // 테이블의 row를 나누고 rows 로 만들어 가져옴
            Elements rows = table.getElementsByClass("trFieldValue");

            rowParsing(rows);

            resourceDeallocate();
        }
    }

    // 넘겨 받은 표를 학과별로 한 줄씩 나눈다.
    private static void rowParsing(Elements rows) {
        // rows의 각 row를 꺼내와서 하나 씩 반복
//        log.info("{}", rows.text());
//        log.info("{}", rows);
        for (Element row : rows) {
            // 한줄의 row를 column 으로 나눔
            Elements columns = row.getElementsByClass("txtFieldValue");

            columnPolicyExecute(columns);
        }
    }

    // DepartmentTemplate을 받아서 DepartmentTemplate을 다시 return하는 구조 수정해야 함
    public static void columnPolicyExecute(Elements columns) {
        int currentPoint = 0;
        int staticCategoryCnt = categoryInfos.size();
        int dynamicCategoryCnt = getSubDeptCount(columns);
        int subDeptCnt;

        // 사이즈가 크면 department 뒤에 sub 이어 붙이기
        if ((subDeptCnt = dynamicCategoryCnt - staticCategoryCnt) > 0) {
            // columns.size()가 categoryInfos.size()보다 크다는 것은
            // 모집단위이 나누어 졌다는 것.
            // 공식을 바꿔야 할 듯
            // 즉, columns.size() - categoryInfos.size() 만큼 모집 단위가 늘어 났다는 것을 의미한다.
            log.info("===========================모집단위 나눠짐===========================");
            int deptSeq = categoryMap.get("모집단위").categorySeq;
            for (int i = 1; i <= subDeptCnt; i++) {
                int subDeptSeq = deptSeq + i;
                categoryInfos.add(subDeptSeq, new CategoryInfo("서브모집단위", subDeptSeq));
            }
        }

        for (CategoryInfo info : categoryInfos) {
            log.info("-------------------------");

            // rowCount가 1이면 columns에서 꺼내옴
            // 꺼내기 전 column 의 row값을 확인 후 1 이상이면 info에 집어넣음
            // start +1
            if (info.rowCount == 1) {
                Element column = columns.get(currentPoint);
                String data = column.text();
                currentPoint++;
                int row = toInteger(column.attr("rowspan"));
                if (row > 1) {
                    log.info("업 rowCount");
                    info.rowCount = row;
                    info.data = data;
                    // column.text()를 DepartmentTemplate에 삽입
                }
                log.info("{} : {} ", info.categoryName, data);
                if (
                        info.categoryIsDeptName() ||
                                info.categoryIsSubDeptName() ||
                                info.categoryIsRecruitCount() ||
                                info.categoryIsApplicantCount() ||
                                info.categoryIsRatio()
                ) {
                    departmentTemplate.setParameter(data, info.categoryName);
                }
            } else {
                // info.data를 DepartmentTemplate에 삽입
                log.info("다운 rowCount");
                info.rowCount--;
                log.info("{} : {} ", info.categoryName, info.data);
                if (
                        info.categoryIsDeptName() ||
                                info.categoryIsSubDeptName() ||
                                info.categoryIsRecruitCount() ||
                                info.categoryIsApplicantCount() ||
                                info.categoryIsRatio()
                ) {
                    departmentTemplate.setParameter(info.data, info.categoryName);
                }
            }


            if (info.categoryIsSubDeptName()) {
                if (info.rowCount == 1)
                    categoryRemoveQueue.add(info);
            }
            log.info("rowCount = " + info.rowCount);
            log.info("currentPoint = " + currentPoint);
        }

        for (CategoryInfo removeTarget : categoryRemoveQueue) {
            categoryInfos.remove(removeTarget);
        }
    }

    private static int getSubDeptCount(Elements columns) {
        int count = 0;
        for (CategoryInfo info : categoryInfos) {
            if (info.rowCount > 1)
                count += 1;
        }
        return columns.size() + count;
    }

    private static Integer toInteger(String str) {
        return Integer.valueOf(str);
    }

    private static boolean checkCategoryValid(Elements tableCategory) {
        // 우리가 수집을 목표로 하는 2가지 타입의 카테고리
        List<String> categoryList = tableCategory.eachText();
        if (
                categoryList.contains("모집단위") &&
                categoryList.contains("모집인원") &&
                categoryList.contains("지원인원")
        ) {
            for (int i = 0; i < categoryList.size(); i++) {
                categoryMap.put(
                        categoryList.get(i),
                        new CategoryInfo(categoryList.get(i), i)
                );

                categoryInfos.add(new CategoryInfo(categoryList.get(i), i));
            }

//            printMap();
            printList();
            return true;
        }

        return false;
    }

    private static void printList() {
        String str = "";
        for (CategoryInfo c : categoryInfos) {
            str += c.categorySeq + "." + c.categoryName + " ";
        }
        log.info(str);
    }

    private static void printMap() {
        String str = "";
        for (String c : categoryMap.keySet()) {
            CategoryInfo info = categoryMap.get(c);
            str += info.categorySeq + "." + info.categoryName + " ";
        }
        log.info(str);
        resourceDeallocate();
    }

    private static void resourceDeallocate() {
        categoryMap.clear();
        categoryInfos.clear();
    }

    private static int sumRowCount() {
        int result = 0;
        for (CategoryInfo info : categoryInfos) {
            result += info.rowCount;
        }

        return result;
    }

    private static class CategoryInfo {
        private String categoryName;
        private int categorySeq;
        private int rowCount = 1;
        private String data;

        private CategoryInfo(
                String categoryName,
                int categorySeq
        ) {
            this.categoryName = categoryName;
            this.categorySeq = categorySeq;
        }

        private void setData(String data) {
            this.data = data;
        }

        private String getData() {
            return data;
        }

        private boolean categoryIsCampus() {
            return categoryName.equals("캠퍼스");
        }

        private boolean categoryIsUniversity() {
            return categoryName.equals("대학");
        }

        private boolean categoryIsAdmissionType() {
            return categoryName.equals("전형");
        }

        private boolean categoryIsDeptName() {
            return categoryName.equals("모집단위");
        }

        private boolean categoryIsSubDeptName() {
            return categoryName.equals("서브모집단위");
        }

        private boolean categoryIsRecruitCount() {
            return categoryName.equals("모집인원");
        }

        private boolean categoryIsApplicantCount() {
            return categoryName.equals("지원인원");
        }

        private boolean categoryIsRatio() {
            return categoryName.equals("경쟁률");
        }
    }
}