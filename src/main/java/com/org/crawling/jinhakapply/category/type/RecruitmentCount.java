package com.org.crawling.jinhakapply.category.type;


import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;
import com.org.crawling.jinhakapply.support.TypeCastSupport;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;

@Slf4j
public class RecruitmentCount extends Category {

    private boolean isGroup = false;

    public RecruitmentCount(
            String categoryName,
            int categorySeq
    ) {
        super(categoryName, categorySeq);
    }

    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
//        isStringContainsWithin();

//        log.info("toLinked = {}, rowCount = {}", toLinked, rowCount);
        if (toLinked && (rowCount == 0) && !isGroup) {
            template.linkedRecruitmentCount(columnData);
        } else {
            template.setRecruitmentCount(columnData);
        }
    }

    public void setColumn(Element column) {
        columnData = column.text();
        rowCount = TypeCastSupport.stringToInteger(column.attr("rowspan"));
        if (rowCount > 1)
            isGroup = true;
        else
            isGroup = false;
    }

    @Override
    public boolean isTarget() {
        return true;
    }

    @Override
    public String getColumnData() {
        return columnData;
    }

//    private void isStringContainsWithin() {
//        if (columnData.contains("이내")) {
//            // 아.. 맘에 안들어
//            columnData = columnData.replace(" ", "").replace("이내", "");
//            columnData = "-" + columnData;
//        }
//    }
}
