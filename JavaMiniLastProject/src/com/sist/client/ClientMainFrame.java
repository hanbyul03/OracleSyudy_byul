package com.sist.client;
import javax.swing.*;
import java.awt.*; // 배치 => 레이아웃 
import java.awt.event.*; // 이벤트 처리 
public class ClientMainFrame extends JFrame
implements ActionListener
{
	MenuForm menu=new MenuForm();
	ControllerPanel cp=new ControllerPanel();
	Login login=new Login();
	Join join=new Join();
	JMenuItem genie;
	JMenuItem melon;
	JMenuItem user;
	// has-a => 포함 클래스 
    public ClientMainFrame()
    {
    	JMenuBar bar=new JMenuBar();
    	JMenu menu1=new JMenu("기타");
    	genie=new JMenuItem("지니뮤직");
    	melon=new JMenuItem("멜론");
    	user=new JMenuItem("개인");
    	menu1.add(genie);
    	menu1.add(melon);
    	menu1.add(user);
    	bar.add(menu1);
    	setJMenuBar(bar);
    	setLayout(null);
    	menu.setBounds(200, 15,750 , 50);
    	cp.setBounds(10, 75,990, 580);
    	add(menu);
    	add(cp);
    	setSize(1024, 700);
    	//setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	//MemberListener m=new MemberListener(this);
    	menu.b1.addActionListener(this);
    	menu.b5.addActionListener(this);
    	menu.b6.addActionListener(this);
    	menu.b3.addActionListener(this);
    	
    	login.b1.addActionListener(this); // 로그인 
    	login.b2.addActionListener(this); // 회원가입 
    	login.b3.addActionListener(this); // 취소
    	
    	join.b1.addActionListener(this); // 회원가입 
    	join.b2.addActionListener(this); // 취소 
    	
    	genie.addActionListener(this);
    	
    	// textfield / button / menuitem => ActionListener
    	// table / label / image / panel => MouseListener
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			//                                         hifi  HiFiLook~
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception e) {}
        new ClientMainFrame();// 생성자 호출 
        // ClientMainFrame c=new ClientMainFrame();
        
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==menu.b1)
		{
			cp.card.show(cp, "HF");
		}
		else if(e.getSource()==menu.b3)
		{
			cp.card.show(cp, "FF");
		}
		else if(e.getSource()==menu.b5)
		{
			cp.card.show(cp, "CF");
		}
		else if(e.getSource()==menu.b6)
		{
			cp.card.show(cp, "BF");
		}
		else if(e.getSource()==login.b2)
		{
			login.setVisible(false);
			join.setVisible(true);
		}
		else if(e.getSource()==login.b3)
		{
			//dispose();
			//System.exit(0);
			setVisible(true);
			login.setVisible(false);
		}
		else if(e.getSource()==join.b1)
		{
			
		}
		else if(e.getSource()==join.b2)
		{
			login.setVisible(true);
			join.setVisible(false);
		}
		else if(e.getSource()==genie)
		{
			cp.card.show(cp, "GM");
		}
	}
	

}