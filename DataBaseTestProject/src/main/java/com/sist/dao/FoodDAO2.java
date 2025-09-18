package com.sist.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.sist.vo.*;

import oracle.jdbc.OracleTypes;
public class FoodDAO2 {
	private Connection conn;
	   //private PreparedStatement ps;
	   // => CallableStement (PS/SQL) 
	   private CallableStatement ps;
	   private static FoodDAO2 dao;
	   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	   
	   // 드라이버 등록 => 285~287page
	   public FoodDAO2()
	   {
		   try
		   {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
		   }catch(Exception ex) {}
	   }
	   // 싱글턴 
	   public static FoodDAO2 newInstance()
	   {
		  if(dao==null)
			  dao=new FoodDAO2();
		  return dao;
	   }
	   
	   // 연결 
	   public void getConnection()
	   {
		   try
		   {
			   conn=DriverManager.getConnection(URL,"hr","happy");
		   }catch(Exception ex) {}
	   }
	   // 닫기
	   public void disConnection()
	   {
		   try
		   {
			   if(ps!=null)
				   ps.close();
			   if(conn!=null)
				   conn.close();
		   }catch(Exception ex) {}
	   }
	   
	   public List<FoodVO> foodListData(int page)
	   {
		   List<FoodVO> list=new ArrayList<FoodVO>();
		   try
		   {
			   getConnection();
			   String sql="{CALL foodListData(?,?,?)}";
			   ps=conn.prepareCall(sql);
			   // ?에 값을 채운다 
			   int rowSize=12;
			   int start=(rowSize*page)-(rowSize-1);
			   int end=rowSize*page;
			   
			   ps.setInt(1, start);
			   ps.setInt(2, end);
			   ps.registerOutParameter(3, OracleTypes.CURSOR);
			   // PROCEDURE의 모든 실행은 executeQuery
			   ps.executeQuery();
			   
			   // 저장된 값 읽기
			   ResultSet rs=(ResultSet)ps.getObject(3);
			   
			   while(rs.next())
			   {
				   FoodVO vo=new FoodVO();
				   vo.setFno(rs.getInt(1));
				   vo.setName(rs.getString(2));
				   vo.setPoster(rs.getString(3));
				   list.add(vo);
			   }
			   rs.close();
			   
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
		   return list;
	   }
	   public int foodTotalPage()
	   {
		   int total=0;
		   try
		   {
			   getConnection();
			   String sql="{CALL foodTotalPage(?)}";
			   ps=conn.prepareCall(sql);
			   ps.registerOutParameter(1, OracleTypes.INTEGER);
			   // CURSOR / VARCHAR / INTEGER => OUT
			   ps.executeQuery();
			   total=ps.getInt(1);
			   // ResultSet => CURSOR 
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
	   public FoodVO foodDetailData(int fno)
	   {
		   FoodVO vo=new FoodVO();
		   try
		   {
			   getConnection();
			   String sql="{CALL foodDetailData(?,?)}";
			   ps=conn.prepareCall(sql);
			   ps.setInt(1, fno);
			   ps.registerOutParameter(2,OracleTypes.CURSOR);
			   ps.executeQuery();
			   ResultSet rs=(ResultSet)ps.getObject(2);
			   rs.next();
			   vo.setFno(rs.getInt(1));
			   vo.setName(rs.getString(2));
			   vo.setType(rs.getString(3));
			   vo.setAddress(rs.getString(4));
			   vo.setPhone(rs.getString(5));
			   vo.setScore(rs.getDouble(6));
			   vo.setParking(rs.getString(7));
			   vo.setTime(rs.getString(8));
			   vo.setTheme(rs.getString(9));
			   vo.setContent(rs.getString(10));
			   vo.setPoster(rs.getString(11));
			   vo.setImages(rs.getString(12));
			   vo.setPrice(rs.getString(13));
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
}



