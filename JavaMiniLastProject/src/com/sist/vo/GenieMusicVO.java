package com.sist.vo;

import lombok.Data;

/*
 *   NO                                        NOT NULL NUMBER
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
	 LIKECOUNT                                          NUMBER
 */
@Data
public class GenieMusicVO {
    private int no,cno,hit,rank,likecount,idcrement;
    private String title,singer,album,poster,state,key;
}