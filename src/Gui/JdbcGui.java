package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JdbcGui implements ActionListener {

	private JFrame frame;// frame
	JButton[] button;// ��ť
	JTable table;// ���
	DefaultTableModel model;
	JScrollPane jspane;// ����������pane�����JTable
	JPanel pane;// ��Ű�ť��pane
	Vector attribute;// �����������������Ǳ��ĵ�һ��

	int line;// ����
	int rows;// �������


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

	JdbcGui() {// ������
		frame = new JFrame();

		button = new JButton[6];
		button[0] = new JButton("���в���");// ���ܰ�ť
		button[1] = new JButton("���в���");
		button[2] = new JButton("�Ӳ�ѯ����");
		button[3] = new JButton("��ʵ");
		button[4] = new JButton("ɾ������");

		for (int i = 0; i < 5; i++) {// ��ÿ����ť����¼�
			button[i].addActionListener(this);
		}

		model = new DefaultTableModel();
		table = new JTable(model);// �������
		attribute = new Vector();

		line = 0;// ��¼�ñ������
		connectDatabase();// �������ݿ�
		try {
			String sql = "select column_name from user_tab_cols where table_name='EMPLOYEE'";// ��ѯ�������
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				attribute.add(result.getString(1));
				line++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ����ʧ��");
		}

		Vector data = new Vector();

		rows = 0;// ����
		try {
			String sql = "select * from EMPLOYEE";// ��ѯ����
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				Vector row = new Vector();// һ������
				for (int i = 1; i <= line; i++) {
					row.add(result.getString(i));
				}
				data.add(row);
				rows++;// ������1
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ����ʧ��");
		}

		model.setDataVector(data, attribute);// ����
		jspane = new JScrollPane(table);// �½�jspane

		table.setPreferredScrollableViewportSize(new Dimension(200, 200));// ���ȴ�С

		pane = new JPanel();
		for (int i = 0; i < 5; i++) {
			pane.add(button[i]);
		}

		// System.out.println("hang:"+rows+"lie:"+line);

		frame.add(jspane, BorderLayout.NORTH);
		frame.add(pane, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(frame.getOwner());// ���ô��ڵ�����λ��
	}

	public void DisConnect() {
		try {
			// ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
			// ע��رյ�˳�����ʹ�õ����ȹر�
			if (result != null)
				result.close();
			if (pre != null)
				pre.close();
			if (con != null)
				con.close();
			System.out.println("���ݿ������ѹرգ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcGui jg = new JdbcGui();
		// jg.DisConnect();
	}

	int newrows = 0;// Ҫ��ӵ����ݿ��������

	JTextArea textarea;
	JFrame frame1;
	JPanel panel1;
	JScrollPane jspanel1;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button[0]) {
			Vector newRow = new Vector();
			model.addRow(newRow);
			newrows++;
		}
		if (e.getSource() == button[1]) {// ���в���
			frame1 = new JFrame();
			textarea = new JTextArea();

			jspanel1 = new JScrollPane(textarea);
			panel1 = new JPanel();

			jspanel1.setPreferredSize(new Dimension(0, 210));// ���ó�ʼ��С

			button[5] = new JButton("ȷ������");
			button[5].addActionListener(this);

			panel1.add(button[5]);

			frame1.add(jspanel1, BorderLayout.NORTH);
			frame1.add(panel1, BorderLayout.SOUTH);

			frame1.setVisible(true);
			frame1.setSize(500, 300);
			frame1.setLocationRelativeTo(frame.getOwner());// ���ô��ڵ�����λ��

		}
		
		if (e.getSource() == button[2]) {// �Ӳ�ѯ����
			
		}
		
		
		if (e.getSource() == button[3]) {
			int i = (int) model.getRowCount();// ���ڵ�����

			int temp = newrows - 1;
			for (int m = temp; m >= 0; m--) {
				String[] newline = new String[line];// �洢ÿ��Ҫ���������

				for (int j = 0; j < line; j++) {// ����������ݴ��͵�newline������
					newline[j] = (String) model.getValueAt((i - 1) - m, j);
					System.out.print(newline[j] + " ");
				}
				System.out.println();

				// ���������ݵ������ݿ���
				try {

					String sql = "insert into EMPLOYEE values(?,?,?,?,?,?,?)";

					pre = con.prepareStatement(sql);

					for (int n = 0; n < line; n++) {// ����ѭ������
						if (n == 3) {
							if (newline[n] == null) {
								pre.setNull(4, java.sql.Types.FLOAT);
							} else {
								float temp2 = Float.valueOf(newline[3]);
								pre.setFloat(4, temp2);
							}
						} else {
							pre.setString(n + 1, newline[n]);
						}
					}
					pre.executeUpdate();
				} catch (SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
				newrows--;// Ҫ��ӵ�������һ
			}
			System.out.println("��Ҫ�������ݿ��������" + newrows);// ���ɹ����룬���ʱnewrowsӦ��Ϊ0
		}

		if (e.getSource() == button[4]) {// ɾ��Ϊ��ʵ���У������Ѿ��е��в���ɾ��
			if (newrows > 0) {
				int i = (int) model.getRowCount();// ���ڵ�����
				model.removeRow(i - 1);
				newrows--;
			}
		}
		if (e.getSource() == button[5]) {// ����ȷ�ϲ���,�����ڵ�����в��밴ť�󵯳��Ľ�����
			System.out.println(textarea.getLineCount());// ���textareaһ��������
			System.out.println(textarea.getText());// �ı����

			String[] RowArray = textarea.getText().split("\n");// �Ի��з���String�ָ�м��оͷָ�ɼ���String

			String[][] AttributeArray = new String[textarea.getLineCount()][];// �ö�ά����洢���е�����

			for (int li = 0; li < textarea.getLineCount(); li++) {// ���н����Դ洢��AttributeArray������
				AttributeArray[li] = RowArray[li].split("\t");// ��ÿһ�зָ���뵽��ά������ȥ
			}

			for (int j = 0; j < textarea.getLineCount(); j++) {
				for (int i = 0; i < AttributeArray[0].length; i++) {// ����ѭ������ά����Ԫ���������
					if (AttributeArray[j].equals(""))
						AttributeArray[j][i] = null;
					System.out.print(AttributeArray[j][i] + " ");
				}
				System.out.println("/n��" + j + "�д������ݸ���" + AttributeArray[0].length);
			}

			int tag = 0;// ��¼��sourceStrArray�����λ��
			for (int j = 0; j < textarea.getLineCount(); j++) {// �ı����м���ѭ������
				try {

					String sql = "insert into EMPLOYEE values(?,?,?,?,?,?,?)";

					pre = con.prepareStatement(sql);

					for (int n = 0; n < line; n++) {// ����ѭ������
						if (n == 3) {
							if (AttributeArray[j][n] == null) {// �˱��е��ĸ�Ԫ����float�ͣ���������
								pre.setNull(4, java.sql.Types.FLOAT);// �����
							} else {
								float temp = Float.valueOf(AttributeArray[j][n]);// ������ת��Ϊfloat��
								pre.setFloat(4, temp);
							}
						} else {
							pre.setString(n + 1, AttributeArray[j][n]);
						}
						tag++;// ָ��������һ������
					}
					pre.executeUpdate();
				} catch (SQLException exception) {
					// TODO Auto-generated catch block
					System.out.println(tag - 1);
					System.out.println(AttributeArray[j] + "�г���");
					exception.printStackTrace();
				}
			}
		}
	}
}
