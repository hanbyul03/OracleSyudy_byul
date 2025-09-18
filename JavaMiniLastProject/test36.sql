-- 트리거 : PS/SQL => Function / Procedure / Trigger
/*
     트리거 : 특정 이벤트 (INSERT / UPDATE / DELETE)가 발생했을 경우에 
                 자동 저장 프로시저 
     특징 
         테이블/뷰에 연결 
         이벤트가 발생하면 자동호출 
         INSERT / UPDATE / DELETE에서만 사용이 가능 
         로그파일 / 백업시에 주로 사용 
         입고 => 재고 자동화 / 출고 => 재고 자동화 
         *** 맛집 => 찜 => 찜갯수 증가
                         ---- 맛집 번호 => Function 이용 
         *** 프로시저 : 댓글 (웹사이트) 
     형식)
              CREATE [OR REPLACE] TRIGGER tri_name 
              {BEFORE|AFTER} {이벤트:INSERT | UPDATE |DELETE} ON 어떤 테이블
              모든 ROW에 적용 
              {FOR EACH ROW}
              BEGIN 
                트리거 실행 문장 => 입고 
                                            -------
                                            존재 상품 ==> UPDATE 
                                            비존재 상품 ==> INSERT
                                             출고 
                                            -------
                                             => 수량이 0이면 => DELETE
                                             => UPDATE 
              END;
             ---------------------------------------------------------------
               시점 : 언제 트리거 처리 
               BEFORE : DML 실행전에 동작 => 발생한 테이블 저장전에 trigger수행
               AFTER : DML실행후에 동작 => 발생한 테이블 저장후에 trigger수행

               행단위 / 문 단위 
               각행마다 실행 : FOR EACH ROW (default)
               SQL문 전체에 한번만 수행 : 생략 

               컬럼 
                새로 들어오는 값 => :NEW.컬럼 (INSERT/UPDATE)
                기존에 있는 값 => :OLD.컬럼 (UPDATE/DELETE)
                예)
                      입고 (상품번호 , 수량 , 단가) 
                      => INSERT INTO 입고 VALUES(1,5,1500)
                                                              ------------- 새로운 값
                           :NEW.수량 

*/
-- emp 
/*
CREATE TABLE emp_trg
AS 
  SELECT empno,ename,sal,deptno 
  FROM emp;
*/
/*
ALTER TABLE emp_trg ADD CONSTRAINT et_empno_pk PRIMARY KEY(empno);
-- 로그 테이블 
CREATE TABLE emp_log(
   log_id NUMBER,
   empno NUMBER,
   action VARCHAR2(20),
   log_date DATE DEFAULT SYSDATE,
   CONSTRAINT el_id_pk PRIMARY KEY(log_id),
   CONSTRAINT el_empno_fk FOREIGN KEY(empno)
   REFERENCES emp_trg(empno)  
);
CREATE SEQUENCE el_id_seq
     START WITH 1
     INCREMENT BY 1
     NOCYCLE
     NOCACHE;
*/
-- 트리거는 자동으로 COMMIT => COMMIT을 사용하면 오류 발생 
/*
CREATE OR REPLACE TRIGGER log_insert
AFTER INSERT ON emp_trg
FOR EACH ROW
BEGIN
   INSERT INTO emp_log VALUES(el_id_seq.nextval,:NEW.empno,
   'INSERT',SYSDATE);
END;
/
INSERT INTO emp_trg VALUES((SELECT NVL(MAX(empno)+1,1) FROM emp_trg),
  '홍길동',3000,10);
COMMIT;
SELECT * FROM emp_trg;
*/
-- UPDATE 
-- 유효성 검사  -- SQL문장 검색 , 데이터 추가 (무결성 체크)
/*
CREATE OR REPLACE TRIGGER trg_update
BEFORE UPDATE OF sal ON emp_trg
FOR EACH ROW 
BEGIN
   IF :NEW.sal > :OLD.sal * 2 THEN
     RAISE_APPLICATION_ERROR(-21000,'급여는 기존의 2배를 넘을 수 없다');
   END IF;   
END;
/
UPDATE emp_trg SET 
sal=7000
WHERE empno=7935;
*/
/*
CREATE TABLE emp_backup
AS
  SELECT * FROM emp_trg;
*/

CREATE OR REPLACE TRIGGER tri_backup
BEFORE DELETE ON emp_trg
FOR EACH ROW -- 전체 추가 
BEGIN
  INSERT INTO emp_backup(empno,ename,sal,deptno)
  VALUES(:OLD.empno,:OLD.ename,:OLD.sal,:OLD.deptno);
END;
/
DELETE FROM emp_log 
WHERE empno=7935;
DELETE FROM emp_trg
WHERE empno=7935;

SELECT * FROM emp_trg;
SELECT * FROM emp_backup;
-- 삭제전 (BEFORE DELETE) => 삭제전에 이전 데이터를 보관 (:OLD) 
DROP TRIGGER tri_backup;
DROP TRIGGER trg_update;
DROP TRIGGER log_insert;
DROP TABLE emp_log;
DROP TABLE emp_backup;
DROP TABLE emp_trg;