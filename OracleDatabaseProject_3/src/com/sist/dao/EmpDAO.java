package com.sist.dao;
import java.util.*;
import java.sql.*;

import com.sist.db.CreateDataBase;
import com.sist.vo.*;
public class EmpDAO {
  private static EmpDAO dao;
  private CreateDataBase db=new CreateDataBase();
  // 드라이버 등록 / 오라클 연결 / 오라클 해제
  public static EmpDAO newInstance()
  {
	  if(dao==null)
		  dao=new EmpDAO();
	  return dao;
  }
  // SELECT empno,ename,job,hiredate FROM emp
  public List<EmpVO> empListData()
  {
	  List<EmpVO> list=new ArrayList<EmpVO>();
	  Connection conn=null;
	  PreparedStatement ps=null;
	  try
	  {
		  conn=db.getConnection();
		  String sql="SELECT empno,ename,job,hiredate,sal "
				    +"FROM emp";
		  ps=conn.prepareStatement(sql);
		  ResultSet rs=ps.executeQuery();
		  while(rs.next())
		  {
			  EmpVO vo=new EmpVO();
			  vo.setEmpno(rs.getInt(1));
			  vo.setEname(rs.getString(2));
			  vo.setJob(rs.getString(3));
			  vo.setHiredate(rs.getDate(4));
			  vo.setSal(rs.getInt(5));
			  list.add(vo);
		  }
		  rs.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  db.disConnection(conn, ps);
	  }
	  return list;
  }
}