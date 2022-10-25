package com.org.crawling.jinhakapply.category.type;


import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;
import com.org.crawling.jinhakapply.support.TypeCastSupport;

public class CompetitionRatio extends Category {

    public CompetitionRatio(String categoryName, int categorySeq) {
        super(categoryName, categorySeq);
    }

    // 좀 애매하네..
    // slideRatio를 columnData에 적용 시킨 상태로 필드로 가지고 싶다..
    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
        template.setCompetitionRatio(TypeCastSupport.stringToFloat(slideRatio(columnData)));
    }

    public CompetitionRatio(String categoryName, int categorySeq, String columnData, int rowCount) {
        super(categoryName, categorySeq);
        super.columnData = columnData;
        super.rowCount = rowCount;
    }

    @Override
    public boolean isTarget() {
        return true;
    }

    @Override
    public String getColumnData() {
        return slideRatio(columnData);
    }

    private static String slideRatio(String ratio) {
        String result;
        if ((result = ratio.replace(" : 1", "")).equals("-"))
            return "-1";

        return result;
    }
}
