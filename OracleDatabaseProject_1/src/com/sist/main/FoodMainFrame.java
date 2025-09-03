package com.sist.main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class FoodMainFrame extends JFrame implements
ActionListener
{
    CardLayout card=new CardLayout();
    JMenuItem homeItem=new JMenuItem("홈");
    JMenuItem finditem=new JMenuItem("검색");
    JMenuItem exititem=new JMenuItem("종료");
    FoodListPanel flp=new FoodListPanel();
    public FoodMainFrame()
    {
    	JMenuBar bar=new JMenuBar();
    	JMenu menu=new JMenu("File");
    	menu.add(homeItem);
    	menu.addSeparator();
    	menu.add(finditem);
    	menu.addSeparator();
    	menu.add(exititem);
    	bar.add(menu);
    	
    	setJMenuBar(bar);
    	
    	setLayout(card);
    	add("FLP",flp);
    	
    	setSize(840,740);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	flp.b1.addActionListener(this);
    	flp.b2.addActionListener(this);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         new FoodMainFrame();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==flp.b1)
		{
			if(flp.curpage>1)
			{
				flp.curpage--;
				flp.init();
				flp.print();
			}
		}
		else if(e.getSource()==flp.b2)
		{
			if(flp.curpage<flp.totalpage)
			{
				flp.curpage++;
				flp.init();
				flp.print();
			}
		}
	}

}