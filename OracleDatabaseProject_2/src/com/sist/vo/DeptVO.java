package com.sist.vo;

import lombok.Data;
// => SELECT empno,ename,job,dname,loc 
@Data
public class DeptVO {
   private int deptno;
   private String dname,loc;
}