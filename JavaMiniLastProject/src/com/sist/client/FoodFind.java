package com.sist.client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;
import java.util.List;
import java.net.*;
public class FoodFind extends JPanel implements ActionListener,MouseListener{
     JComboBox<String> box;
     JTextField tf;
     JButton btn;
     DefaultTableModel model;
     JTable table;
     TableColumn column;
     ControllerPanel cp;
     JButton b1,b2;
     JLabel pageLa=new JLabel("0 page / 0 pages");
     int curpage=1;
     int totalpage=0;
     public FoodFind(ControllerPanel cp)
     {
    	 this.cp=cp;
    	 box=new JComboBox<String>();
    	 box.addItem("업체명");
    	 box.addItem("음식종류");
    	 box.addItem("주소");
    	 String[] col={"번호","","맛집명"};
    	 Object[][] row=new Object[0][3];
    	 model=new DefaultTableModel(row,col)
    	 {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return getValueAt(0,columnIndex).getClass();
			}
    		  
    	 };
    	 
    	 table=new JTable(model);
    	 JScrollPane js=new JScrollPane(table);
    	 for(int i=0;i<col.length;i++)
     	{
     		//DefaultTableCellRenderer rend=
     				//new DefaultTableCellRenderer();
     		column=table.getColumnModel().getColumn(i);
     		if(i==0)
     		{
     			column.setPreferredWidth(35);
     			//rend.setHorizontalAlignment(JLabel.CENTER);
     		}
     		else if(i==1)
     		{
     			column.setPreferredWidth(80);
     		}
     		else if(i==2)
     		{
     			column.setPreferredWidth(350);
     		}
     		
     		//column.setCellRenderer(rend);
     	}
     	table.getTableHeader().setReorderingAllowed(false);
     	table.getTableHeader().setResizingAllowed(false);
     	table.setRowHeight(30);
     	//////////////
     	DefaultTableCellRenderer centerRenderer = 
     			new DefaultTableCellRenderer();
     	centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
     	table.getColumnModel().getColumn(2).
     	                 setCellRenderer(centerRenderer);
     	/////////////////////
     	tf=new JTextField(10);
     	btn=new JButton("검색");
     	JPanel p=new JPanel();
     	p.add(box);p.add(tf);p.add(btn);
     	setLayout(null);
     	p.setBounds(200, 15, 350, 35);
     	add(p);
     	js.setBounds(200, 60, 650, 420);
     	add(js);
     	
     	b1=new JButton("이전");
     	b2=new JButton("다음");
     	
     	JPanel p2=new JPanel();
     	p2.add(b1);p2.add(pageLa);p2.add(b2);
     	p2.setBounds(200, 490, 650, 35);
    	add(p2);
    	btn.addActionListener(this);
    	tf.addActionListener(this);
    	
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	// 웹 => 자바스크립트 <a> 
    	table.addMouseListener(this);
     }
	 @Override
	 public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn || e.getSource()==tf)
		{
			print();
		}
		else if(e.getSource()==b1)
		{
			if(curpage>1)
			{
				curpage--;
				print();
			}
		}
		else if(e.getSource()==b2)
		{
			if(curpage<totalpage)
			{
				curpage++;
				print();
			}
		}
	 }
	 public void print()
	 {
		 for(int i=model.getRowCount()-1;i>=0;i--)
		 {
			 model.removeRow(i);
		 }
		 
		 String fd=tf.getText();
		 if(fd.trim().length()<1)
		 {
			 tf.requestFocus();
			 return;
		 }
		 int index=box.getSelectedIndex();
		 String[] data={"name","type","address"};
		 FoodDAO dao=FoodDAO.newInstance();
		 List<FoodVO> list=dao.foodFindData(data[index],fd,curpage);
		 int count=dao.findCount(data[index], fd);
		 if(count==0)
		 {
			 JOptionPane.showMessageDialog(this,"검색 결과가 없습니다");
		 }
		 else
		 {
			try
			{
				for(FoodVO vo:list)
				{
					URL url=new URL(vo.getPoster());
					Image img=ImageChange.getImage(new ImageIcon(url), 35, 35);
					Object[] d= {
						String.valueOf(vo.getFno()),
						new ImageIcon(img),
						vo.getName()
					};
					model.addRow(d);
				}
			}catch(Exception ex) {}
		 }
         totalpage=(int)(Math.ceil(count/20.0));
         pageLa.setText(curpage+" page / "+totalpage+" pages");
	 }
	 @Override
	 public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				int row=table.getSelectedRow();
				String fno=model.getValueAt(row, 0).toString();
				cp.card.show(cp, "FD");
				cp.fd.print(Integer.parseInt(fno));
				FoodDetail.type=1;
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




