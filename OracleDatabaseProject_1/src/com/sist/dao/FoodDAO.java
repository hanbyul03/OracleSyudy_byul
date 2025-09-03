package com.sist.dao;
import java.util.*;

import com.sist.vo.FoodVO;

import java.sql.*;
public class FoodDAO {
    //1. 오라클 연결 => 인터페이스 
	private Connection conn;
	//2. 오라클 송수신 (송신:SQL,수신:저장된 데이터 읽기)
	private PreparedStatement ps;
	//3. 한개의 메모리 사용 (싱글턴) 
	private static FoodDAO dao;
	//4. 상수 = 오라클 주소 
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	// 구현 1 드라이버 등록 
	public FoodDAO()
	{
		// 생성자 => 멤버변수 초기화 , 시각과 동시에 처리 
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 클래스이름으로 멤버변수 , 생성자 , 메소드 제어 
			// 리플렉션 
			// => DriverManager
		}catch(Exception ex) {}
	}
	// 구현 2 싱글턴 
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	// 웹 => Connection Pool
	// 구현 3 오라클 연결 
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	// 구현 4 오라클 해제 
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// ----------------------------- 공통으로 적용 
	// 기능 1. 목록 출력
	public List<FoodVO> foodListData(int page)
	{
		List<FoodVO> temp=new ArrayList<FoodVO>();
		List<FoodVO> list=new ArrayList<FoodVO>();
		try
		{
			//1. 연결 
			getConnection();
			//2. SQL문장 만든다 ==> 
			String sql="SELECT fno,name,type,poster,score "
					  +"FROM food "
					  +"ORDER BY fno ASC";
			//3. 오라클 전송 
			ps=conn.prepareStatement(sql);
			//4. 실행후 결과값 요청 
			ResultSet rs=ps.executeQuery();
			//5. 전송하기 위해 List에 값을 첨부 
			while(rs.next()) // 첫번째 위치부터 저장을 시작 
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setType(rs.getString(3));
				vo.setPoster("https://www.menupan.com"+rs.getString(4));
				vo.setScore(rs.getDouble(5));
				temp.add(vo);
			}
			rs.close();
			
			int rowSize=20;
			int start=(rowSize*page)-rowSize; 
			int end=rowSize*page;
			
			list=temp.subList(start, end);
			
			// => View : 인라인뷰 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			// 닫기 
			disConnection();
		}
		return list;
	}
	// 총페이지 
	public int foodTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/20.0) "
					  +"FROM food";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
}







