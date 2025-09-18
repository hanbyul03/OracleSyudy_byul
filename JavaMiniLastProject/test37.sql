-- 트리거 
/*
DROP TABLE 상품;
DROP TABLE 입고;
DROP TABLE 출고;
DROP TABLE 재고;
CREATE TABLE 상품(
   품번 NUMBER,
   상품명 VARCHAR2(30),
   단가 NUMBER
);
CREATE TABLE 입고(
   품번 NUMBER,
   수량 NUMBER,
   금액 NUMBER
); 
CREATE TABLE 출고(
   품번 NUMBER,
   수량 NUMBER,
   금액 NUMBER
); 
-- 자동화 처리
CREATE TABLE 재고(
   품번 NUMBER,
   수량 NUMBER,
   금액 NUMBER,
   누적금액 NUMBER
); 
*/
/*
     주의점 
      트리거안에서 DML 실행시 참조시에 무한루프
      복잡한 SQL문장은 트리거보다 프로시저를 권장 
      트리거는 디버깅이 어렵다 (테스트를 많이 해야 된다)
      => 키워드 
           INSERTING / UPDATING / DLETEING
                                               | DELETE문장이 실행중 
                             | UPDATE 실행중 
               | INSERT 문장이 실행중 
           => BOOLEAN 
      => IF INSERTING THEN 
           ELSIF UPDATING THEN 
           ELSIF DELETING THEN 
*/
-- 입고 : INSERT / UPDATE / DELETE  
-- 출고 : INSERT / UPDATE / DELETE 
/*
CREATE TABLE emp_trg(
   empno NUMBER, 
   ename VARCHAR2(30),
   sal NUMBER,
   deptno NUMBER
);
*/
SET SERVEROUTPUT ON;
/*
CREATE OR REPLACE TRIGGER tri_test
AFTER INSERT OR UPDATE OR DELETE ON emp_trg
FOR EACH ROW
BEGIN
  IF INSERTING THEN 
    DBMS_OUTPUT.PUT_LINE('INSERT 발생:'||:NEW.ename);
  ELSIF UPDATING THEN 
    DBMS_OUTPUT.PUT_LINE('UPDATE 발생:'||:OLD.ename||' '||:NEW.ename);
  ELSIF DELETING THEN  
    DBMS_OUTPUT.PUT_LINE('DELETE 발생:'||:OLD.ename);
  END IF;
END;
/
*/
/*
    INSERT => :NEW
    UPDATE => :OLD , :NEW
    DELETE => :OLD
*/
/*
INSERT INTO emp_trg VALUES(1,'홍길동',3000,10);
COMMIT;
*/
/*
INSERT INTO 상품 VALUES(100,'새우깡',1500);
INSERT INTO 상품 VALUES(200,'감자깡',1000);
INSERT INTO 상품 VALUES(300,'맛동산',2000);
INSERT INTO 상품 VALUES(400,'짱구',500);
INSERT INTO 상품 VALUES(500,'고구마깡',2500);
COMMIT;
*/
-- 입고 
/*
CREATE OR REPLACE TRIGGER input_trigger
AFTER INSERT ON 입고 
FOR EACH ROW 
DECLARE 
  -- 변수 선언 
  v_cnt NUMBER:=0;
BEGIN
  SELECT COUNT(*) INTO v_cnt 
  FROM 재고 
  WHERE 품번=:NEW.품번; 
  IF v_cnt=0 THEN -- 기존의 상품이 없는 경우 
    INSERT INTO 재고 VALUES(:NEW.품번,:NEW.수량,:NEW.금액,:NEW.수량*:NEW.금액);
  ELSE -- 기존의 상품이 있는 경우 
    UPDATE 재고 SET 
    수량=수량+:NEW.수량,
    누적금액 =누적금액+(:NEW.수량*:NEW.금액)
    WHERE 품번=:NEW.품번;
  END IF;
END;
/
*/
/*
INSERT INTO 입고 VALUES(100,3,1500);
COMMIT;
SELECT * FROM 입고;
SELECT * FROM 재고;
*/
/*
    입고 ====== 재고 
                      상품 존재 => 수량 / 누적 금액 변경 => UPDATE
                      상품 미존재 => INSERT 추가 
    출고 ======= 재고 
                       상품이 몇개 
                       0 : DELETE 
                     >0 : UPDATE
*/
/*
CREATE OR REPLACE TRIGGER output_trigger
AFTER INSERT ON 출고 
FOR EACH ROW 
DECLARE
  v_cnt NUMBER:=0;
BEGIN
  SELECT 수량 INTO v_cnt
  FROM 재고 
  WHERE 품번=:NEW.품번;
  IF :NEW.수량=v_cnt THEN
     DELETE FROM 재고 
     WHERE 품번=:NEW.품번;
  ELSE
     UPDATE 재고 SET
     수량=수량-:NEW.수량, 
     누적금액=누적금액-(:NEW.수량*:NEW.금액)
     WHERE 품번=:NEW.품번;
  END IF;
END;
/
*/
INSERT INTO 출고 VALUES(100,3,1500);
COMMIT;