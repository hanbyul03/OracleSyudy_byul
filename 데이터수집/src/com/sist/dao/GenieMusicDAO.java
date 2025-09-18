package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

import com.sist.vo.GenieMusicVO;
// => Adapter Pattern 
public class GenieMusicDAO {
	   private Connection conn; // 오라클 연결 
	   private PreparedStatement ps; // 송수신 => SQL 전송 => 결과값을 메모리 저장
	   private static GenieMusicDAO dao; // DAO객체를 한사람당 1개씩만 사용 
	   // 메모리 공간을 한개만 생성 => 메모리 누수현상을 방지 = 싱글턴 
	   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	   // 변경할 수 없다 ======================>@localhost (ip) port:1521 XE:데이터베이스(폴더)
	   // se(XE) pe(ORCL) 
	   private final int rowSize=10;
	   // 1. 드라이버 등록 
	   public GenieMusicDAO()
	   {
		   try
		   {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			   // 리플렉션 => 클래스이름 변수,메소드,생성자 => 제어할 수 있게 만든 프로그램 
			   // MyBatis / Spring 
			   // oracle.jdbc.driver.OracleDriver = DriverManager
		   }catch(Exception ex) {}
	   }
	   // 2. 싱글턴 => 반드시 static : 메모리 공간이 한개만 생성 
	   /*
	    *   가비지컬렉션 : 사용하지 않거나 / null 인 상태의 객체의 메모리 회수 
	    *   ---------- 메모리가 커지면서 역할을 잘 못한다 : 프로그램 종료시 회수 
	    */
	   public static GenieMusicDAO newInstance()
	   {
		   if(dao==null)
			   dao=new GenieMusicDAO();
		   return dao;
	   }
	   // 반복 제거 => 연결 / 해제 
	   // 메소드는 한개의 기능을 가지고 있다 
	   // 반복코딩 / 재사용 / 다른 클래스 통신 / 단락 나누기 
	   public void getConnection()
	   {
		   try
		   {
			   conn=DriverManager.getConnection(URL,"hr","happy");
		   }catch(Exception ex) {}
	   }
	   public void disConnection()
	   {
		   try
		   {
			   if(ps!=null) ps.close();
			   // OutputStream / BufferedReader 
			   if(conn!=null) conn.close();
			   // conn => Socket
		   }catch(Exception ex) {}
	   }
	   /*
	    *    NO                                        NOT NULL NUMBER
			 CNO                                                NUMBER
			 RANK                                      NOT NULL NUMBER
			 TITLE                                     NOT NULL VARCHAR2(200)
			 SINGER                                    NOT NULL VARCHAR2(100)
			 ALBUM                                     NOT NULL VARCHAR2(200)
			 POSTER                                    NOT NULL VARCHAR2(260)
			 STATE                                              CHAR(6)
			 IDCREMENT                                          NUMBER
			 KEY                                                VARCHAR2(100)
			 HIT                                                NUMBER
			 LIKECOUNT     
	    */
	   public void genieInsert(GenieMusicVO vo)
	   {
		   try
		   {
			   getConnection();
			   String sql="INSERT INTO genie_music(no,cno,rank,title,"
					      +"singer,album,poster,state,idcrement) "
					      +"VALUES(gm_no_seq.nextval,?,?,?,?,?,?,?,?)";
			   ps=conn.prepareStatement(sql);
			   ps.setInt(1, vo.getCno());
			   ps.setInt(2, vo.getRank());
			   ps.setString(3, vo.getTitle());
			   ps.setString(4, vo.getSinger());
			   ps.setString(5, vo.getAlbum());
			   ps.setString(6, vo.getPoster());
			   ps.setString(7, vo.getState());
			   ps.setInt(8, vo.getIdcrement());
			   ps.executeUpdate();
			   
		   }catch(Exception ex)
		   {
			   ex.printStackTrace();
		   }
		   finally
		   {
			   disConnection();
		   }
	   }
	   public List<GenieMusicVO> genieListData(int cno)
	   {
		   List<GenieMusicVO> list=
				   new ArrayList<GenieMusicVO>();
		   try
		   {
			   getConnection();
			   String sql="SELECT /*+ INDEX_ASC(genie_music gm_no_pk)*/no,rank,title,singer,album,"
					   +"poster,idcrement,state "
					   +"FROM genie_music"
					   +"WHERE cno=?";
			   ps=conn.prepareStatement(sql);
			   ps.setInt(1, cno);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   GenieMusicVO vo=new GenieMusicVO();
				   vo.setNo(rs.getInt(1));
				   vo.setRank(rs.getInt(2));
				   vo.setTitle(rs.getString(3));
				   vo.setSinger(rs.getString(4));
				   vo.setAlbum(rs.getString(5));
				   vo.setPoster(rs.getString(6));
				   vo.setIdcrement(rs.getInt(7));
				   vo.setState(rs.getString(8));
				   list.add(vo);
			   }
		   rs.close();
	   }catch (Exception ex) 
		   {
		   ex.printStackTrace();
		   }
		   finally
		   {
			   
		   }
		   return list;
		   }
	   
}



