package com.org.crawling.jinhakapply;

import com.org.crawling.jinhakapply.category.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class DepartmentTemplate {

    // 대학이름
    private String universityName;

    // 전형 구분
    private String admissionType;

    // 학과이름
    private String deptName = "";

    // 모집인원
    private String recruitmentCount;

    // 지원인원
    private String applicantsCount;

    // 경쟁률
    private Float competitionRatio;

    public DepartmentTemplate(
            String universityName
    ) {
        this.universityName = universityName;
    }

    public void linkedDeptName(String str) {
        deptName += " | " + str;
    }

    public void linkedApplicantsCount(String str) {
        applicantsCount += " | " + str;
    }

    public void linkedRecruitmentCount(String str) {
        recruitmentCount += " | " + str;
    }

    public void setSubDept(String subDept) {
        deptName += " [" + subDept + "]";
    }

    public void printLog() {
        log.info( "대학이름 : {}", universityName);
        log.info("모집전형 : {}", admissionType);
        log.info("모집단위 : {}", deptName);
        log.info("모집인원 : {}", recruitmentCount);
        log.info("지원인원 : {}", applicantsCount);
        log.info("경쟁률 : {}", competitionRatio);
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
