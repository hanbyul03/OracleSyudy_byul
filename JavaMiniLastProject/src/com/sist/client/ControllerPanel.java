package com.sist.client;
import javax.swing.*;
import java.awt.*;
public class ControllerPanel extends JPanel{
    HomeForm hf;
    ChatForm cf=new ChatForm();
    BoardList bf=new BoardList();
    FoodFind ff;
    FoodDetail fd;
    GenieMusic gm;
    CardLayout card=new CardLayout();
    public ControllerPanel()
    {
    	hf=new HomeForm(this);
    	ff=new FoodFind(this);
    	fd=new FoodDetail(this);
    	gm=new GenieMusic(this);
    	setLayout(card);
    	
    	add("HF",hf);
    	add("CF",cf);
    	add("BF",bf);
    	add("FF",ff);
    	add("FD",fd);
    	add("GM",gm);
    }
}