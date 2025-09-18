package com.sist.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupMain2 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
        String html="<div class=\"areaBasic\">"
        		+ "		<dl class=\"restName\">"
        		+ "			<dt>업체명<span style=\"color:#ffffff;cursor:default;\" ondblclick=\"$('#id_basicdata_reporter').toggle();\">.</span></dt>"
        		+ "			<dd class=\"name\">아름드리카페&nbsp;&nbsp;<span id=\"id_basicdata_reporter\" style=\"display:none\">[D200342 : 제휴영업팀]</span></dd>"
        		+ "		</dl>"
        		+ "		<dl class=\"restType\">"
        		+ "			<dt>업종</dt>"
        		+ "			<dd class=\"type\">카페/주점-카페</dd>"
        		+ "		</dl>"
        		+ ""
        		+ "		<dl class=\"restTel\">"
        		+ "			<dt>전화번호</dt>"
        		+ "			<dd class=\"tel1\">(070) 8872-4418</dd>"
        		+ ""
        		+ "		</dl>"
        		+ "		<dl class=\"restAdd\">"
        		+ "			<dt>주소</dt>"
        		+ "			<dd class=\"add1\"><a href=\"/map/restmap/map_search.asp?acode=D200342\" target=\"_blank\">강원 동해시 평릉동 487-1</a></dd>"
        		+ ""
        		+ "			<dd class=\"add2\">[새주소] <a href=\"/map/restmap/map_search.asp?acode=D200342\" target=\"_blank\">강원 동해시 평원5길 4</a></dd>"
        		+ ""
        		+ "		</dl>"
        		+ ""
        		+ "		<dl class=\"restGrade\">"
        		+ "			<dt>평점</dt>"
        		+ "			<dd class=\"rate\">"
        		+ "				<p class=\"point\"><span class=\"star\" style=\"width:60%\"></span><!-- ☆☆☆☆☆ //--></p>"
        		+ "				<p class=\"score\"><span class=\"total\">3.0</span><span class=\"line\">|</span><span class=\"count\">1명 참여</span></p>"
        		+ "			</dd>"
        		+ "			<dd class=\"btnPoint\">"
        		+ ""
        		+ "				<a href=\"javascript:;\" onClick=\"fn_Openmember();\"><img src=\"/image/restaurant/onepage/btn_point.gif\" alt=\"평가하기\" /></a>"
        		+ ""
        		+ "			</dd>"
        		+ "		</dl>"
        		+ ""
        		+ "		<dl class=\"restTheme\">"
        		+ "			<dt>테마</dt>"
        		+ "			<dd class=\"Theme\">"
        		+ "<a href=\"/Restaurant/theme/theme_main.asp?thm_cd=TA04002\" target=\"_blank\">일상데이트</a>"
        		+ ", <a href=\"/Restaurant/theme/theme_main.asp?thm_cd=TA06002\" target=\"_blank\">더운날</a>"
        		+ ", <a href=\"/Restaurant/theme/theme_main.asp?thm_cd=TA07002\" target=\"_blank\">야외테라스</a>"
        		+ ", <a href=\"/Restaurant/theme/theme_main.asp?thm_cd=TA09002\" target=\"_blank\">포장 가능</a>"
        		+ ""
        		+ "			</dd>"
        		+ "		</dl>";
             Document doc=Jsoup.parse(html);
             System.out.println(doc.toString());
             System.out.println("업체명:"+doc.selectFirst("dd.name").text());
             System.out.println("테마:"+doc.selectFirst("dd.Theme").text());
             
	}

}