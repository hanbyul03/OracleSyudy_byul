package com.sist.dao;
/*
 *    JDBC = DBCP = ORM = DataSet 
 *                        -- LinQ , JPA 
 *   ------  ----   --- MyBatis/Hibernate
 *    원시소스  웹에서만 사용
 */
import java.util.*;

import com.sist.vo.FoodVO;

import java.sql.*;
public class FoodDAO {
   private Connection conn;
   private PreparedStatement ps;
   // => CallableStement (PS/SQL) 
   private static FoodDAO dao;
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   // 드라이버 등록 => 285~287page
   public FoodDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex) {}
   }
   // 싱글턴 
   public static FoodDAO newInstance()
   {
	  if(dao==null)
		  dao=new FoodDAO();
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
   
   // 기능 
   public List<FoodVO> foodListData(int page)
   {
	   List<FoodVO> list=new ArrayList<FoodVO>();
	   try
	   {
		   getConnection();
		   String sql="SELECT fno,name,poster,num "
				     +"FROM (SELECT fno,name,poster,rownum as num "
				     +"FROM (SELECT fno,name,poster "
				     +"FROM menupan_food ORDER BY fno ASC)) "
				     +"WHERE num BETWEEN ? AND ?";
		   // 오라클 전송 
		   ps=conn.prepareStatement(sql);
		   // 실행 전에 ?에 값을 채운다 
		   int rowSize=12;
		   int start=(rowSize*page)-(rowSize-1);
		   int end=rowSize*page;
		   /*
		    *   1page => 1  12
		    *   2page => 13 24
		    *   3page => 25 36
		    *   ..
		    *   ..
		    *   .. 
		    *   
		    */
		   ps.setInt(1, start);
		   ps.setInt(2, end);
		   // 실행 결과값 받기 
		   ResultSet rs=ps.executeQuery();
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
   // 총페이지 
   public int foodTotalPage()
   {
	   int total=0;
	   try
	   {
		   getConnection();
		   String sql="SELECT CEIL(COUNT(*)/12.0) FROM menupan_food";
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
   // 상세보기 
   public FoodVO foodDetailData(int fno)
   {
	   FoodVO vo=new FoodVO();
	   try
	   {
		   getConnection();
		   String sql="SELECT fno,name,type,address,phone,score,"
				     +"parking,time,theme,content,poster,images,price "
				     +"FROM menupan_food "
				     +"WHERE fno=?";
		   ps=conn.prepareStatement(sql);
		   ps.setInt(1, fno);
		   ResultSet rs=ps.executeQuery();
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












