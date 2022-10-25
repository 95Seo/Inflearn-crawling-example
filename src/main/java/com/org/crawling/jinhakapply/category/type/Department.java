package com.org.crawling.jinhakapply.category.type;


import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;

public class Department extends Category {

    public Department(
            String categoryName,
            int categorySeq
    ) {
        super(categoryName, categorySeq);
    }

    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
        if (toLinked)
            template.linkedDeptName(columnData);
        else
            template.setDeptName(columnData);
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
