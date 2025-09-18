package com.sist.main;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try
        {
        	for(int i=1;i<=10;i++)
        	{
        	  try
        	  {
        		int r=(int)(Math.random()*3);
        		System.out.println(i/r);
        	  }catch(Exception ex) {}
        	}
        }catch(Exception ex) {}
	}

}