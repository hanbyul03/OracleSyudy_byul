package com.sist.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.JobKoreaDAO;
import com.sist.vo.JobKoreaVO;

public class JobKoreaRegionJsoup {

    public void regionJobDataCollect(String regionParam) {
        JobKoreaDAO dao = JobKoreaDAO.newInstance();

        try {
            // URL 예시 (지역 파라미터는 실제 코드값 확인 필요)
            String url = "https://www.jobkorea.co.kr/recruit/joblist?menucode=local&localorder=1&local=" + regionParam;
            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0")
                                .timeout(10000)
                                .get();

            Elements items = doc.select("div.list-default div.list-info"); // 잡코리아 공고 리스트 셀렉터

            int no = 1;
            for (Element item : items) {
                try {
                    JobKoreaVO vo = new JobKoreaVO();

                    Element companyEl = item.selectFirst("a.coLink");
                    if (companyEl != null) vo.setCompany(companyEl.text().trim());

                    Element titleEl = item.selectFirst("a.title");
                    if (titleEl != null) {
                        vo.setTitle(titleEl.text().trim());
                        vo.setLink("https://www.jobkorea.co.kr" + titleEl.attr("href"));
                    }

                    // info: 경력, 학력, 근무지, 고용형태, 연봉 (보통 순서가 고정임)
                    Elements infoEls = item.select("div.jobCondition span");
                    vo.setCareer(infoEls.size() > 0 ? infoEls.get(0).text().trim() : "");
                    vo.setEducation(infoEls.size() > 1 ? infoEls.get(1).text().trim() : "");
                    vo.setLocation(infoEls.size() > 2 ? infoEls.get(2).text().trim() : "");
                    vo.setEmpType(infoEls.size() > 3 ? infoEls.get(3).text().trim() : "");
                    vo.setSalary(infoEls.size() > 4 ? infoEls.get(4).text().trim() : "");

                    Element regdateEl = item.selectFirst("span.regDate");
                    vo.setRegdate(regdateEl != null ? regdateEl.text().trim() : "");

                    Element deadlineEl = item.selectFirst("span.deadline");
                    vo.setDeadline(deadlineEl != null ? deadlineEl.text().trim() : "");

                    Element skillEl = item.selectFirst("div.jobTagList");
                    vo.setSkill(skillEl != null ? skillEl.text().trim() : "");

                    Element logoEl = item.selectFirst("a.coLink img");
                    vo.setLogo(logoEl != null ? logoEl.attr("src") : "");

                    // viewCount, scrapCount는 크롤링에서 없으면 0으로 세팅
                    vo.setViewCount(0);
                    vo.setScrapCount(0);

                    dao.insertJob(vo);
                    System.out.println("저장 완료: " + vo.getTitle() + " | " + vo.getCompany());

                    no++;
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("✅ 잡코리아 지역별 채용공고 크롤링 완료!");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JobKoreaRegionJsoup jsoup = new JobKoreaRegionJsoup();
        jsoup.regionJobDataCollect("SEOUL"); // 예: 서울 지역
    }
}
