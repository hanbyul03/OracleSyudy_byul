package com.sist.jsoup;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.*;
import com.sist.vo.*;
public class GenieMusicJsoup {
   public void genieDataCollection()
   {
	  GenieMusicDAO dao=GenieMusicDAO.newInstance();
	  try
	  {
	   String[] genre={"","M0100","M0200","M0300","M0107","M0500","M0600"};
	   for(int i=0;i<genre.length;i++)
	   {
		   int r=1;
		   String url="";
		  for(int p=1;p<=2;p++)
		  {
		     if(i==0)
		     {
			    url="https://www.genie.co.kr/chart/top200?ditc=D&ymd=20250917&hh=14&rtm=Y&pg="+p;
		     }
		     else
		     {
		    	 url="https://www.genie.co.kr/chart/genre?ditc=D&ymd=20250916&genrecode="+genre[i]+"&pg="+p;
		     }
		     
		     Document doc=Jsoup.connect(url).get();
		     Elements title=doc.select("table.list-wrap td.info a.title");
		     Elements singer=doc.select("table.list-wrap td.info a.artist");
		     Elements poster=doc.select("table.list-wrap a.cover img");
		     Elements album=doc.select("table.list-wrap td.info a.albumtitle");
		     Elements etc=doc.select("table.list-wrap td.number span.rank");
		     
		     /*
		      *   HTML => 데이터 출력 
		      *   -----------------
		      *   <a>aaa</a> => text()
		      *   <img src="aaa"> => attr("src")
		      *   <a href="">
		      */
		     for(int j=0;j<title.size();j++)
		     {
		    	 try
		    	 {
		    		 // 데이터 추출 
		    		 System.out.println(genre[i]);
		    		 System.out.println(title.get(j).text()
		    		   +" "+singer.get(j).text()
		    		   +" "+album.get(j).text()
		    		   +" "+poster.get(j).attr("src")
		    		   +" "+etc.get(j).text()
		    		 );
		    		 System.out.println("---------------------------------------");
		    		 GenieMusicVO vo=new GenieMusicVO();
		    		 vo.setCno(i+1);
		    		 vo.setRank(r++);
		    		 vo.setTitle(title.get(j).text());
		    		 vo.setSinger(singer.get(j).text());
		    		 vo.setAlbum(album.get(j).text());
		    		 vo.setPoster("http:"+poster.get(j).attr("src"));
		    		 String s=etc.get(j).text();
		    		 int id=0;
		    		 String state="";
		    		 if(s.equals("유지"))
		    		 {
		    			 id=0;
		    			 state="유지";
		    		 }
		    		 else
		    		 {
		    			 state=s.replaceAll("[0-9]", "");
		    			 id=Integer.parseInt(s.replaceAll("[가-힣]", ""));
		    		 }
		    		 vo.setState(state);
		    		 vo.setIdcrement(id);
		    		 
		    		 // db연동 
		    		 dao.genieInsert(vo);
		    	 }catch(Exception ex) {}
		     }
		  }
	   }
	   System.out.println("데이터수집 완료!!");
	  }catch(Exception ex){}
   }
   public static void main(String[] args) {
	    GenieMusicJsoup gm=new GenieMusicJsoup();
	    //gm.genieDataCollection();
   }
}