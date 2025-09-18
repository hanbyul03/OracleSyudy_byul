package com.sist.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Login extends JFrame{
	JLabel la1,la2;
	JTextField tf;
	JPasswordField pf;
	JButton b1,b2,b3;
	public Login()
	{
		la1=new JLabel("ID",JLabel.CENTER);
		la2=new JLabel("PW",JLabel.CENTER);
		tf=new JTextField();
		pf=new JPasswordField();
		b1=new JButton("로그인");
		b2=new JButton("회원가입");
		b3=new JButton("취소");
		
		//배치
		setLayout(null);
		la1.setBounds(10, 15, 80, 30);
		tf.setBounds(95, 15, 200, 30);
		
		la2.setBounds(10, 50, 80, 30);
		pf.setBounds(95, 50, 200, 30);
		
		JPanel p=new JPanel();
		p.add(b1);p.add(b2);p.add(b3);
		p.setBounds(10, 90, 285, 35);
		
		add(la1);add(tf);
		add(la2);add(pf);		
		add(p);
		
		setBounds(370, 250, 320, 155);
		setVisible(true);
	}
}
