package com.org.crawling.jinhakapply.category;

import com.org.crawling.jinhakapply.category.type.*;
import com.org.crawling.jinhakapply.support.CategoryReg;

public class CategoryFactory {

    private CategoryFactory() {}

    public static Category create(String categoryName, int categorySeq) {
        // 신안산대학교 - 모집단위 -> 모집학과

        // 흠.. 좀 코드가 알아보기 어려운듯?
        if (categoryName.matches(CategoryReg.DEPARTMENT_REG.getReg()))
            return new Department(CategoryReg.DEPARTMENT_REG.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.SUB_DEPARTMENT_REG.getReg()))
            return new SubDepartment(CategoryReg.SUB_DEPARTMENT_REG.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.RECURITMENT_COUNT_REG.getReg()))
            return new RecruitmentCount(CategoryReg.RECURITMENT_COUNT_REG.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.APPLICANTS_COUNT_REG1.getReg()))
            return new ApplicantsCount(CategoryReg.APPLICANTS_COUNT_REG1.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.APPLICANTS_COUNT_REG2.getReg()))
            return new ApplicantsCount(CategoryReg.APPLICANTS_COUNT_REG2.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.COMPETITION_RATIO_REG1.getReg()))
            return new CompetitionRatio(CategoryReg.COMPETITION_RATIO_REG1.getCategoryName(), categorySeq);
        else if (categoryName.matches(CategoryReg.COMPETITION_RATIO_REG2.getReg()))
            return new CompetitionRatio(CategoryReg.COMPETITION_RATIO_REG2.getCategoryName(), categorySeq);
        else
            return new ExceptCategory(categoryName, categorySeq);
    }

    // 경쟁률 전용
    public static Category createRatio(int rowCount) {
        return new CompetitionRatio(CategoryReg.COMPETITION_RATIO_REG1.getCategoryName(), 0, "-1", rowCount);
    }
}
