package com.org.crawling.jinhakapply.category.type;

import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.Category;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptCategory extends Category {

    public ExceptCategory(String categoryName, int categorySeq) {
        super(categoryName, categorySeq);
    }

    @Override
    public void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked) {
        // 흠...
        log.info("쓰레기 값");
    }

    @Override
    public boolean isTarget() {
        return false;
    }

    @Override
    public String getColumnData() {
        return columnData;
    }
}
