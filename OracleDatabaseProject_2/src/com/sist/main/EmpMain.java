package com.sist.main;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
/*
 *   public String display(){
 *   
 *   }
 *   
 *   (){
 *   }
 *   ()->{
 *   
 *   }
 *   
 *   (a)->{
 *   }
 *   
 *   a->{
 *   }
 *   
 *   
 */
public class EmpMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EmpDAO dao=EmpDAO.newInstance();
        List<EmpVO> list=dao.empListData();
        // for(EmpVO vo:list) => Collection연결 => 제어 filter
        // filter => WHERE
        list.forEach(vo->{
        	System.out.println(vo.getEmpno()+" "
        	  +vo.getEname()+" "
        	  +vo.getJob()+" "
        	  +vo.getSal()+" "
        	  +vo.getHiredate()+" "
        	  +vo.getDvo().getDname()+" "
        	  +vo.getDvo().getLoc()+" "
        	  +vo.getSvo().getGrade());
        });
        Scanner scan=new Scanner(System.in);
        System.out.print("사번 입력:");
        int empno=scan.nextInt();
        EmpVO vo=dao.empDetailData(empno);
        System.out.println(vo.getEmpno()+" "
          	  +vo.getEname()+" "
          	  +vo.getJob()+" "
          	  +vo.getSal()+" "
          	  +vo.getHiredate()+" "
          	  +vo.getDvo().getDname()+" "
          	  +vo.getDvo().getLoc()+" "
          	  +vo.getSvo().getGrade());
	}

}