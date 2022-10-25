package com.org.crawling.jinhakapply.category.type;


import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;

public class SubDepartment extends Category {

    public SubDepartment(String categoryName, int categorySeq) {
        super(categoryName, categorySeq);
    }

    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
        template.setSubDept(columnData);
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
