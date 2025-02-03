package Frames;
import Admin.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Color;
import java.awt.Font;



  public class AdminDashboard implements ActionListener 
  {
    private JFrame frame;

    public AdminDashboard() {
        frame = new JFrame("Library Management System");
        frame.setSize(600, 600);
        frame.setLayout(null);

       
        ImageIcon backgroundImage = new ImageIcon("Pictures/background1.jpg"); 
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(backgroundLabel);

       
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setBounds(200, 30, 250, 40); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20)); 
        frame.add(titleLabel);


        
        int buttonWidth = 200;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - buttonWidth) / 2;

        
        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setBounds(centerX, 100, buttonWidth, buttonHeight);
        manageUsersButton.addActionListener(this);
        frame.add(manageUsersButton);

        
        JButton manageBooksButton = new JButton("Manage Books");
        manageBooksButton.setBounds(centerX, 160, buttonWidth, buttonHeight);
        manageBooksButton.addActionListener(this);
        frame.add(manageBooksButton);

       
        JButton logoutButton = new JButton("Logout");
        int logoutWidth = 120; 
        int logoutHeight = 30; 
        int logoutX = (frame.getWidth() - logoutWidth) / 2;
        logoutButton.setBounds(logoutX, 250, logoutWidth, logoutHeight); 
        logoutButton.addActionListener(this);
        frame.add(logoutButton);

        
        frame.getContentPane().setComponentZOrder(backgroundLabel, frame.getContentPane().getComponentCount() - 1);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }




    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() instanceof JButton) 
		{
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Manage Users")) 
			{
                frame.dispose(); 
				new ManageUsers();
            } else if (button.getText().equals("Manage Books")) 
			{
                frame.dispose(); 
				new ManageBooks();
            } else if (button.getText().equals("Logout")) 
			{
                frame.dispose();
                new Login();
            }
        }
    }
}

