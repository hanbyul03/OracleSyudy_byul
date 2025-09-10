package com.sist.main;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.*;

import java.util.Date;
import java.util.List;
import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;
public class BoardList extends JPanel implements ActionListener,
MouseListener
{
    JLabel la1,la2;
    JTable table;
    DefaultTableModel model;
    JButton b1,b2,b3;
    private BoardMainFrame bm;
    TableColumn column;
    // 현재페이지 / 총페이지 
    int curpage=1;
    int totalpage=0;
    public BoardList(BoardMainFrame bm)
    {
    	this.bm=bm;
    	la1=new JLabel("게시판",JLabel.CENTER);
    	la1.setFont(new Font("맑은 고딕",Font.BOLD,40));
    	la2=new JLabel("0 page / 0 pages");
    	b1=new JButton("새글");
    	b2=new JButton("이전");
    	b3=new JButton("다음");
    	
    	String[] col={"번호","","제목","이름","작성일","조회수"};
    	String[][] row=new String[0][6];
    	model=new DefaultTableModel(row,col)
    	{

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		DefaultTableCellRenderer rend=
    				new DefaultTableCellRenderer();
    		column=table.getColumnModel().getColumn(i);
    		if(i==0)
    		{
    			column.setPreferredWidth(35);
    			rend.setHorizontalAlignment(JLabel.CENTER);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(35);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(350);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(100);
    			rend.setHorizontalAlignment(JLabel.CENTER);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(100);
    			rend.setHorizontalAlignment(JLabel.CENTER);
    		}
    		else if(i==5)
    		{
    			column.setPreferredWidth(50);
    			rend.setHorizontalAlignment(JLabel.CENTER);
    		}
    		column.setCellRenderer(rend);
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.getTableHeader().setResizingAllowed(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.pink);
    	table.getColumnModel().removeColumn(
    	    table.getColumnModel().getColumn(1)
    	);
    	
    	setLayout(null);
    	la1.setBounds(10, 15, 610, 50);
    	add(la1);
    	b1.setBounds(30, 75, 80, 30);
    	add(b1);
    	js.setBounds(30, 110, 580, 350);
    	add(js);
    	JPanel p=new JPanel();
    	p.add(b2);p.add(la2);p.add(b3);
    	p.setBounds(30, 470, 580, 35);
    	add(p);
    	print();
    	
    	b1.addActionListener(this);// Menu / Button / TextField
    	b2.addActionListener(this);
    	b3.addActionListener(this);
    	table.addMouseListener(this);// table / tree / label  
    }
    // 데이터 출력 
    public void print()
    {
    	//1. 테이블을 초기화 
    	for(int i=model.getRowCount()-1;i>=0;i--)
    	{
    		model.removeRow(i);
    	}
    	//2. 데이터베이스값 
    	BoardDAO dao=BoardDAO.newInstance();
    	List<BoardVO> list=dao.boardListData(curpage);
    	int count=dao.boardRowCount();
    	totalpage=(int)(Math.ceil(count/10.0));
    	count=count-((curpage*10)-10); 
        String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	for(BoardVO vo:list)
    	{
    		String ss="<html><body>"+vo.getSubject()+"</body></html>";
    		if(vo.getDbday().equals(today))
    		{
    			ss="<html><body>"+vo.getSubject()+"&nbsp;<sup><font color=red>new</font></sub></body></html>";
    		}
    		String[] data= {
    			String.valueOf(count),
    			String.valueOf(vo.getNo()),
    			ss,
    			vo.getName(),
    			vo.getDbday(),
    			String.valueOf(vo.getHit())
    		};
    		model.addRow(data);
    		count--;
    	}
    	la2.setText(curpage +" page / "+totalpage+" pages");
    	
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)// 이전
		{
			if(curpage>1)
			{
				curpage--;
				print();
			}
		}
		else if(e.getSource()==b3) //다음
		{
			if(curpage<totalpage)
			{
				curpage++;
				print();
			}
		}
		else if(e.getSource()==b1)
		{
			bm.card.show(bm.getContentPane(), "insert");
			bm.bInsert.tf1.setText("");
			bm.bInsert.tf2.setText("");
			bm.bInsert.ta.setText("");
			bm.bInsert.pf.setText("");
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				int row=table.getSelectedRow();
				String no=model.getValueAt(row, 1).toString();
				bm.card.show(bm.getContentPane(), "detail");
				bm.bDetail.print(Integer.parseInt(no));
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}