package com.org.crawling.jinhakapply.category.type;


import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;

public class ApplicantsCount extends Category {

    public ApplicantsCount(String categoryName, int categorySeq) {
        super(categoryName, categorySeq);
    }

    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
        if (toLinked)
            template.linkedApplicantsCount(columnData);
        else
            template.setApplicantsCount(columnData);
    }

    @Override
    public boolean isTarget() {
        return true;
    }

    @Override
    public String getColumnData() {
        return columnData;
    }
}
