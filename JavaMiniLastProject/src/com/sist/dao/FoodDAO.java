package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.FoodVO;

public class FoodDAO {
	   private Connection conn;
	   private PreparedStatement ps;
	   //                                         211.238.142.22
	   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	   private static FoodDAO dao;
	   private final int ROWSIZE=12;
	   // 드라이버 등록 
	   public FoodDAO()
	   {
		   try
		   {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
		   }catch(Exception ex) {}
	   }
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
			   // 1조 => hr_1 , 2조 => hr_2 , 3조 => hr_3 
			   conn=DriverManager.getConnection(URL,"hr","happy");
		   }catch(Exception ex) {}
	   }
	   // 해제 
	   public void disConnection()
	   {
		   try
		   {
			   if(ps!=null) ps.close();
			   if(conn!=null) conn.close();
		   }catch(Exception ex) {}
	   }
	   // = 목록 selectList() selectOne()
	   public List<FoodVO> foodListData(int page)
	   {
		   // 페이징 처리 => 인라인뷰 
		   List<FoodVO> list=
				   new ArrayList<FoodVO>();
		   try
		   {
			   // 인덱스 자동화 : PRIMARY KEY , UNIQUE 
			   getConnection();
			   String sql="SELECT fno,name,poster,num "
					     +"FROM (SELECT fno,name,poster,rownum as num "
					     +"FROM (SELECT /*+ INDEX_ASC(menupan_food meunf_fno_pk)*/fno,name,poster "
					     +"FROM menupan_food)) "
					     +"WHERE num BETWEEN ? AND ?";
			   ps=conn.prepareStatement(sql); // 오라클로 전송 
			   int start=(page*ROWSIZE)-(ROWSIZE-1);
			   int end=page*ROWSIZE;
			   /*
			    *     1page => 1~12 => rownum은 1번부터 시작 
			    *     2page => 13~24 
			    */
			   ps.setInt(1, start);
			   ps.setInt(2, end);
			   // 오라클 실행 
			   ResultSet rs=ps.executeQuery();
			   // 결과값이 rs안에 존재 => List에 채운다 
			   while(rs.next())  // 출력한 첫번째 위치에 커서이동 
			   {
				   // JDBC => 읽기 => ROW단위로 읽어 온다
                   // fno , name, poster읽기 
				   // => 한번에 저장 => VO
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
	   // 1-1 총페이지
	   public int foodTotalPage()
	   {
		   int total=0;
		   try
		   {
			   getConnection();
			   String sql="SELECT CEIL(COUNT(*)/"+ROWSIZE+") FROM menupan_food";
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
	   // => 인기 많은 맛집 10개 추출 
	   public List<FoodVO> foodTop10()
	   {
		   List<FoodVO> list=new ArrayList<FoodVO>();
		   try
		   {
			   getConnection();
			   String sql="SELECT fno,name,SUBSTR(address,1,2),poster,rownum "
					     +"FROM (SELECT fno,name,address,poster "
					     +"FROM menupan_food ORDER BY hit DESC) "
					     +"WHERE rownum<=10";
			   ps=conn.prepareStatement(sql);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   FoodVO vo=new FoodVO();
				   vo.setFno(rs.getInt(1));
				   vo.setName(rs.getString(2));
				   vo.setAddress(rs.getString(3));
				   vo.setPoster(rs.getString(4));
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
	   // = 상세보기 
	   /*
	    *   FNO     NOT NULL NUMBER        
		NAME    NOT NULL VARCHAR2(200) 
		TYPE    NOT NULL VARCHAR2(100) 
		PHONE            VARCHAR2(20)  
		ADDRESS NOT NULL VARCHAR2(500) 
		SCORE            NUMBER(2,1)   
		THEME            CLOB          
		PRICE            VARCHAR2(50)  
		TIME             VARCHAR2(100) 
		PARKING          VARCHAR2(100) 
		POSTER  NOT NULL VARCHAR2(260) 
		IMAGES           CLOB          
		CONTENT          CLOB          
		HIT              NUMBER    
	    */
	   public FoodVO foodDetailData(int fno)
	   {
		   FoodVO vo=new FoodVO();
		   try
		   {
			   getConnection();
			   String sql="UPDATE menupan_food SET "
					     +"hit=hit+1 "
					     +"WHERE fno=?";
			   ps=conn.prepareStatement(sql);
			   ps.setInt(1, fno);
			   ps.executeUpdate();
			   
			   sql="SELECT fno,name,type,address,phone,score,"
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
	   // = 검색 
	   /*
	    *     INDEX_ASC(테이블명 PK|UK)
	    *     INDEX_DESC(테이블명 PK|UK)
	    *     INDEX(테이블명 INDEX명)
	    *     
	    *     /*+  , -- : Hint 
	    *     
	    *     WHERE name
	    *     ps.setString(1,name) => 'name' 
	    */
	   public List<FoodVO> foodFindData(String column,String fd,int page)
	   {
		   List<FoodVO> list=
				   new ArrayList<FoodVO>();
		   try
		   {
			   // 연결 
			   getConnection();
			   String sql="SELECT fno,poster,name,num "
					     +"FROM (SELECT fno,poster,name,rownum as num "
					     +"FROM (SELECT /*+ INDEX_ASC(menupan_food menuf_fno_pk)*/fno,poster,name "
					     +"FROM menupan_food "
					     +"WHERE "+column+" LIKE '%'||?||'%')) "
					     +"WHERE num BETWEEN ? AND ?";
			   // => Mybatis => #{fd} JPA => :fd 
			   //               ${fd}
			   // column명 / table명은 ?를 사용하지 않는다 
			   // ? => 실제 데이터값 첨부시에만 사용 
			   ps=conn.prepareStatement(sql);
			   int rowSize=20;
			   int start=(rowSize*page)-(rowSize-1);
			   int end=rowSize*page;
			   
			   // ?에 값을 채운다 
			   ps.setString(1, fd);
			   ps.setInt(2, start);
			   ps.setInt(3, end);
			   
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   FoodVO vo=new FoodVO();
				   vo.setFno(rs.getInt(1));
				   vo.setPoster(rs.getString(2));
				   vo.setName(rs.getString(3));
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
	   public int findCount(String column,String fd)
	   {
		   int count=0;
		   try
		   {
			   getConnection();
			   String sql="SELECT COUNT(*) "
					     +"FROM menupan_food "
					     +"WHERE "+column+" LIKE '%'||?||'%'";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, fd);
			   ResultSet rs=ps.executeQuery();
			   rs.next();
			   count=rs.getInt(1);
			   rs.close();
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
		   return count;
	   }
	   
}





