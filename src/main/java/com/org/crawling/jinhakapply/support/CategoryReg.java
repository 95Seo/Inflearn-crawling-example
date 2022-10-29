package com.org.crawling.jinhakapply.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryReg {
    DEPARTMENT_REG("^[모집단위\\s]*$", "모집단위"),
    SUB_DEPARTMENT_REG("^[서브모집단위\\s]*$", "서브모집단위"),
    // 모집인원이 "40이내" 라고 되어 있으면 -> -40으로 변경
    RECURITMENT_COUNT_REG("^[모집인원\\s]*$", "모집인원"),
    APPLICANTS_COUNT_REG1("^[지원인원\\s]*$", "지원인원"),
    APPLICANTS_COUNT_REG2("^[지원자\\s]*$", "지원인원"),
    COMPETITION_RATIO_REG1("^[경쟁률\\s]*$", "경쟁률"),
    COMPETITION_RATIO_REG2("^[지원현황\\s]*$", "지원인원");

    private final String reg;
    private final String categoryName;
}
