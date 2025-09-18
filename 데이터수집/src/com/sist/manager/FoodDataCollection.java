package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.FoodDAO;
import com.sist.vo.FoodVO;

public class FoodDataCollection {
   
	// food 수집
	// genie / melon
	// recipe / chef 
	// 서울 여행 / 부산 여행 
	// 위키북스 
	public void foodCollection()
	{
		try
		{
			FoodDAO dao=FoodDAO.newInstance();
			for(int i=1;i<=346;i++)
			{
				Document doc=Jsoup.connect("https://www.menupan.com/restaurant/bestrest/bestrest.asp?page="+i+"&trec=8630&pt=rt").get();
				Elements link=doc.select("p.listName span.restName a");
				for(int j=0;j<link.size();j++)
				{
				  try
				  {
					System.out.println("https://www.menupan.com"
				                       +link.get(j).attr("href"));
					String url="https://www.menupan.com"
		                       +link.get(j).attr("href");
					
					Document doc2=Jsoup.connect(url).get();
					Element name=doc2.selectFirst("div.areaBasic dd.name");
					//System.out.println(name.text());
					String strName=name.text();
					int n=strName.indexOf("[");
					if(n>=0)
					{
						strName=strName.substring(0,strName.indexOf("["));
					}
					System.out.println("업체명:"+strName.trim());
					
					Element type=doc2.selectFirst("div.areaBasic dd.type");
					System.out.println("업종:"+type.text());
					
					Element phone=doc2.selectFirst("div.areaBasic dd.tel1");
					System.out.println("전화:"+phone.text());
					
					Element address=doc2.selectFirst("div.areaBasic dd.add1");
					System.out.println("주소:"+address.text());
					
					Element score=doc2.selectFirst("div.areaBasic p.score span.total");
					System.out.println("평점:"+score.text());
					
					Element theme=doc2.selectFirst("div.areaBasic dd.theme");
					System.out.println("테마:"+theme.text());
					
					Element price=doc2.selectFirst("div.areaBasic p.price");
					System.out.println("가격대:"+price.text());
					
					Element time=doc2.selectFirst("div.infoTable ul.tableTopA dd.txt2");
					System.out.println("영업시간:"+time.text());
					
					Element content=doc2.selectFirst("div.infoTable ul.tableBottom div#info_ps_f");
					System.out.println("소개:"+ content.text());
					
					Elements etc=doc2.select("div.infoTable ul.tableLR dt");
					String parking="";
					for(int k=0;k<etc.size();k++)
					{
						try
						{
							if(etc.get(k).text().equals("주차"))
							{
								parking=
									doc2.select("div.infoTable ul.tableLR dd").get(k).text();
								System.out.println("주차:"+parking);
							}
						}catch(Exception ex){}
					}
					
					Element poster=doc2.selectFirst("div.areaThumbnail img#rest_bigimg");
					System.out.println("메인이미지:"+poster.attr("src"));
					/*
					 * 	 $('div#id_restphoto_slides').click()
					 * 
					 * 	 div#id_restphoto_slides{
					 * 	    background:'white'
					 * 	 }
					 * 
					 * 	 String img="a,b,c,d,e,g"
					 * 	 StringTokenizer st=new StringTokenizer(img,",")
					 * 	 while(st.hasMoeTokens())
					 * 	 {
					 * 	   <td><img src="st.nextToken()"></td>
					 * 	 }
					 */
					Elements images=doc2.select("div#id_restphoto_slides li img");
					String strImage="";
					for(int m=0;m<images.size();m++)
					{
						//System.out.println(images.get(m).attr("src"));
						strImage+="https://www.menupan.com"+images.get(m).attr("src")+",";
					}
					strImage=strImage.substring(0,strImage.lastIndexOf(","));
					
					FoodVO vo=new FoodVO();
					vo.setName(strName);
					vo.setType(type.text());
					vo.setAddress(address.text());
					vo.setParking(parking);
					vo.setContent(content.text());
					vo.setImages(strImage);
					vo.setPhone(phone.text());
					vo.setPrice(price.text());
					vo.setScore(Double.parseDouble(score.text()));
					vo.setTheme(theme.text());
					vo.setPoster("https://www.menupan.com"+poster.attr("src"));
					vo.setTime(time.text());
					dao.foodInsert(vo);
					// insert 
				  }catch(Exception ex) {}
					
				}
			}
			System.out.println("데이터 수집 완료!!");
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		FoodDataCollection f=new FoodDataCollection();
		f.foodCollection();
	}
}