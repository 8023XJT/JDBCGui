package Gui;



import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ImplementDemo {
    public static void main(String[] args) throws EmptyStackException{
        JFrame f=new JFrame("Welcome To Earth!");
        JTextField name=new JTextField(30);
        JTextField noed=new JTextField("HJW",10);
        JLabel nameLab=new JLabel("�����û�����:");
        JLabel noedLab=new JLabel("���ɱ༭�ı� ");
        noed.setEnabled(false);
        name.setColumns(30);
        noed.setColumns(30);
        f.setLayout(new GridLayout(2,2));//���ò��ֹ�����
        f.add(nameLab);
        f.add(name);
        f.add(noedLab);
        f.add(noed);
        f.setSize(300,100);
        f.setLocation(300,200);
        f.setVisible(true);
        
    }
    
}