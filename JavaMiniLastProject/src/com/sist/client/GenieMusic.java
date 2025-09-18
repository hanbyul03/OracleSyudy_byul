package com.sist.client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.*;
import javax.swing.table.*;

import com.sist.commons.ImageChange;
import com.sist.dao.GenieMusicDAO;
import com.sist.vo.GenieMusicVO;

import java.awt.event.*;
import java.net.URL;
import java.util.List;
public class GenieMusic extends JPanel implements ActionListener{
   ControllerPanel cp;
   JButton[] btns=new JButton[7];
   String[] titles={"Top100",
		   "가요",
		   "POP",
		   "OST",
		   "트롯",
		   "JAZZ",
		   "CLASSIC"};
   JTable table;
   DefaultTableModel model;
   TableColumn column;
   public GenieMusic(ControllerPanel cp)
   {
	   this.cp=cp;
	   //setBackground(Color.gray);
	   setLayout(null);
	   JPanel p=new JPanel();
	   p.setLayout(new GridLayout(1,7,5,5));
	   for(int i=0;i<btns.length;i++)
	   {
		   btns[i]=new JButton(titles[i]);
		   p.add(btns[i]);
		   btns[i].addActionListener(this);
	   }
	   p.setBounds(200, 15, 600, 35);
	   add(p);
	   
	   String[] col={"순위","","","곡명","가수명","앨범"};
	   Object[][] row=new Object[0][6];
	   // 익명의 클래스 => 상속없이 오버라이딩이 가능 
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
	   
	   js.setBounds(100, 60, 800, 500);
	   
	   add(js);
	   
	   for(int i=0;i<col.length;i++)
	   {
		   column=table.getColumnModel().getColumn(i);
		   if(i==0)
		   {
			   column.setPreferredWidth(30);
		   }
		   else if(i==1)
		   {
			   column.setPreferredWidth(45);
		   }
		   else if(i==2)
		   {
			   column.setPreferredWidth(60);
		   }
		   else
		   {
			   column.setPreferredWidth(280);
		   }
	   }
	   table.getTableHeader().setReorderingAllowed(false);
	   table.getTableHeader().setResizingAllowed(false);
	   table.setRowHeight(35);
	   table.setShowVerticalLines(false);
	   print(1);
   }
   public void print(int cno)
   {
	   for(int i=model.getRowCount()-1;i>=0;i--)
	   {
		   model.removeRow(i);
	   }
	   
	   GenieMusicDAO dao=GenieMusicDAO.newInstance();
	   List<GenieMusicVO> list=dao.genieListData(cno);
	   for(GenieMusicVO vo:list)
	   {
		   try
		   {
			    URL url=new URL(vo.getPoster());
				Image img=ImageChange.getImage(new ImageIcon(url), 
						  35, 35);
				String s="";
				if(vo.getState().equals("유지"))
					s="-";
				else if(vo.getState().equals("상승"))
				{
					s="<html><body>"
					 +"<b><font color=red>▲"+vo.getIdcrement()
					 +"</font></b></body></html>";
					 
				}
				else if(vo.getState().equals("하강"))
				{
					s="<html><body>"
					+"<b><font color=blue>▼"+vo.getIdcrement()
					+"</font></b></body></html>";
					
				}
				Object[] data= {
					vo.getRank(),
					s,
					new ImageIcon(img),
					vo.getTitle(),
					vo.getSinger(),
					vo.getAlbum()
				};
				model.addRow(data);
		   }catch(Exception ex) {}
	   }
	   
   }
   @Override
   public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	  for(int i=0;i<btns.length;i++)
	  {
		  if(e.getSource()==btns[i])
		  {
			  print(i+1);
		  }
	  }
   }
}




