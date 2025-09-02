package com.sist.dao;
import java.util.*;

import com.sist.vo.EmpVO;


import java.sql.*;
/*
 *   DAO => 데이터베이스 연동
 *   VO => 컬럼값을 받기위한 자바변수 설정
 *   ----------------------------
 *   오라클은 실행과정 => ROW단위로 
 *   
 *   DAO 제작
 *   1. 오라클 드라이버 설치 : ojdbc8.jar => 오라클사에 제공
 *        Class.forName("드라이버") => 한번 (생성자)
 *   2. 데이터베이스 연결
 *      Connection
 *   3. 데이터베이스 해제
 *   4. SQL문장 제작 => ***
 *   5. 오라클 전송 => PreperedStatement
 *   6. 오라클에서 SQL문장 실행 => 결과값을 얻어온다
 *   7. 결과값을 List/VO에 담아서 => 콘솔 / 윈도우 / 브라우저에 전송
 *   8. 닫기
 *   ------------------------------------------ 메소드 한개로 제작
 *        selectList SelectOne
 */
public class EmpDAO {
    //1. 공통 사용된 객체 
	private Connection conn; // 오라클 연결 객체 => 인터페이스 
	// => SQL문장 ANSI (표준화) 
	//2. SQL전송
	private PreparedStatement ps; 
	//3. 싱글턴 
	private static EmpDAO dao;
	//4. 오라클 주소 
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//5. 드라이버 등록 
	public EmpDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//   com.mysql.cj.Driver
		}catch(Exception ex) {}
	}
	//6. 싱글턴 구현 
	public static EmpDAO newInstance()
	{
		if(dao==null)
			dao=new EmpDAO();
		return dao;
	}
	//7. 오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//8. 오라클 연결 종료 
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//9. 기능
	//9-1. 목록 => 프로젝션 List
	public List<EmpVO> empListData()
	{
		List<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			// 1. 연결
			getConnection();
			// 2. 오라클에 어떤 문장을 전송할지 
			String sql="SELECT empno,ename,job,TO_CHAR(hiredate,'YYYY-MM-DD'),sal "
					  +"FROM emp "
					  +"ORDER BY sal DESC";
			//3. 오라클로 전송 
			ps=conn.prepareStatement(sql);
			//4. 실행후에 결과값을 메모리에 저장 요청 
			ResultSet rs=ps.executeQuery();
			while(rs.next()) // 첫번째줄부터 데이터를 읽어라 
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setSal(rs.getInt(5));
				
				list.add(vo);
			}
			// 5. 메모리 닫기
			rs.close();
		}catch(Exception ex)
		{
			// 에러 확인 
			ex.printStackTrace();
		}
		finally 
		{
		   disConnection(); // 오라클 닫기	
		}
		return list;
	}
	//9-2. 상세보기 => 셀렉션 VO
	public EmpVO empDetailData(int empno)
	{
		EmpVO vo=new EmpVO();
		try
		{
			getConnection();
			String sql="SELECT empno,ename,job,mgr,hiredate,"
					  +"       sal,comm,deptno "
					  +"FROM emp "
					  +"WHERE empno="+empno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setHiredate(rs.getDate(5));
			vo.setSal(rs.getInt(6));
			vo.setComm(rs.getInt(7));
			vo.setDeptno(rs.getInt(8));
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	//9-3. 검색 => LIKE List
	public List<EmpVO> empFindData(String ename)
	{
		List<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			// 1. 연결
			getConnection();
			// 2. 오라클에 어떤 문장을 전송할지 
			// 오라클은 데이터가 대소문자 구분 => EMP에 모든 데이터가 대문자 
			String sql="SELECT empno,ename,job,TO_CHAR(hiredate,'YYYY-MM-DD'),sal "
					  +"FROM emp "
					  +"WHERE ename LIKE '%"+ename.toUpperCase()+"%' "
					  +"ORDER BY sal DESC";
			//3. 오라클로 전송 
			ps=conn.prepareStatement(sql);
			//4. 실행후에 결과값을 메모리에 저장 요청 
			ResultSet rs=ps.executeQuery();
			while(rs.next()) // 첫번째줄부터 데이터를 읽어라 
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setSal(rs.getInt(5));
				
				list.add(vo);
			}
			// 5. 메모리 닫기
			rs.close();
		}catch(Exception ex)
		{
			// 에러 확인 
			ex.printStackTrace();
		}
		finally 
		{
		   disConnection(); // 오라클 닫기	
		}
		return list;
	}
	
	
	
}