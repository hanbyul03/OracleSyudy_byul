package com.sist.lamda;
/*
 *   1. 매개변수 1개, 리턴형이 없는 람다식
 *      Consumer
 *   2. 매개변수 2개, 리턴이 있는 람다식
 *      Function
 *   3. 조건 검색 람다식
 *      Predicate
 *   4. 리스트 처리(Stream+Lambda)
 */
import java.util.*;
public class 람다_3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        List<String> names=Arrays.asList("박문수","홍길동","이순신","강감찬","을지문덕");
        /*names.stream().filter(name->name.startsWith("홍"))
        .forEach(System.out::println);*/
        names.forEach(name->System.out.println(name));
	}

}

