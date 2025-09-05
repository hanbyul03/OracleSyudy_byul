package com.sist.vo;
import java.util.*;

import lombok.Data;
@Data
// 반드시 => 컬럼과 일치 
// 문자열 : 날짜 , 숫자 => 오라클에 있는 데이터 모아서 한번 브라우저에 전송 (DTO) 
public class EmpVO {
   private int empno,sal,comm,deptno,mgr;
   private String ename,job;
   private Date hiredate;
   private DeptVO dvo=new DeptVO();
   private SalGradeVO svo=new SalGradeVO();
}