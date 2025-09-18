<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.vo.*,com.sist.dao.*"%>
<%
    // 자바 코딩 => main
    // 1. 사용자 보내준 데이터를 받는다 (페이지)
    String strPage=request.getParameter("page");
    if(strPage==null) // 처음 실행시 
       strPage="1";
    int curpage=Integer.parseInt(strPage);
    // DB에서 데이터 받기 
    FoodDAO2 dao=FoodDAO2.newInstance();
    List<FoodVO> list=dao.foodListData(curpage);
    // 총페이지 받기 
    int totalpage=dao.foodTotalPage();
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container {
   margin-top: 50px;
}
.row {
  margin: 0px auto;
  width: 960px;
}
p {
     overflow: hidden;
     white-space: nowrap;
     text-overflow: ellipsis;
}
</style>
</head>
<body>
    <div class="container">
      <div class="row">
        <%
           for(FoodVO vo:list)
           {
        %>
               <div class="col-md-3">
			    <div class="thumbnail">
			      <a href="detail.jsp?fno=<%=vo.getFno()%>">
			        <img src="<%=vo.getPoster() %>" alt="Lights" 
			        style="width:230px;height: 150px">
			        <div class="caption">
			          <p><%=vo.getName() %></p>
			        </div>
			      </a>
			    </div>
			  </div>
        <%
           }
        %>
      </div>
      <div style="height: 10px"></div>
      <div class="row text-center">
        <a href="list.jsp?page=<%=curpage>1?curpage-1:curpage %>"
           class="btn btn-sm btn-danger">이전</a>
           <%=curpage %> page / <%=totalpage %> pages
        <a href="list.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>"
           class="btn btn-sm btn-danger">다음</a>
      </div>
    </div>
</body>
</html>