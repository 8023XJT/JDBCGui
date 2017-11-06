package Gui;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class SubInsert {

	
	JFrame frame;
	
	
	//数据库有关的成员变量
	Connection con;// 数据库的链接
	PreparedStatement pre;//
	ResultSet result;
	
	public void connectDatabase() {// 连接数据库
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:SuperMarketO";
			String user = "SYSTEM";// 用户名,系统默认的账户名
			String password = "150103";// 密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
			System.out.println("连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String[] table = {"EMPLOYEE", "MEMBER", "INVENTORY", "COMMODITY", "U_USERS","SALES","WAREHOUSE" };

//	private Object[] a() {//第一级的属性
//		Object[] os = new Object[xxx.length];
//		for (int i = 0; i < xxx.length; i++) {
//			os[i] = xxx[i][0];
//		}
//		return os;
//	}
	
	SubInsert(){
		
		
		frame=new JFrame("子查询插入");
		

		JComboBox tableName = new JComboBox(table);
		
		frame.add(tableName);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.setVisible(true);
		frame.setSize(200, 100);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SubInsert si=new SubInsert();
	}

}
