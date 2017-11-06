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
	JButton[] button;// 按钮
	JTable table;// 表格
	DefaultTableModel model;
	JScrollPane jspane;// 带滚动条的pane，存放JTable
	JPanel pane;// 存放按钮的pane
	Vector attribute;// 列名的列向量，就是表格的第一行

	int line;// 列数
	int rows;// 表的行数


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

	JdbcGui() {// 创建类
		frame = new JFrame();

		button = new JButton[6];
		button[0] = new JButton("单行插入");// 功能按钮
		button[1] = new JButton("多行插入");
		button[2] = new JButton("子查询插入");
		button[3] = new JButton("落实");
		button[4] = new JButton("删除空行");

		for (int i = 0; i < 5; i++) {// 给每个按钮添加事件
			button[i].addActionListener(this);
		}

		model = new DefaultTableModel();
		table = new JTable(model);// 创建表格
		attribute = new Vector();

		line = 0;// 记录该表的列数
		connectDatabase();// 连接数据库
		try {
			String sql = "select column_name from user_tab_cols where table_name='EMPLOYEE'";// 查询表的列名
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				attribute.add(result.getString(1));
				line++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询列名失败");
		}

		Vector data = new Vector();

		rows = 0;// 行数
		try {
			String sql = "select * from EMPLOYEE";// 查询数据
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				Vector row = new Vector();// 一行数据
				for (int i = 1; i <= line; i++) {
					row.add(result.getString(i));
				}
				data.add(row);
				rows++;// 行数加1
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询列名失败");
		}

		model.setDataVector(data, attribute);// 创建
		jspane = new JScrollPane(table);// 新建jspane

		table.setPreferredScrollableViewportSize(new Dimension(200, 200));// 首先大小

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
		frame.setLocationRelativeTo(frame.getOwner());// 设置窗口到中心位置
	}

	public void DisConnect() {
		try {
			// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
			// 注意关闭的顺序，最后使用的最先关闭
			if (result != null)
				result.close();
			if (pre != null)
				pre.close();
			if (con != null)
				con.close();
			System.out.println("数据库连接已关闭！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcGui jg = new JdbcGui();
		// jg.DisConnect();
	}

	int newrows = 0;// 要添加到数据库的行数据

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
		if (e.getSource() == button[1]) {// 多行插入
			frame1 = new JFrame();
			textarea = new JTextArea();

			jspanel1 = new JScrollPane(textarea);
			panel1 = new JPanel();

			jspanel1.setPreferredSize(new Dimension(0, 210));// 设置初始大小

			button[5] = new JButton("确定插入");
			button[5].addActionListener(this);

			panel1.add(button[5]);

			frame1.add(jspanel1, BorderLayout.NORTH);
			frame1.add(panel1, BorderLayout.SOUTH);

			frame1.setVisible(true);
			frame1.setSize(500, 300);
			frame1.setLocationRelativeTo(frame.getOwner());// 设置窗口到中心位置

		}
		
		if (e.getSource() == button[2]) {// 子查询插入
			
		}
		
		
		if (e.getSource() == button[3]) {
			int i = (int) model.getRowCount();// 现在的行数

			int temp = newrows - 1;
			for (int m = temp; m >= 0; m--) {
				String[] newline = new String[line];// 存储每行要插入的数据

				for (int j = 0; j < line; j++) {// 将表格中数据传送到newline数组中
					newline[j] = (String) model.getValueAt((i - 1) - m, j);
					System.out.print(newline[j] + " ");
				}
				System.out.println();

				// 将数组数据导入数据库中
				try {

					String sql = "insert into EMPLOYEE values(?,?,?,?,?,?,?)";

					pre = con.prepareStatement(sql);

					for (int n = 0; n < line; n++) {// 属性循环插入
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
				newrows--;// 要添加的行数减一
			}
			System.out.println("需要更新数据库的行数：" + newrows);// 若成功插入，则此时newrows应该为0
		}

		if (e.getSource() == button[4]) {// 删除为落实的行，表中已经有的行不能删除
			if (newrows > 0) {
				int i = (int) model.getRowCount();// 现在的行数
				model.removeRow(i - 1);
				newrows--;
			}
		}
		if (e.getSource() == button[5]) {// 多行确认插入,布置在点击多行插入按钮后弹出的界面上
			System.out.println(textarea.getLineCount());// 输出textarea一共的行数
			System.out.println(textarea.getText());// 文本输出

			String[] RowArray = textarea.getText().split("\n");// 以换行符将String分割，有几行就分割成几个String

			String[][] AttributeArray = new String[textarea.getLineCount()][];// 用二维数组存储所有的属性

			for (int li = 0; li < textarea.getLineCount(); li++) {// 逐行将属性存储到AttributeArray数组中
				AttributeArray[li] = RowArray[li].split("\t");// 将每一行分割存入到二维数组中去
			}

			for (int j = 0; j < textarea.getLineCount(); j++) {
				for (int i = 0; i < AttributeArray[0].length; i++) {// 二重循环将二维数组元素依次输出
					if (AttributeArray[j].equals(""))
						AttributeArray[j][i] = null;
					System.out.print(AttributeArray[j][i] + " ");
				}
				System.out.println("/n第" + j + "行存了数据个数" + AttributeArray[0].length);
			}

			int tag = 0;// 记录在sourceStrArray数组的位置
			for (int j = 0; j < textarea.getLineCount(); j++) {// 文本中有几行循环几次
				try {

					String sql = "insert into EMPLOYEE values(?,?,?,?,?,?,?)";

					pre = con.prepareStatement(sql);

					for (int n = 0; n < line; n++) {// 属性循环插入
						if (n == 3) {
							if (AttributeArray[j][n] == null) {// 此表中第四个元素是float型，单独处理
								pre.setNull(4, java.sql.Types.FLOAT);// 输入空
							} else {
								float temp = Float.valueOf(AttributeArray[j][n]);// 将数据转化为float型
								pre.setFloat(4, temp);
							}
						} else {
							pre.setString(n + 1, AttributeArray[j][n]);
						}
						tag++;// 指向数组下一个数据
					}
					pre.executeUpdate();
				} catch (SQLException exception) {
					// TODO Auto-generated catch block
					System.out.println(tag - 1);
					System.out.println(AttributeArray[j] + "行出错");
					exception.printStackTrace();
				}
			}
		}
	}
}
