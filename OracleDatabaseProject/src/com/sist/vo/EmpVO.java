package com.sist.vo;
/*
 EMPNO NOT NULL NUMBER(4)
 ENAME VARCHAR2(10)
 JOB  VARCHAR2(9)
 MGR   NUMBER(4)
 HIREDATE     DATE
 SAL   NUMBER(7,2)
 COMM  NUMBER(7,2)
 DEPTNO     NUMBER(2)
 
      오라클 데이터형 
      ------------
      문자 관련 
        CHAR / VARCHAR2 / CLOB  ==> String
      숫자 관련 
        NUMBER ==> int / double 
      날짜 관련 
        DATE , TIMESTAMP ==> java.util.Date
      => 변수명은 이미 결정되어 있다 
         ----- 컬럼과 동일 
 */
import java.util.*;

import lombok.Data;
@Data
public class EmpVO {
  private int empno,mgr,sal,comm,deptno;
  private String ename,job,dbday;// 변환된 날짜 
  private Date hiredate;
}