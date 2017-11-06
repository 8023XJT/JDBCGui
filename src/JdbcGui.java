import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JdbcGui implements ActionListener {

//	static { 
//		try { 
//			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver"); 
//		} catch (Exception e) { 
//			e.printStackTrace(); 
//		} 
//	}
	JFrame jf;
	JButton jb1;
	JButton jb2;
	JButton jb3;
	JButton jb4;
	
	JTable jt1;
	JScrollPane jp1;
	JPanel jp2;

	DefaultTableModel model;
	
	JdbcGui(){
		JFrame jf=new JFrame();
		
		jb1=new JButton("插入");//功能按钮
		jb2=new JButton("落实");
		jb3=new JButton("SQL语句插入");
		jb4=new JButton("选择表");
		
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);


		model=new DefaultTableModel();
		jt1=new JTable(model);
		Vector data=new Vector();
		Vector row1=new Vector();//第一行数据
		
		row1.add("1");//第一行数据
		data.add(row1);//将第一行加入到向量中
		
		Vector name=new Vector();//行名称
		name.add("第一列");//内容
		name.add("第二列");
		
		model.setDataVector(data,name );//创建
		
		
		jt1.setPreferredScrollableViewportSize(new Dimension(200, 200));//首先大小
//        jt1.setFillsViewportHeight(true);//占据整个panel
	
		
		
        JScrollPane jp1=new JScrollPane(jt1);
		JPanel jp2=new JPanel();
		
		jp2.add(jb1);
		jp2.add(jb2);
		jp2.add(jb3);
		
		
		jf.add(jp1,BorderLayout.NORTH);
		jf.add(jp2,BorderLayout.SOUTH);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setSize(400, 300);
		
//		DefaultTableModel model1=new DefaultTableModel();
//		jt1.setModel(model1);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcGui jg=new JdbcGui();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1) {
			System.out.println(1);
			Vector newRow=new Vector();
			model.addRow(newRow);
		}if(e.getSource()==jb2) {
			int i=(int)model.getRowCount();
			System.out.println(i);
		}
	}

}
