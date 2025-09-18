package com.sist.client;
import javax.swing.*;

import com.sist.commons.ImageChange;
import com.sist.dao.FoodDAO;
import com.sist.vo.FoodVO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.List;
import javax.swing.table.*;
public class HomeForm extends JPanel implements ActionListener,MouseListener{
   ControllerPanel cp;
   JPanel pan=new JPanel();
   JLabel[] imgs=new JLabel[12];
   JLabel pageLa=new JLabel("0 page / 0 pages",JLabel.CENTER);
   JButton b1,b2;
   FoodDAO dao=FoodDAO.newInstance();
   int curpage=1;
   int totalpage=0;
   JTable table;
   DefaultTableModel model;
   public HomeForm(ControllerPanel cp)
   {
	   this.cp=cp;
	   setLayout(null);
	   //pan.setBackground(Color.cyan);
	   pan.setLayout(new GridLayout(3,4,5,5));
	   pan.setBounds(30, 15, 700, 500);
	   add(pan);
	   
	   
	   b1=new JButton("이전");
	   b2=new JButton("다음");
	   JPanel p=new JPanel();
	   p.add(b1);p.add(pageLa);p.add(b2);
	   p.setBounds(30, 525, 700, 35);
	   add(p);
	   
	   b1.addActionListener(this);
	   b2.addActionListener(this);
	   
	   String[] col={"","업체명","지역"};
	   Object[][] row=new Object[0][3];
	   model=new DefaultTableModel(row,col)
	   {
            // 편집 방지 
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
            // 이미지 출력 
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return getValueAt(0, columnIndex).getClass();
			}
			
		   
	   };
	   table=new JTable(model);
	   table.getTableHeader().setReorderingAllowed(false);
	   table.setRowHeight(35);
	   JScrollPane js=new JScrollPane(table);
	   for(int i=0;i<col.length;i++)
	   {
		   TableColumn column=table.getColumnModel().getColumn(i);
		   if(i==0)
			   column.setPreferredWidth(100);
		   else if(i==1)
			   column.setPreferredWidth(150);
		   else if(i==2)
			   column.setPreferredWidth(100);
	   }
	   js.setBounds(740, 15, 230, 500);
	   add(js);
	   print();
   }
   public void init()
   {
	   for(int i=0;i<imgs.length;i++)
	   {
		   imgs[i]=new JLabel("");
	   }
	   pan.removeAll();
	   pan.validate();
   }
   public void print()
   {
	   totalpage=dao.foodTotalPage();
	   // 데이터 읽기 
	   List<FoodVO> list=dao.foodListData(curpage);
	   for(int i=0;i<list.size();i++)
	   {
		   FoodVO vo=list.get(i);
		   try
		   {
			   URL url=new URL(vo.getPoster());
			   Image image=ImageChange.getImage(new ImageIcon(url), 170, 180);
			   imgs[i]=new JLabel(new ImageIcon(image));
			   imgs[i].setToolTipText(vo.getFno()+"."+vo.getName());
			   imgs[i].addMouseListener(this);
			   pan.add(imgs[i]);
		   }catch(Exception ex) {}
		   pageLa.setText(curpage+" page / "+totalpage+" pages");
	   }
	   
	   for(int i=model.getRowCount()-1;i>=0;i--)
	   {
		   model.removeRow(i);
	   }
	   
	   List<FoodVO> tList=dao.foodTop10();
	   System.out.println(tList.size());
	   for(FoodVO vo:tList)
	   {
		   try
		   {
			   URL url=new URL(vo.getPoster());
			   Image image=ImageChange.getImage(new ImageIcon(url), 35, 35);
			   
			   Object[] data= {
				  new ImageIcon(image),
				  vo.getName(),
				  vo.getAddress()
			   };
			   model.addRow(data);
		   }catch(Exception ex) {ex.printStackTrace();}
	   }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	  if(e.getSource()==b1)
	  {
		  if(curpage>1)
		  {
			  curpage--;
			  init();
			  print();
		  }
	  }
	  else if(e.getSource()==b2)
	  {
		  if(curpage<totalpage)
		  {
			  curpage++;
			  init();
			  print();
		  }
	  }
   }
   @Override
   public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	   for(int i=0;i<imgs.length;i++)
	   {
		   if(e.getSource()==imgs[i])
		   {
			   String s=imgs[i].getToolTipText();
			   s=s.substring(0,s.indexOf("."));
			   FoodDetail.type=0;
			   //JOptionPane.showMessageDialog(this, "선택된 번호:"+s);
			   cp.card.show(cp, "FD");
			   cp.fd.print(Integer.parseInt(s));
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






