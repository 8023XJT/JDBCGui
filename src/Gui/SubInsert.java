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
	
	
	//���ݿ��йصĳ�Ա����
	Connection con;// ���ݿ������
	PreparedStatement pre;//
	ResultSet result;
	
	public void connectDatabase() {// �������ݿ�
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("��ʼ�����������ݿ⣡");
			String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:SuperMarketO";
			String user = "SYSTEM";// �û���,ϵͳĬ�ϵ��˻���
			String password = "150103";// ����
			con = DriverManager.getConnection(url, user, password);// ��ȡ����
			System.out.println("���ӳɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String[] table = {"EMPLOYEE", "MEMBER", "INVENTORY", "COMMODITY", "U_USERS","SALES","WAREHOUSE" };

//	private Object[] a() {//��һ��������
//		Object[] os = new Object[xxx.length];
//		for (int i = 0; i < xxx.length; i++) {
//			os[i] = xxx[i][0];
//		}
//		return os;
//	}
	
	SubInsert(){
		
		
		frame=new JFrame("�Ӳ�ѯ����");
		

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
