package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class MemberDAO {
   private Connection conn;
   private PreparedStatement ps;
   //                                         211.238.142.22
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   private static MemberDAO dao;
   
   // 드라이버 등록 
   public MemberDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex) {}
   }
   public static MemberDAO newInstance()
   {
	   if(dao==null)
		   dao=new MemberDAO();
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
   
   // 1. 로그인 
   public MemberVO isLogin(String id,String pwd)
   {
	   MemberVO vo=new MemberVO();
	   try
	   {
		   getConnection();
		   // 1. SQL => ID존재 여부 => COUNT
		   String sql="SELECT COUNT(*) "
				     +"FROM project_member "
				     +"WHERE id=?";
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, id);
		   // 결과값 받기 
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   int count=rs.getInt(1);
		   rs.close();
		   
		   if(count==0) //ID가 없는 상태 
		   {
			   vo.setMsg("NOID");
		   }
		   else // ID가 있는 상태 
		   {
			   // 비밀번호 확인 
			   sql="SELECT id,pwd,name,sex "
			      +"FROM project_member "
				  +"WHERE id=?";
			   ps=conn.prepareStatement(sql);
			   ps.setString(1, id);
			   rs=ps.executeQuery();
			   rs.next();
			   String db_id=rs.getString(1);
			   String db_pwd=rs.getString(2);
			   String name=rs.getString(3);
			   String sex=rs.getString(4);
			   rs.close();
			   
			   if(db_pwd.equals(pwd))//로그인 
			   {
				   vo.setId(db_id);
				   vo.setName(name);
				   vo.setSex(sex);
				   vo.setMsg("OK");
			   }
			   else // 비밀번호가 틀린 상태  
			   {
				   vo.setMsg("NOPWD");
			   }
			   
			   
		   }
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
   // 2. 아이디 중복 체크 
   public int memberIdCheck(String id)
   {
	   int count=0;
	   try
	   {
		   getConnection();
		   String sql="SELECT COUNT(*) "
				     +"FROM project_member "
				     +"WHERE id=?";
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, id);
		   // 결과값 받기 
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
   // 3. 우편번호 검색 
   public List<ZipcodeVO> postFind(String dong)
   {
	   List<ZipcodeVO> list=
			   new ArrayList<ZipcodeVO>();
	   try
	   {
		   getConnection();
		   String sql="SELECT zipcode,sido,gugun,dong,"
				     +"NVL(bunji,' ') "
				     +"FROM zipcode "
				     +"WHERE dong LIKE '%'||?||'%'";
		   // 오라클SQL != 자바SQL => LIKE
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, dong);
		   // 결과값 받기 
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   ZipcodeVO vo=new ZipcodeVO();
			   vo.setZipcode(rs.getString(1));
			   vo.setSido(rs.getString(2));
			   vo.setGugun(rs.getString(3));
			   vo.setDong(rs.getString(4));
			   vo.setBunji(rs.getString(5));
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
   public int postFindCount(String dong)
   {
	   // count=0 => 검색 결과가 없다 
	   int count=0;
	   try
	   {
		   getConnection();
		   String sql="SELECT COUNT(*) "
				     +"FROM zipcode "
				     +"WHERE dong LIKE '%'||?||'%'";
		   // 오라클SQL != 자바SQL => LIKE
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, dong);
		   // 결과값 받기 
		   ResultSet rs=ps.executeQuery();
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
   // 4. 전화번호 검색 => PRIMARY KEY , UNIQUE (NULL 허용) 
   // ID를 모는 경우 => 전화번호로 찾는다 
   public int memberPhoneCheck(String phone)
   {
	   int count=0;
	   try
	   {
		   getConnection();
		   String sql="SELECT COUNT(*) "
				     +"FROM project_member "
				     +"WHERE phone=?";
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, phone);
		   // 결과값 받기 
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
   // 5. 회원가입 => 회원 수정 => 회원 정보 출력 => 회원 탈퇴
   /*
    *   ID      NOT NULL VARCHAR2(20)  
		PWD     NOT NULL VARCHAR2(10)  
		NAME    NOT NULL VARCHAR2(52)  
		SEX              CHAR(6)       
		POST    NOT NULL VARCHAR2(7)   
		ADDR1   NOT NULL VARCHAR2(500) 
		ADDR2            VARCHAR2(100) 
		PHONE            VARCHAR2(13)  
		CONTENT          CLOB          
		REGDATE          DATE 
    */
   public void memberJoin(MemberVO vo)
   {
	   try
	   {
		   getConnection();
		   String sql="INSERT INTO project_member "
				     +"VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
		   ps=conn.prepareStatement(sql);
		   //?에 값을 채운다 
		   ps.setString(1, vo.getId());
		   ps.setString(2, vo.getPwd());
		   ps.setString(3, vo.getName());
		   ps.setString(4, vo.getSex());
		   ps.setString(5, vo.getPost());
		   
		   ps.setString(6, vo.getAddr1());
		   ps.setString(7, vo.getAddr2());
		   ps.setString(8, vo.getPhone());
		   ps.setString(9, vo.getContent());
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
   // 옵션 6. ID찾기 / 비밀번호 찾기  
   
}







