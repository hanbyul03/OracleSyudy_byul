package com.sist.vo;

import lombok.Data;

/*
 *   NO            NOT NULL NUMBER -- 고유번호 (PK)
 *   COMPANY                VARCHAR2(100) -- 회사명
 *   TITLE                  VARCHAR2(200) -- 공고 제목
 *   LOCATION               VARCHAR2(100) -- 근무지
 *   CAREER                 VARCHAR2(50)  -- 경력
 *   EDUCATION              VARCHAR2(50)  -- 학력
 *   EMPTYPE                VARCHAR2(50)  -- 고용형태
 *   SALARY                 VARCHAR2(100) -- 연봉 (옵션)
 *   REGDATE                VARCHAR2(20)  -- 등록일
 *   DEADLINE               VARCHAR2(20)  -- 마감일
 *   SKILL                  VARCHAR2(500) -- 스킬 키워드
 *   LOGO                   VARCHAR2(260) -- 회사 로고 URL
 *   LINK                   VARCHAR2(500) -- 상세 링크
 *   VIEWCOUNT              NUMBER        -- 조회 수 (옵션)
 *   SCRAPCOUNT             NUMBER        -- 스크랩 수 (옵션)
 */

@Data
public class JobKoreaVO {
    private int no;
    private String company;
    private String title;
    private String location;
    private String career;
    private String education;
    private String empType;
    private String salary;
    private String regdate;
    private String deadline;
    private String skill;
    private String logo;
    private String link;
    private int viewCount;
    private int scrapCount;
}
