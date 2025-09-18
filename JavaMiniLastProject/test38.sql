-- PL/SQL 응용 
/*
    ---------------------------
    1. 회원가입 
    2. 맛집 
    3. 찜 / 좋아요 
    4. 게시판 
    5. 근처 => 여행지 
    6. 맛집 뉴스 
    7. 실시간 채팅 
    8. 마이페이지 
   ---------------------------
            id
    회원  -- 찜(매핑테이블) => 3정규화  
                | fno => 이미지 / 맛집명 => Function 
              맛집 
   찜 => 맛집에 jjimcount증가 => Trigger이용 
   마이페이지 => PROCEDURE 
*/
-- 회원
/*
    id = pk
   pwd = nn
   name = nn
   sex = ck 
   post = nn
   addr1 = nn 
   addr2 
   phone = uk 
   ----------------
*/ 
/*
DROP TABLE jjim;
DROP TABLE project_member;
CREATE TABLE project_member(
   id VARCHAR2(20),
   pwd VARCHAR2(10) CONSTRAINT pm_pwd_nn NOT NULL,
   name VARCHAR2(51) CONSTRAINT pm_name_nn NOT NULL,
   sex CHAR(6),
   post VARCHAR2(7) CONSTRAINT pm_post_nn NOT NULL,
   addr1 VARCHAR2(200) CONSTRAINT pm_addr1_nn NOT NULL,
   addr2 VARCHAR2(200),
   phone VARCHAR2(13),
   regdate DATE DEFAULT SYSDATE,
   login CHAR(1) DEFAULT 'n',
   CONSTRAINT pm_id_pk PRIMARY KEY(id),
   CONSTRAINT pm_sex_ck CHECK(sex IN('남자','여자')),
   CONSTRAINT pm_phone_uk UNIQUE(phone),
   CONSTRAINT pm_login_ck CHECK(login IN('y','n'))
);
-- 찜 
CREATE TABLE jjim(
   jno NUMBER,
   fno NUMBER,
   id VARCHAR2(20),
   CONSTRAINT jjim_jno_pk PRIMARY KEY(jno),
   CONSTRAINT jjim_fno_fk FOREIGN KEY(fno)
   REFERENCES menupan_food(fno),
   CONSTRAINT jjim_id_fk FOREIGN KEY(id)
   REFERENCES project_member(id)
);
ALTER TABLE menupan_food ADD jjimcount NUMBER DEFAULT 0;
*/
-- TRIGGER 생성 
CREATE OR REPLACE TRIGGER jjim_trigger
AFTER INSERT OR DELETE ON jjim 
FOR EACH ROW
BEGIN
  IF INSERTING THEN
     UPDATE menupan_food SET
     jjimcount=jjimcount+1 
    WHERE fno=:NEW.fno;
  ELSIF DELETING THEN
     UPDATE menupan_food SET 
     jjimcount=jjimcount-1
     WHERE fno=:OLD.fno;
  END IF;
END;
/
-- FUNCTION 생성 
CREATE OR REPLACE FUNCTION getName(vFno menupan_food.fno%TYPE)
RETURN VARCHAR2
IS 
  vname menupan_food.name%TYPE;
BEGIN
  SELECT name INTO vname
  FROM menupan_food
  WHERE fno=vFno;
  RETURN vname; 
END;
/

CREATE OR REPLACE FUNCTION getPoster(vFno menupan_food.fno%TYPE)
RETURN VARCHAR2
IS 
  vpos menupan_food.poster%TYPE;
BEGIN
  SELECT poster INTO vpos
  FROM menupan_food
  WHERE fno=vFno;
  RETURN vpos; 
END;
/

SELECT getName(1),getPoster(1) FROM menupan_food;