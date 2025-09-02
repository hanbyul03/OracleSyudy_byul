package com.sist.main;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class EmpMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		EmpDAO dao=EmpDAO.newInstance();
        while(true)
        {
        	System.out.println("---------- 메뉴 -----------");
        	System.out.println("1. 사원 목록");
        	System.out.println("2. 사원 상세보기");
        	System.out.println("3. 사원 검색");
        	System.out.println("4. 종료"); 
        	System.out.println("--------------------------");
        	System.out.print("메뉴 선택:");
        	int menu=scan.nextInt();
        	if(menu==4)
        	{
        		System.out.println("프로그램 종료");
        		break;
        	}
        	else if(menu==1)
        	{
        		List<EmpVO> list=dao.empListData();
        		for(EmpVO vo:list)
        		{
        			System.out.println(
        			  vo.getEmpno()+" "
        			  +vo.getEname()+" "
        			  +vo.getJob()+" "
        			  +vo.getDbday()+" "
        			  +vo.getSal()
        			);
        		}
        	}
        	else if(menu==3)
        	{
        		System.out.print("검색할 사원이름 입력:");
        		String ename=scan.next();
        		List<EmpVO> list=dao.empFindData(ename);
        		for(EmpVO vo:list)
        		{
        			System.out.println(
        			  vo.getEmpno()+" "
        			  +vo.getEname()+" "
        			  +vo.getJob()+" "
        			  +vo.getDbday()+" "
        			  +vo.getSal()
        			);
        		}
        	}
        	else if(menu==2)
        	{
        		EmpVO vo=dao.empDetailData(7900);
        		System.out.println("사번:"+vo.getEmpno());
        		System.out.println("이름:"+vo.getEname());
        		System.out.println("직위:"+vo.getJob());
        		System.out.println("입사일:"+vo.getHiredate());
        	}
        }
	}

}