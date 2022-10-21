package com.org.crawling.jinhakapply;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DepartmentTemplate {

    // 대학이름
    private String universityName;

    // 전형 구분
    private String admissionType;

    // 학과이름
    private String deptName;

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

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    public void setParameter(String data, String param) {
        switch (param) {
            case "모집단위" :
                setDeptName((String) data);
                break;
            case "서브모집단위" :
                setSubDept((String) data);
                break;
            case "모집인원" :
                setRecruitmentCount(toInteger(data));
                break;
            case "지원인원" :
                setApplicantsCount(toInteger(data));
                break;
            case "경쟁률" :
                setCompetitionRatio(toFloat(slideRatio(data)));
                break;
            default:
        }
    }

    // 형 변환과 문자열 처리에 사용되는 메모리 체크 해보자
    private static String exclusionMark(String str) {
        return str.replaceAll(",", "");
    }

    private static String slideRatio(String ratio) {
        return exclusionMark(ratio).replace(" : 1", "");
    }

    private static Integer toInteger(String str) {
        return Integer.valueOf(exclusionMark(str));
    }

    private static Float toFloat(String str) {
        return Float.valueOf(exclusionMark(str));
    }

    private void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private void setSubDept(String subDept) {
        deptName += " : " + subDept;
    }

    private void setRecruitmentCount(Integer recruitmentCount) {
        this.recruitmentCount = recruitmentCount;
    }

    private void setApplicantsCount(Integer applicantsCount) {
        this.applicantsCount = applicantsCount;
    }

    private void setCompetitionRatio(Float competitionRatio) {
        this.competitionRatio = competitionRatio;
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
