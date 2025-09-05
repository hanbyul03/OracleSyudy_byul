package com.sist.main;
import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import com.sist.dao.EmpDAO;
import com.sist.vo.EmpVO;
public class EmpMainFrame extends JFrame implements MouseListener{
    JTable table;
    DefaultTableModel model;
    EmpDAO dao=EmpDAO.newInstance();
    public EmpMainFrame()
    {
    	String s="empno,ename,job,sal,hiredate,dname,loc,grade";
    	String[] col=s.split(",");
    	String[][] row=new String[0][col.length];
    	model=new DefaultTableModel(row,col){

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);
    	add("Center",js);
    	// 데이터 추가 
    	List<EmpVO> list=dao.empListData();
    	list.forEach(vo->{
    		String[] data={
    			String.valueOf(vo.getEmpno()),
    			vo.getEname(),
    			vo.getJob(),
    			String.valueOf(vo.getSal()),
    			vo.getHiredate().toString(),
    			vo.getDvo().getDname(),
    			vo.getDvo().getLoc(),
    			String.valueOf(vo.getSvo().getGrade())
    		};
    		model.addRow(data);
    	});
    	setSize(640, 480);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	table.addMouseListener(this);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new EmpMainFrame();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				int row=table.getSelectedRow();
				String empno=model.getValueAt(row, 0).toString();
				EmpVO vo=dao.empDetailData(Integer.parseInt(empno));
				String msg="사번:"+vo.getEmpno()+"\n"
						  +"이름:"+vo.getEname()+"\n"
						  +"직위:"+vo.getJob()+"\n"
						  +"입사일:"+vo.getHiredate().toString()+"\n"
						  +"급여:"+vo.getSal()+"\n"
						  +"부서명:"+vo.getDvo().getDname()+"\n"
						  +"근무지:"+vo.getDvo().getLoc()+"\n"
						  +"급여등급:"+vo.getSvo().getGrade();
               JOptionPane.showMessageDialog(this, msg);						  
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