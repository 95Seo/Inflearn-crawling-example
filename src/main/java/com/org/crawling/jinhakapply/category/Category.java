package com.org.crawling.jinhakapply.category;

import com.org.crawling.jinhakapply.DepartmentTemplate;
import com.org.crawling.jinhakapply.category.type.ExceptCategory;
import com.org.crawling.jinhakapply.support.TypeCastSupport;
import lombok.Getter;
import org.jsoup.nodes.Element;

@Getter
public abstract class Category {
    protected String categoryName;
    protected int categorySeq;
    protected int rowCount = 0;
    protected String columnData;

    protected Category(
            String categoryName,
            int categorySeq
    ) {
        this.categoryName = categoryName;
        this.categorySeq = categorySeq;
    }

    public void setColumn(Element column) {
        columnData = column.text();
        rowCount = TypeCastSupport.stringToInteger(column.attr("rowspan"));
    }

    public boolean rowCountIsZero() {
        return rowCount == 0;
    }

    public void downRowCount() {
        rowCount--;
    }

    // saveCnt가 Department에 밖에 안쓰이는데
    // 무조건 다 받아야 한다.
    // 맘에 안드네//
    public abstract void setDepartmentProxyData(DepartmentTemplate template, boolean toLinked);

    public boolean isTarget() {
        return !(this instanceof ExceptCategory);
    }

    public abstract String getColumnData();
}
