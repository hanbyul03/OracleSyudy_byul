package com.sist.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberListener implements ActionListener{
    ClientMainFrame cmf;
    public MemberListener(ClientMainFrame cmf)
    {
    	this.cmf=cmf;
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cmf.menu.b1)
		{
			cmf.cp.card.show(cmf.cp, "HF");
		}
		else if(e.getSource()==cmf.menu.b5)
		{
			cmf.cp.card.show(cmf.cp, "CF");
		}
		else if(e.getSource()==cmf.menu.b6)
		{
			cmf.cp.card.show(cmf.cp, "BF");
		}
		else if(e.getSource()==cmf.login.b2)
		{
			cmf.login.setVisible(false);
			cmf.join.setVisible(true);
		}
		else if(e.getSource()==cmf.login.b3)
		{
			//dispose();
			//System.exit(0);
			cmf.setVisible(true);
			cmf.login.setVisible(false);
		}
		else if(e.getSource()==cmf.join.b1)
		{
			
		}
		else if(e.getSource()==cmf.join.b2)
		{
			cmf.login.setVisible(true);
			cmf.join.setVisible(false);
		}
	}

}