package com.sist.lamda;
/*   
 *      |
 *   JDK 1.8 => SUN => 오라클 인수
 *      |
 *   람다식 : 표현식 => JDK 1.8이상
 *   1. 익명의 함수를 만드는 방식
 *      -------- 함수명이 없다 ()->{} (a,b)->a+b
 *   2. 메소드를 하나의 식으로 간단하게 표현하는 방식
 *   3. 자바는 함수형 프로그램을 지원하기 위해 도입 
 *           -----------
 *   = 람다식 기본 문법
 *     1) 일반 형태
 *        (int a)->{구현}
 *     2) 매개변수의 타입 생략
 *        (a)->{구현}
 *     3) 매개변수가 1개인 경우 => 괄호 생략이 가능
 *        a->{구현}
 *     4) 구현의 {} 생략이 가능
 *        a->System.out.println(a)
 *    --------------------------------------
 *    리턴형이 있는 경우
 *    (x,y) -> {return x+y}
 *    리턴형이 생략 경우
 *    (x,y) -> x+y
 *   = 함수형 인터페이스
 *     = 람다식은 하나의 추상메소드를 가진 인터페이스로만 사용이 가능
 *             -------------
 *       ActionListener => actionPerformed()
 *       Runnable => run()
 *     = @FunctionalInterface     
 */
@FunctionalInterface
interface 계산기{
	public void calc(int a,int b);
}
@FunctionalInterface
interface MyFunction{
	public void display();
}
// => 사용 빈도가 거의 없다
/*
 *   람다식 : 함수명이 존재하지 않는다
 *          -----------------
 *          익명의 함수를 간단하게 표현하는 문법
 *         ()->{구현부} => 함수명(X), return(X)
 *         
 *         let aaa=()=>{} => function / return
 *         함수형 인터페이스를 만드는 경우 : 구현이 안된 메소드 1개가 필요
 *         => 코드가 간결하다 / 가독성(여러개 사용)이 떨어진다
 *         => 기존의 라이브러리 함수 구현
 */
public class 람다_1 {
    public static void main(String[] args) {
    	계산기 add=(a,b)->System.out.println(a+b);
    	계산기 minus=(a,b)->System.out.println(a-b);
    	계산기 gop=(a,b)->System.out.println(a*b);
    	계산기 div=(a,b)->System.out.println(a/b);
    	
    	add.calc(10, 5);
    	minus.calc(10, 5);
    	gop.calc(10, 5);
    	div.calc(10, 5);
    	
    	MyFunction mf=()->{
    		System.out.println("람다식 실행!!");
    	};
    	mf.display();
    }
    
}
