package com.sist.main;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// ControllerPanel
public class BoardMainFrame extends JFrame{
    CardLayout card=new CardLayout();
    BoardList bList;
    BoardInsert bInsert;
    BoardDetail bDetail;
    BoardUpdate bUpdate;
    public BoardMainFrame()
    {
    	bList=new BoardList(this);
    	bInsert=new BoardInsert(this);
    	bDetail=new BoardDetail(this);
    	bUpdate=new BoardUpdate(this);
    	setLayout(card);
    	add("list",bList);
    	add("detail",bDetail);
    	add("insert",bInsert);
    	add("update",bUpdate);
    	setSize(640, 570);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new BoardMainFrame();
	}

}