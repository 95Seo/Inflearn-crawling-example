package com.org.crawling.jinhakapply;

import lombok.Setter;

@Setter
public class DepartmentTemplate {

    // 대학이름
    private String universityName;

    // 전형 구분
    private String admissionType;

    // 학과이름
    private String departmentName;

    // 모집인원
    private Integer recruitmentCount;

    // 지원인원
    private Integer applicantsCount;

    // 경쟁률
    private Float competitionRatio;

    private DepartmentTemplate() {}

    public DepartmentTemplate(
            String universityName
    ) {
        this.universityName = universityName;
    }

//    public Department toEntity() {
//        return Department.builder()
//                .universityName(universityName)
//                .admissionType(admissionType)
//                .departmentName(departmentName)
//                .recruitmentCount(recruitmentCount)
//                .applicantsCount(applicantsCount)
//                .competitionRatio(competitionRatio)
//                .build();
//    }
}
